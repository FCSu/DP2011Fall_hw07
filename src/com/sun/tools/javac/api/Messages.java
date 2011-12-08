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
import java.util.MissingResourceException;

/**
 * This interface defines the minimum requirements in order to provide support
 * for localized formatted strings.
 *
 * @author Maurizio Cimadamore
 */
public interface Messages {

    /**
     * Add a new resource bundle to the list that is searched for localized messages.
     * @param bundleName the name to identify the resource bundle of localized messages.
     * @throws MissingResourceException if the given resource is not found
     */
    void add(String bundleName) throws MissingResourceException;

    /**
     * Get a localized formatted string
     * @param l locale in which the text is to be localized
     * @param key locale-independent message key
     * @param args misc message arguments
     * @return a localized formatted string
     */
    String getLocalizedString(Locale l, String key, Object... args);
}
