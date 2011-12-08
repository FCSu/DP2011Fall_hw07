/*
 * Copyright 2001-2006 Sun Microsystems, Inc.  All rights reserved.
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

import com.sun.tools.javac.main.OptionName;
import java.util.*;

/** A table of all command-line options.
 *  If an option has an argument, the option name is mapped to the argument.
 *  If a set option has no argument, it is mapped to itself.
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class Options {
    private static final long serialVersionUID = 0;

    /** The context key for the options. */
    public static final Context.Key<Options> optionsKey =
        new Context.Key<Options>();

    private LinkedHashMap<String,String> values;

    /** Get the Options instance for this context. */
    public static Options instance(Context context) {
        Options instance = context.get(optionsKey);
        if (instance == null)
            instance = new Options(context);
        return instance;
    }

    protected Options(Context context) {
// DEBUGGING -- Use LinkedHashMap for reproducability
        values = new LinkedHashMap<String,String>();
        context.put(optionsKey, this);
    }

    public String get(String name) {
        return values.get(name);
    }

    public String get(OptionName name) {
        return values.get(name.optionName);
    }

    public void put(String name, String value) {
        values.put(name, value);
    }

    public void put(OptionName name, String value) {
        values.put(name.optionName, value);
    }

    public void putAll(Options options) {
        values.putAll(options.values);
    }

    public void remove(String name) {
        values.remove(name);
    }

    public Set<String> keySet() {
        return values.keySet();
    }

    public int size() {
        return values.size();
    }

    static final String LINT = "-Xlint";

    /** Check for a lint suboption. */
    public boolean lint(String s) {
        // return true if either the specific option is enabled, or
        // they are all enabled without the specific one being
        // disabled
        return
            get(LINT + ":" + s)!=null ||
            (get(LINT)!=null || get(LINT + ":all")!=null) &&
                get(LINT+":-"+s)==null;
    }
}
