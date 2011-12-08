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
package com.sun.tools.javac.api;

import java.util.Locale;
import javax.tools.Diagnostic;

/**
 * Provides simple functionalities for javac diagnostic formatting
 * @param <D> type of diagnostic handled by this formatter
 */
public interface DiagnosticFormatter<D extends Diagnostic<?>> {

    /**
     * Whether the source code output for this diagnostic is to be displayed
     *
     * @param diag diagnostic to be formatted
     * @return true if the source line this diagnostic refers to is to be displayed
     */
    boolean displaySource(D diag);

    /**
     * Format the contents of a diagnostics
     *
     * @param diag the diagnostic to be formatted
     * @param l locale object to be used for i18n
     * @return a string representing the diagnostic
     */
    public String format(D diag, Locale l);

    /**
     * Controls the way in which a diagnostic message is displayed.
     *
     * @param diag diagnostic to be formatted
     * @param l locale object to be used for i18n
     * @return string representation of the diagnostic message
     */
    public String formatMessage(D diag,Locale l);

    /**
     * Controls the way in which a diagnostic kind is displayed.
     *
     * @param diag diagnostic to be formatted
     * @param l locale object to be used for i18n
     * @return string representation of the diagnostic prefix
     */
    public String formatKind(D diag, Locale l);

    /**
     * Controls the way in which a diagnostic source is displayed.
     *
     * @param diag diagnostic to be formatted
     * @param l locale object to be used for i18n
     * @param fullname whether the source fullname should be printed
     * @return string representation of the diagnostic source
     */
    public String formatSource(D diag, boolean fullname, Locale l);

    /**
     * Controls the way in which a diagnostic position is displayed.
     *
     * @param diag diagnostic to be formatted
     * @param pk enum constant representing the position kind
     * @param l locale object to be used for i18n
     * @return string representation of the diagnostic position
     */
    public String formatPosition(D diag, PositionKind pk, Locale l);
    //where
    /**
     * This enum defines a set of constants for all the kinds of position
     * that a diagnostic can be asked for. All positions are intended to be
     * relative to a given diagnostic source.
     */
    public enum PositionKind {
        /**
         * Start position
         */
        START,
        /**
         * End position
         */
        END,
        /**
         * Line number
         */
        LINE,
        /**
         * Column number
         */
        COLUMN,
        /**
         * Offset position
         */
        OFFSET
    }
}
