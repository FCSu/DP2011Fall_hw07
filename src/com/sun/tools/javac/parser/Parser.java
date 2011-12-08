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

import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCStatement;

/**
 * Reads syntactic units from source code.
 * Parsers are normally created from a ParserFactory.
 *
 * <p><b>This is NOT part of any API supported by Sun Microsystems.
 * If you write code that depends on this, you do so at your own risk.
 * This code and its internal interfaces are subject to change or
 * deletion without notice.</b>
 */
public interface Parser {
    /**
     * Parse a compilation unit.
     * @return a compilation unit
     */
    JCCompilationUnit parseCompilationUnit();

    /**
     * Parse an expression.
     * @return an expression
     */
    JCExpression parseExpression();

    /**
     * Parse a statement.
     * @return an expression
     */
    JCStatement parseStatement();

    /**
     * Parse a type.
     * @return an expression for a type
     */
    JCExpression parseType();
}
