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
 * A tree node for a parenthesized expression.  Note: parentheses
 * not be preserved by the parser.
 *
 * For example:
 * <pre>
 *   ( <em>expression</em> )
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 15.8.5"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface ParenthesizedTree extends ExpressionTree {
    ExpressionTree getExpression();
}
