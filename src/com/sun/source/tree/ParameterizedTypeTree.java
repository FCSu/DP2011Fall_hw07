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

import java.util.List;

/**
 * A tree node for a type expression involving type parameters.
 *
 * For example:
 * <pre>
 *   <em>type</em> &lt; <em>typeArguments</em> &gt;
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 4.5.1"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface ParameterizedTypeTree extends Tree {
    Tree getType();
    List<? extends Tree> getTypeArguments();
}
