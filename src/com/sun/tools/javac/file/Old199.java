/*
 * Copyright 2006-2008 Sun Microsystems, Inc.  All rights reserved.
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

import javax.tools.FileObject;

/**
 * Provides an easy migration to JSR 199 v3.3.  The class is
 * deprecated as we should remove it as soon as possible.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</b></p>
 *
 * @author Peter von der Ah\u00e9
 */
@Deprecated
public class Old199 {

    private Old199() {}

    public static String getPath(FileObject jfo) {
        return JavacFileManager.getJavacFileName(jfo);
    }

    public static String getName(FileObject jfo) {
        return JavacFileManager.getJavacBaseFileName(jfo);
    }

}
