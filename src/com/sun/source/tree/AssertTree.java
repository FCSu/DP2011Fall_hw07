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
 * A tree node for an 'assert' statement.
 *
 * For example:
 * <pre>
 *   assert <em>condition</em> ;
 *
 *   assert <em>condition</em> : <em>detail</em> ;
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 14.10"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface AssertTree extends StatementTree {
    ExpressionTree getCondition();
    ExpressionTree getDetail();
}
