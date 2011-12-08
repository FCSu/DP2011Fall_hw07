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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import javax.tools.JavaFileObject;

/**
 * A subclass of JavaFileObject representing regular files.
 */
class RegularFileObject extends BaseFileObject {

    /** Have the parent directories been created?
     */
    private boolean hasParents = false;
    private String name;
    final File f;

    public RegularFileObject(JavacFileManager fileManager, File f) {
        this(fileManager, f.getName(), f);
    }

    public RegularFileObject(JavacFileManager fileManager, String name, File f) {
        super(fileManager);
        if (f.isDirectory()) {
            throw new IllegalArgumentException("directories not supported");
        }
        this.name = name;
        this.f = f;
    }

    public InputStream openInputStream() throws IOException {
        return new FileInputStream(f);
    }

    protected CharsetDecoder getDecoder(boolean ignoreEncodingErrors) {
        return fileManager.getDecoder(fileManager.getEncodingName(), ignoreEncodingErrors);
    }

    public OutputStream openOutputStream() throws IOException {
        ensureParentDirectoriesExist();
        return new FileOutputStream(f);
    }

    public Writer openWriter() throws IOException {
        ensureParentDirectoriesExist();
        return new OutputStreamWriter(new FileOutputStream(f), fileManager.getEncodingName());
    }

    @Override
    protected String inferBinaryName(Iterable<? extends File> path) {
        String fPath = f.getPath();
        //System.err.println("RegularFileObject " + file + " " +r.getPath());
        for (File dir: path) {
            //System.err.println("dir: " + dir);
            String dPath = dir.getPath();
            if (dPath.length() == 0)
                dPath = System.getProperty("user.dir");
            if (!dPath.endsWith(File.separator))
                dPath += File.separator;
            if (fPath.regionMatches(true, 0, dPath, 0, dPath.length())
                && new File(fPath.substring(0, dPath.length())).equals(new File(dPath))) {
                String relativeName = fPath.substring(dPath.length());
                return removeExtension(relativeName).replace(File.separatorChar, '.');
            }
        }
        return null;
    }

    private void ensureParentDirectoriesExist() throws IOException {
        if (!hasParents) {
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    if (!parent.exists() || !parent.isDirectory()) {
                        throw new IOException("could not create parent directories");
                    }
                }
            }
            hasParents = true;
        }
    }

    @Deprecated
    public String getName() {
        return name;
    }

    public boolean isNameCompatible(String cn, JavaFileObject.Kind kind) {
        cn.getClass();
        // null check
        if (kind == Kind.OTHER && getKind() != kind) {
            return false;
        }
        String n = cn + kind.extension;
        if (name.equals(n)) {
            return true;
        }
        if (name.equalsIgnoreCase(n)) {
            try {
                // allow for Windows
                return f.getCanonicalFile().getName().equals(n);
            } catch (IOException e) {
            }
        }
        return false;
    }

    @Deprecated
    public String getPath() {
        return f.getPath();
    }

    public long getLastModified() {
        return f.lastModified();
    }

    public boolean delete() {
        return f.delete();
    }

    public CharBuffer getCharContent(boolean ignoreEncodingErrors) throws IOException {
        CharBuffer cb = fileManager.getCachedContent(this);
        if (cb == null) {
            InputStream in = new FileInputStream(f);
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
        if (!(other instanceof RegularFileObject)) {
            return false;
        }
        RegularFileObject o = (RegularFileObject) other;
        try {
            return f.equals(o.f) || f.getCanonicalFile().equals(o.f.getCanonicalFile());
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return f.hashCode();
    }

    public URI toUri() {
        try {
            String path = f.getAbsolutePath().replace(File.separatorChar, '/');
            return new URI("file://" + path).normalize();
        } catch (URISyntaxException ex) {
            return f.toURI();
        }
    }
}
