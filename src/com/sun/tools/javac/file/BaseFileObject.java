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
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.CharsetDecoder;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;

import static javax.tools.JavaFileObject.Kind.*;

public abstract class BaseFileObject implements JavaFileObject {
    protected BaseFileObject(JavacFileManager fileManager) {
        this.fileManager = fileManager;
    }

    public JavaFileObject.Kind getKind() {
        String n = getName();
        if (n.endsWith(CLASS.extension))
            return CLASS;
        else if (n.endsWith(SOURCE.extension))
            return SOURCE;
        else if (n.endsWith(HTML.extension))
            return HTML;
        else
            return OTHER;
    }

    @Override
    public String toString() {
        return getPath();
    }

    /** @deprecated see bug 6410637 */
    @Deprecated
    public String getPath() {
        return getName();
    }

    /** @deprecated see bug 6410637 */
    @Deprecated
    abstract public String getName();

    public NestingKind getNestingKind() { return null; }

    public Modifier getAccessLevel()  { return null; }

    public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
        return new InputStreamReader(openInputStream(), getDecoder(ignoreEncodingErrors));
    }

    protected CharsetDecoder getDecoder(boolean ignoreEncodingErrors) {
        throw new UnsupportedOperationException();
    }

    protected abstract String inferBinaryName(Iterable<? extends File> path);

    protected static String removeExtension(String fileName) {
        int lastDot = fileName.lastIndexOf(".");
        return (lastDot == -1 ? fileName : fileName.substring(0, lastDot));
    }

    /** The file manager that created this JavaFileObject. */
    protected final JavacFileManager fileManager;

}
