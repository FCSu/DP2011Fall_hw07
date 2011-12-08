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

package com.sun.tools.javac.util;

import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileObject;

import com.sun.tools.javac.util.JCDiagnostic.DiagnosticPosition;
import com.sun.tools.javac.util.JCDiagnostic.SimpleDiagnosticPosition;


/**
 *  A base class for error logs. Reports errors and warnings, and
 *  keeps track of error numbers and positions.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public abstract class AbstractLog {
    AbstractLog(JCDiagnostic.Factory diags) {
        this.diags = diags;
        sourceMap = new HashMap<JavaFileObject, DiagnosticSource>();
    }

    /** Re-assign source, returning previous setting.
     */
    public JavaFileObject useSource(JavaFileObject file) {
        JavaFileObject prev = (source == null ? null : source.getFile());
        source = getSource(file);
        return prev;
    }

    protected DiagnosticSource getSource(JavaFileObject file) {
        if (file == null)
            return null;
        DiagnosticSource s = sourceMap.get(file);
        if (s == null) {
            s = new DiagnosticSource(file, this);
            sourceMap.put(file, s);
        }
        return s;
    }

    /** Report an error, unless another error was already reported at same
     *  source position.
     *  @param key    The key for the localized error message.
     *  @param args   Fields of the error message.
     */
    public void error(String key, Object ... args) {
        report(diags.error(source, null, key, args));
    }

    /** Report an error, unless another error was already reported at same
     *  source position.
     *  @param pos    The source position at which to report the error.
     *  @param key    The key for the localized error message.
     *  @param args   Fields of the error message.
     */
    public void error(DiagnosticPosition pos, String key, Object ... args) {
        report(diags.error(source, pos, key, args));
    }

    /** Report an error, unless another error was already reported at same
     *  source position.
     *  @param pos    The source position at which to report the error.
     *  @param key    The key for the localized error message.
     *  @param args   Fields of the error message.
     */
    public void error(int pos, String key, Object ... args) {
        report(diags.error(source, wrap(pos), key, args));
    }

    /** Report a warning, unless suppressed by the  -nowarn option or the
     *  maximum number of warnings has been reached.
     *  @param pos    The source position at which to report the warning.
     *  @param key    The key for the localized warning message.
     *  @param args   Fields of the warning message.
     */
    public void warning(String key, Object ... args) {
        report(diags.warning(source, null, key, args));
    }

    /** Report a warning, unless suppressed by the  -nowarn option or the
     *  maximum number of warnings has been reached.
     *  @param pos    The source position at which to report the warning.
     *  @param key    The key for the localized warning message.
     *  @param args   Fields of the warning message.
     */
    public void warning(DiagnosticPosition pos, String key, Object ... args) {
        report(diags.warning(source, pos, key, args));
    }

    /** Report a warning, unless suppressed by the  -nowarn option or the
     *  maximum number of warnings has been reached.
     *  @param pos    The source position at which to report the warning.
     *  @param key    The key for the localized warning message.
     *  @param args   Fields of the warning message.
     */
    public void warning(int pos, String key, Object ... args) {
        report(diags.warning(source, wrap(pos), key, args));
    }

    /** Report a warning.
     *  @param pos    The source position at which to report the warning.
     *  @param key    The key for the localized warning message.
     *  @param args   Fields of the warning message.
     */
    public void mandatoryWarning(DiagnosticPosition pos, String key, Object ... args) {
        report(diags.mandatoryWarning(source, pos, key, args));
    }

    /** Provide a non-fatal notification, unless suppressed by the -nowarn option.
     *  @param key    The key for the localized notification message.
     *  @param args   Fields of the notint an error or warning message:
     */
    public void note(String key, Object ... args) {
        report(diags.note(source, null, key, args));
    }

    /** Provide a non-fatal notification, unless suppressed by the -nowarn option.
     *  @param key    The key for the localized notification message.
     *  @param args   Fields of the notification message.
     */
    public void note(DiagnosticPosition pos, String key, Object ... args) {
        report(diags.note(source, pos, key, args));
    }

    /** Provide a non-fatal notification, unless suppressed by the -nowarn option.
     *  @param key    The key for the localized notification message.
     *  @param args   Fields of the notification message.
     */
    public void note(int pos, String key, Object ... args) {
        report(diags.note(source, wrap(pos), key, args));
    }

    /** Provide a non-fatal notification, unless suppressed by the -nowarn option.
     *  @param key    The key for the localized notification message.
     *  @param args   Fields of the notification message.
     */
    public void note(JavaFileObject file, String key, Object ... args) {
        report(diags.note(getSource(file), null, key, args));
    }

    /** Provide a non-fatal notification, unless suppressed by the -nowarn option.
     *  @param key    The key for the localized notification message.
     *  @param args   Fields of the notification message.
     */
    public void mandatoryNote(final JavaFileObject file, String key, Object ... args) {
        report(diags.mandatoryNote(getSource(file), key, args));
    }

    protected abstract void report(JCDiagnostic diagnostic);

    protected abstract void directError(String key, Object... args);

    private DiagnosticPosition wrap(int pos) {
        return (pos == Position.NOPOS ? null : new SimpleDiagnosticPosition(pos));
    }

    /** Factory for diagnostics
     */
    protected JCDiagnostic.Factory diags;

    /** The file that's currently being translated.
     */
    protected DiagnosticSource source;

    /** A cache of lightweight DiagnosticSource objects.
     */
    protected Map<JavaFileObject, DiagnosticSource> sourceMap;
}
