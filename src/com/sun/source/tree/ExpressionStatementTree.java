/*
 * Copyright 2005 Sun Microsystems, Inc.  All rights reserved.
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

package com.sun.source.tree;

/**
 * A tree node for an expression statement.
 *
 * For example:
 * <pre>
 *   <em>expression</em> ;
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 14.8"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface ExpressionStatementTree extends StatementTree {
    ExpressionTree getExpression();
}
