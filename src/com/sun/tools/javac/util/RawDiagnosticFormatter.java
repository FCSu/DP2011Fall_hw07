/*
 * Copyright 2008 Sun Microsystems, Inc.  All rights reserved.
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
package com.sun.tools.javac.util;

import java.util.Locale;

import com.sun.tools.javac.api.Formattable;
import static com.sun.tools.javac.api.DiagnosticFormatter.PositionKind.*;

/**
 * A raw formatter for diagnostic messages.
 * The raw formatter will format a diagnostic according to one of two format patterns, depending on whether
 * or not the source name and position are set. This formatter provides a standardized, localize-independent
 * implementation of a diagnostic formatter; as such, this formatter is best suited for testing purposes.
 */
public class RawDiagnosticFormatter extends AbstractDiagnosticFormatter {

    /**
     * Create a formatter based on the supplied options.
     * @param msgs
     */
    public RawDiagnosticFormatter(Options opts) {
        super(null, opts, false);
    }

    //provide common default formats
    public String format(JCDiagnostic d, Locale l) {
        try {
            StringBuffer buf = new StringBuffer();
            if (d.getPosition() != Position.NOPOS) {
                buf.append(formatSource(d, false, null));
                buf.append(':');
                buf.append(formatPosition(d, LINE, null));
                buf.append(':');
                buf.append(formatPosition(d, COLUMN, null));
                buf.append(':');
            }
            else
                buf.append('-');
            buf.append(' ');
            buf.append(formatMessage(d, null));
            if (displaySource(d))
                buf.append("\n" + formatSourceLine(d));
            return buf.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String formatArgument(JCDiagnostic diag, Object arg, Locale l) {
        String s;
        if (arg instanceof Formattable)
            s = arg.toString();
        else
            s = super.formatArgument(diag, arg, null);
        if (arg instanceof JCDiagnostic)
            return "(" + s + ")";
        else
            return s;
    }

    @Override
    protected String localize(Locale l, String s, Object... args) {
        StringBuffer buf = new StringBuffer();
        buf.append(s);
        String sep = ": ";
        for (Object o : args) {
            buf.append(sep);
            buf.append(o);
            sep = ", ";
        }
        return buf.toString();
    }
}
