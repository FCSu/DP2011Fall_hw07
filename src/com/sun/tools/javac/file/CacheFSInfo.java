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
import java.util.List;

import com.sun.tools.javac.util.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caching implementation of FSInfo
 */
public class CacheFSInfo extends FSInfo {

    /**
     * Register a Context.Factory to create a singleton CacheFSInfo.
     */
    public static void preRegister(final Context context) {
        context.put(FSInfo.class, new Context.Factory<FSInfo>() {
            public FSInfo make() {
                if (singleton == null)
                    singleton = new CacheFSInfo();
                context.put(FSInfo.class, singleton);
                return singleton;
            }
        });
    }

    static CacheFSInfo singleton;

    public void clearCache() {
        cache.clear();
    }

    @Override
    public File getCanonicalFile(File file) {
        Entry e = getEntry(file);
        return e.canonicalFile;
    }

    @Override
    public boolean exists(File file) {
        Entry e = getEntry(file);
        return e.exists;
    }

    @Override
    public boolean isDirectory(File file) {
        Entry e = getEntry(file);
        return e.isDirectory;
    }

    @Override
    public boolean isFile(File file) {
        Entry e = getEntry(file);
        return e.isFile;
    }

    @Override
    public List<File> getJarClassPath(File file) throws IOException {
        // don't bother to lock the cache, because it is thread-safe, and
        // because the worst that can happen would be to create two identical
        // jar class paths together and have one overwrite the other.
        Entry e = getEntry(file);
        if (e.jarClassPath == null)
            e.jarClassPath = super.getJarClassPath(file);
        return e.jarClassPath;
    }

    private Entry getEntry(File file) {
        // don't bother to lock the cache, because it is thread-safe, and
        // because the worst that can happen would be to create two identical
        // entries together and have one overwrite the other.
        Entry e = cache.get(file);
        if (e == null) {
            e = new Entry();
            e.canonicalFile = super.getCanonicalFile(file);
            e.exists = super.exists(file);
            e.isDirectory = super.isDirectory(file);
            e.isFile = super.isFile(file);
            cache.put(file, e);
        }
        return e;
    }

    // could also be a Map<File,SoftReference<Entry>> ?
    private Map<File,Entry> cache = new ConcurrentHashMap<File,Entry>();

    private static class Entry {
        File canonicalFile;
        boolean exists;
        boolean isFile;
        boolean isDirectory;
        List<File> jarClassPath;
    }
}
