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
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.tools.JavaFileObject;

import com.sun.tools.javac.file.RelativePath.RelativeDirectory;
import com.sun.tools.javac.file.RelativePath.RelativeFile;
import com.sun.tools.javac.util.List;

public class SymbolArchive extends ZipArchive {

    final File origFile;
    final RelativeDirectory prefix;

    public SymbolArchive(JavacFileManager fileManager, File orig, ZipFile zdir, RelativeDirectory prefix) throws IOException {
        super(fileManager, zdir, false);
        this.origFile = orig;
        this.prefix = prefix;
        initMap();
    }

    @Override
    void addZipEntry(ZipEntry entry) {
        String name = entry.getName();
        if (!name.startsWith(prefix.path)) {
            return;
        }
        name = name.substring(prefix.path.length());
        int i = name.lastIndexOf('/');
        RelativeDirectory dirname = new RelativeDirectory(name.substring(0, i+1));
        String basename = name.substring(i + 1);
        if (basename.length() == 0) {
            return;
        }
        List<String> list = map.get(dirname);
        if (list == null)
            list = List.nil();
        list = list.prepend(basename);
        map.put(dirname, list);
    }

    public JavaFileObject getFileObject(RelativeDirectory subdirectory, String file) {
        RelativeDirectory prefix_subdir = new RelativeDirectory(prefix, subdirectory.path);
        ZipEntry ze = new RelativeFile(prefix_subdir, file).getZipEntry(zdir);
        return new SymbolFileObject(this, file, ze);
    }

    public String toString() {
        return "SymbolArchive[" + zdir.getName() + "]";
    }

    /**
     * A subclass of JavaFileObject representing zip entries in a symbol file.
     */
    public static class SymbolFileObject extends ZipFileObject {
        protected SymbolFileObject(SymbolArchive zarch, String name, ZipEntry entry) {
            super(zarch, name, entry);
        }

        @Override
        protected String inferBinaryName(Iterable<? extends File> path) {
            String entryName = getZipEntryName();
            String prefix = ((SymbolArchive) zarch).prefix.path;
            if (entryName.startsWith(prefix))
                entryName = entryName.substring(prefix.length());
            return removeExtension(entryName).replace('/', '.');
        }
    }


}
