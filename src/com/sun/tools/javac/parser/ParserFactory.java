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

package com.sun.tools.javac.parser;

import com.sun.tools.javac.code.Source;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javac.util.Options;

/**
 * A factory for creating parsers.
 */
public class ParserFactory {

    /** The context key for the parser factory. */
    protected static final Context.Key<ParserFactory> parserFactoryKey = new Context.Key<ParserFactory>();

    public static ParserFactory instance(Context context) {
        ParserFactory instance = context.get(parserFactoryKey);
        if (instance == null) {
            instance = new ParserFactory(context);
        }
        return instance;
    }

    final TreeMaker F;
    final Log log;
    final Keywords keywords;
    final Source source;
    final Names names;
    final Options options;
    final Scanner.Factory scannerFactory;

    protected ParserFactory(Context context) {
        super();
        context.put(parserFactoryKey, this);
        this.F = TreeMaker.instance(context);
        this.log = Log.instance(context);
        this.names = Names.instance(context);
        this.keywords = Keywords.instance(context);
        this.source = Source.instance(context);
        this.options = Options.instance(context);
        this.scannerFactory = Scanner.Factory.instance(context);
    }

    public Parser newParser(CharSequence input, boolean keepDocComments, boolean keepEndPos, boolean keepLineMap) {
        Lexer lexer = scannerFactory.newScanner(input);
        if (keepEndPos) {
            return new EndPosParser(this, lexer, keepDocComments, keepLineMap);
        } else {
            return new JavacParser(this, lexer, keepDocComments, keepLineMap);
        }
    }
}
