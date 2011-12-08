/*
 * Copyright 2005-2006 Sun Microsystems, Inc.  All rights reserved.
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

import java.util.List;

/**
 * A tree node for an annotation.
 *
 * For example:
 * <pre>
 *    {@code @}<em>annotationType</em>
 *    {@code @}<em>annotationType</em> ( <em>arguments</em> )
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 9.7"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface AnnotationTree extends ExpressionTree {
    Tree getAnnotationType();
    List<? extends ExpressionTree> getArguments();
}
