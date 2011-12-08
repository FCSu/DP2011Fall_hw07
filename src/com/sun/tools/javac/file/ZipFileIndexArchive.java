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

import java.io.IOException;
import java.util.Set;
import javax.tools.JavaFileObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;

import com.sun.tools.javac.file.JavacFileManager.Archive;
import com.sun.tools.javac.file.RelativePath.RelativeDirectory;
import com.sun.tools.javac.file.RelativePath.RelativeFile;
import com.sun.tools.javac.util.List;

public class ZipFileIndexArchive implements Archive {

    private final ZipFileIndex zfIndex;
    private JavacFileManager fileManager;

    public ZipFileIndexArchive(JavacFileManager fileManager, ZipFileIndex zdir) throws IOException {
        super();
        this.fileManager = fileManager;
        this.zfIndex = zdir;
    }

    public boolean contains(RelativePath name) {
        return zfIndex.contains(name);
    }

    public List<String> getFiles(RelativeDirectory subdirectory) {
        return zfIndex.getFiles(subdirectory);
    }

    public JavaFileObject getFileObject(RelativeDirectory subdirectory, String file) {
        RelativeFile fullZipFileName = new RelativeFile(subdirectory, file);
        ZipFileIndex.Entry entry = zfIndex.getZipIndexEntry(fullZipFileName);
        JavaFileObject ret = new ZipFileIndexFileObject(fileManager, zfIndex, entry, zfIndex.getZipFile().getPath());
        return ret;
    }

    public Set<RelativeDirectory> getSubdirectories() {
        return zfIndex.getAllDirectories();
    }

    public void close() throws IOException {
        zfIndex.close();
    }

    public String toString() {
        return "ZipFileIndexArchive[" + zfIndex + "]";
    }

    /**
     * A subclass of JavaFileObject representing zip entries using the com.sun.tools.javac.file.ZipFileIndex implementation.
     */
    public static class ZipFileIndexFileObject extends BaseFileObject {

        /** The entry's name.
         */
        private String name;

        /** The zipfile containing the entry.
         */
        ZipFileIndex zfIndex;

        /** The underlying zip entry object.
         */
        ZipFileIndex.Entry entry;

        /** The InputStream for this zip entry (file.)
         */
        InputStream inputStream = null;

        /** The name of the zip file where this entry resides.
         */
        String zipName;


        ZipFileIndexFileObject(JavacFileManager fileManager, ZipFileIndex zfIndex, ZipFileIndex.Entry entry, String zipFileName) {
            super(fileManager);
            this.name = entry.getFileName();
            this.zfIndex = zfIndex;
            this.entry = entry;
            this.zipName = zipFileName;
        }

        public InputStream openInputStream() throws IOException {

            if (inputStream == null) {
                inputStream = new ByteArrayInputStream(read());
            }
            return inputStream;
        }

        protected CharsetDecoder getDecoder(boolean ignoreEncodingErrors) {
            return fileManager.getDecoder(fileManager.getEncodingName(), ignoreEncodingErrors);
        }

        public OutputStream openOutputStream() throws IOException {
            throw new UnsupportedOperationException();
        }

        public Writer openWriter() throws IOException {
            throw new UnsupportedOperationException();
        }

        /** @deprecated see bug 6410637 */
        @Deprecated
        public String getName() {
            return name;
        }

        public boolean isNameCompatible(String cn, JavaFileObject.Kind k) {
            cn.getClass(); // null check
            if (k == Kind.OTHER && getKind() != k)
                return false;
            return name.equals(cn + k.extension);
        }

        /** @deprecated see bug 6410637 */
        @Deprecated
        public String getPath() {
            return zipName + "(" + entry.getName() + ")";
        }

        public long getLastModified() {
            return entry.getLastModified();
        }

        public boolean delete() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ZipFileIndexFileObject))
                return false;
            ZipFileIndexFileObject o = (ZipFileIndexFileObject) other;
            return entry.equals(o.entry);
        }

        @Override
        public int hashCode() {
            return zipName.hashCode() + (name.hashCode() << 10);
        }

        public String getZipName() {
            return zipName;
        }

        public String getZipEntryName() {
            return entry.getName();
        }

        public URI toUri() {
            String zipName = new File(getZipName()).toURI().normalize().getPath();
            String entryName = getZipEntryName();
            return URI.create("jar:" + zipName + "!" + entryName);
        }

        private byte[] read() throws IOException {
            assert entry != null; // see constructor
            return zfIndex.read(entry);
        }

        public CharBuffer getCharContent(boolean ignoreEncodingErrors) throws IOException {
            CharBuffer cb = fileManager.getCachedContent(this);
            if (cb == null) {
                InputStream in = new ByteArrayInputStream(zfIndex.read(entry));
                try {
                    ByteBuffer bb = fileManager.makeByteBuffer(in);
                    JavaFileObject prev = fileManager.log.useSource(this);
                    try {
                        cb = fileManager.decode(bb, ignoreEncodingErrors);
                    } finally {
                        fileManager.log.useSource(prev);
                    }
                    fileManager.recycleByteBuffer(bb); // save for next time
                    if (!ignoreEncodingErrors)
                        fileManager.cache(this, cb);
                } finally {
                    in.close();
                }
            }
            return cb;
        }

        @Override
        protected String inferBinaryName(Iterable<? extends File> path) {
            String entryName = getZipEntryName();
            if (zfIndex.symbolFilePrefix != null) {
                String prefix = zfIndex.symbolFilePrefix.path;
                if (entryName.startsWith(prefix))
                    entryName = entryName.substring(prefix.length());
            }
            return removeExtension(entryName).replace('/', '.');
        }
    }

}
