/*
 * Copyright 2005-2008 Sun Microsystems, Inc.  All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.tools.javac.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.tools.JavaFileObject;

import com.sun.tools.javac.file.JavacFileManager.Archive;
import com.sun.tools.javac.file.RelativePath.RelativeDirectory;
import com.sun.tools.javac.file.RelativePath.RelativeFile;
import com.sun.tools.javac.util.List;

public class ZipArchive implements Archive {

    public ZipArchive(JavacFileManager fm, ZipFile zdir) throws IOException {
        this(fm, zdir, true);
    }

    protected ZipArchive(JavacFileManager fm, ZipFile zdir, boolean initMap) throws IOException {
        this.fileManager = fm;
        this.zdir = zdir;
        this.map = new HashMap<RelativeDirectory,List<String>>();
        if (initMap)
            initMap();
    }

    protected void initMap() throws IOException {
        for (Enumeration<? extends ZipEntry> e = zdir.entries(); e.hasMoreElements(); ) {
            ZipEntry entry;
            try {
                entry = e.nextElement();
            } catch (InternalError ex) {
                IOException io = new IOException();
                io.initCause(ex); // convenience constructors added in Mustang :-(
                throw io;
            }
            addZipEntry(entry);
        }
    }

    void addZipEntry(ZipEntry entry) {
        String name = entry.getName();
        int i = name.lastIndexOf('/');
        RelativeDirectory dirname = new RelativeDirectory(name.substring(0, i+1));
        String basename = name.substring(i+1);
        if (basename.length() == 0)
            return;
        List<String> list = map.get(dirname);
        if (list == null)
            list = List.nil();
        list = list.prepend(basename);
        map.put(dirname, list);
    }

    public boolean contains(RelativePath name) {
        RelativeDirectory dirname = name.dirname();
        String basename = name.basename();
        if (basename.length() == 0)
            return false;
        List<String> list = map.get(dirname);
        return (list != null && list.contains(basename));
    }

    public List<String> getFiles(RelativeDirectory subdirectory) {
        return map.get(subdirectory);
    }

    public JavaFileObject getFileObject(RelativeDirectory subdirectory, String file) {
        ZipEntry ze = new RelativeFile(subdirectory, file).getZipEntry(zdir);
        return new ZipFileObject(this, file, ze);
    }

    public Set<RelativeDirectory> getSubdirectories() {
        return map.keySet();
    }

    public void close() throws IOException {
        zdir.close();
    }

    public String toString() {
        return "ZipArchive[" + zdir.getName() + "]";
    }

    protected JavacFileManager fileManager;
    protected final Map<RelativeDirectory,List<String>> map;
    protected final ZipFile zdir;

    /**
     * A subclass of JavaFileObject representing zip entries.
     */
    public static class ZipFileObject extends BaseFileObject {

        private String name;
        ZipArchive zarch;
        ZipEntry entry;

        protected ZipFileObject(ZipArchive zarch, String name, ZipEntry entry) {
            super(zarch.fileManager);
            this.zarch = zarch;
            this.name = name;
            this.entry = entry;
        }

        public InputStream openInputStream() throws IOException {
            return zarch.zdir.getInputStream(entry);
        }

        public OutputStream openOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        protected CharsetDecoder getDecoder(boolean ignoreEncodingErrors) {
            return fileManager.getDecoder(fileManager.getEncodingName(), ignoreEncodingErrors);
        }

        public Writer openWriter() throws IOException {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public String getName() {
            return name;
        }

        public boolean isNameCompatible(String cn, JavaFileObject.Kind k) {
            cn.getClass();
            // null check
            if (k == Kind.OTHER && getKind() != k) {
                return false;
            }
            return name.equals(cn + k.extension);
        }

        @Deprecated
        public String getPath() {
            return zarch.zdir.getName() + "(" + entry + ")";
        }

        public long getLastModified() {
            return entry.getTime();
        }

        public boolean delete() {
            throw new UnsupportedOperationException();
        }

        public CharBuffer getCharContent(boolean ignoreEncodingErrors) throws IOException {
            CharBuffer cb = fileManager.getCachedContent(this);
            if (cb == null) {
                InputStream in = zarch.zdir.getInputStream(entry);
                try {
                    ByteBuffer bb = fileManager.makeByteBuffer(in);
                    JavaFileObject prev = fileManager.log.useSource(this);
                    try {
                        cb = fileManager.decode(bb, ignoreEncodingErrors);
                    } finally {
                        fileManager.log.useSource(prev);
                    }
                    fileManager.recycleByteBuffer(bb);
                    if (!ignoreEncodingErrors) {
                        fileManager.cache(this, cb);
                    }
                } finally {
                    in.close();
                }
            }
            return cb;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ZipFileObject)) {
                return false;
            }
            ZipFileObject o = (ZipFileObject) other;
            return zarch.zdir.equals(o.zarch.zdir) || name.equals(o.name);
        }

        @Override
        public int hashCode() {
            return zarch.zdir.hashCode() + name.hashCode();
        }

        public String getZipName() {
            return zarch.zdir.getName();
        }

        public String getZipEntryName() {
            return entry.getName();
        }

        public URI toUri() {
            String zipName = new File(getZipName()).toURI().normalize().getPath();
            String entryName = getZipEntryName();
            return URI.create("jar:" + zipName + "!" + entryName);
        }

        @Override
        protected String inferBinaryName(Iterable<? extends File> path) {
            String entryName = getZipEntryName();
            return removeExtension(entryName).replace('/', '.');
        }
    }

}
