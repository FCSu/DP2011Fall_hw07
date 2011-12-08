/*
 * Copyright 1999-2008 Sun Microsystems, Inc.  All rights reserved.
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

package com.sun.tools.javac.jvm;

import com.sun.tools.javac.code.*;
import com.sun.tools.javac.util.*;

/** A JVM class file.
 *
 *  <p>Generic Java classfiles have one additional attribute for classes,
 *  methods and fields:
 *  <pre>
 *   "Signature" (u4 attr-length, u2 signature-index)
 *  </pre>
 *
 *  <p>A signature gives the full Java type of a method or field. When
 *  used as a class attribute, it indicates type parameters, followed
 *  by supertype, followed by all interfaces.
 *  <pre>
 *     methodOrFieldSignature ::= type
 *     classSignature         ::= [ typeparams ] supertype { interfacetype }
 *  </pre>
 *  <p>The type syntax in signatures is extended as follows:
 *  <pre>
 *     type       ::= ... | classtype | methodtype | typevar
 *     classtype  ::= classsig { '.' classsig }
 *     classig    ::= 'L' name [typeargs] ';'
 *     methodtype ::= [ typeparams ] '(' { type } ')' type
 *     typevar    ::= 'T' name ';'
 *     typeargs   ::= '<' type { type } '>'
 *     typeparams ::= '<' typeparam { typeparam } '>'
 *     typeparam  ::= name ':' type
 *  </pre>
 *  <p>This class defines constants used in class files as well
 *  as routines to convert between internal ``.'' and external ``/''
 *  separators in class names.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b> */
public class ClassFile {

    public final static int JAVA_MAGIC = 0xCAFEBABE;

    // see Target
    public final static int CONSTANT_Utf8 = 1;
    public final static int CONSTANT_Unicode = 2;
    public final static int CONSTANT_Integer = 3;
    public final static int CONSTANT_Float = 4;
    public final static int CONSTANT_Long = 5;
    public final static int CONSTANT_Double = 6;
    public final static int CONSTANT_Class = 7;
    public final static int CONSTANT_String = 8;
    public final static int CONSTANT_Fieldref = 9;
    public final static int CONSTANT_Methodref = 10;
    public final static int CONSTANT_InterfaceMethodref = 11;
    public final static int CONSTANT_NameandType = 12;

    public final static int MAX_PARAMETERS = 0xff;
    public final static int MAX_DIMENSIONS = 0xff;
    public final static int MAX_CODE = 0xffff;
    public final static int MAX_LOCALS = 0xffff;
    public final static int MAX_STACK = 0xffff;


/************************************************************************
 * String Translation Routines
 ***********************************************************************/

    /** Return internal representation of buf[offset..offset+len-1],
     *  converting '/' to '.'.
     */
    public static byte[] internalize(byte[] buf, int offset, int len) {
        byte[] translated = new byte[len];
        for (int j = 0; j < len; j++) {
            byte b = buf[offset + j];
            if (b == '/') translated[j] = (byte) '.';
            else translated[j] = b;
        }
        return translated;
    }

    /** Return internal representation of given name,
     *  converting '/' to '.'.
     */
    public static byte[] internalize(Name name) {
        return internalize(name.getByteArray(), name.getByteOffset(), name.getByteLength());
    }

    /** Return external representation of buf[offset..offset+len-1],
     *  converting '.' to '/'.
     */
    public static byte[] externalize(byte[] buf, int offset, int len) {
        byte[] translated = new byte[len];
        for (int j = 0; j < len; j++) {
            byte b = buf[offset + j];
            if (b == '.') translated[j] = (byte) '/';
            else translated[j] = b;
        }
        return translated;
    }

    /** Return external representation of given name,
     *  converting '/' to '.'.
     */
    public static byte[] externalize(Name name) {
        return externalize(name.getByteArray(), name.getByteOffset(), name.getByteLength());
    }

/************************************************************************
 * Name-and-type
 ***********************************************************************/

    /** A class for the name-and-type signature of a method or field.
     */
    public static class NameAndType {
        Name name;
        Type type;

        NameAndType(Name name, Type type) {
            this.name = name;
            this.type = type;
        }

        public boolean equals(Object other) {
            return
                other instanceof NameAndType &&
                name == ((NameAndType) other).name &&
                type.equals(((NameAndType) other).type);
        }

        public int hashCode() {
            return name.hashCode() * type.hashCode();
        }
    }
}
