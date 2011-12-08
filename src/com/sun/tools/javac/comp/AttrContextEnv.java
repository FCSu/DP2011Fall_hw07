/*
 * Copyright 2000-2005 Sun Microsystems, Inc.  All rights reserved.
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

package com.sun.tools.javac.comp;

import com.sun.tools.javac.tree.JCTree;


/** {@code Env<A>} specialized as {@code Env<AttrContext>}
 *
 *  <p><b>This is NOT part of any API supported by Sun Microsystems.  If
 *  you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class AttrContextEnv extends Env<AttrContext> {

    /** Create an outermost environment for a given (toplevel)tree,
     *  with a given info field.
     */
    public AttrContextEnv(JCTree tree, AttrContext info) {
        super(tree, info);
    }
}
