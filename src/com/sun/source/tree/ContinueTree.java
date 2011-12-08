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

import javax.lang.model.element.Name;

/**
 * A tree node for a 'continue' statement.
 *
 * For example:
 * <pre>
 *   continue;
 *   continue <em>label</em> ;
 * </pre>
 *
 * @see "The Java Language Specification, 3rd ed, section 14.16"
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface ContinueTree extends StatementTree {
    Name getLabel();
}
