package hw07.group04;

import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.*;
import java.util.Set;
import java.util.HashSet;


public class NodeCountingVisitor extends JCTree.Visitor {
    
    // under construction

    private int JCAnnotaion_count;
    private int JCArrayAccess_count;
    private int JCArrayTypeTree_count;
    private int JCAssert_count;
    private int JCAssign_count;
    private int JCAssignOp_count;
    private int JCBinary_count;
    private int JCBlock_count;
    private int JCBreak_count;
    private int JCCase_count;
    private int JCCatch_count;
    private int JCClassDecl_count;
    private int JCCompilationUnit_count;
    private int JCConditional_count;
    private int JCContinue_count;
    private int JCDoWhileLoop_count;
    private int JCEnhancedForLoop_count;
    private int JCErroneous_count;
    private int JCExpressionStatement_count;
    private int JCFieldAccess_count;
    private int JCForLoop_count;
    private int JCIdent_count;
    private int JCIf_count;
    private int JCImport_count; 
    private int JCInstanceOf_count;
    private int JCLabeledStatement_count;
    private int JCLiteral_count;
    private int JCMethodDecl_count;
    private int JCMethodInvocation_count;
    private int JCModifiers_count;
    private int JCNewArray_count;
    private int JCNewClass_count;
    private int JCParens_count;
    private int JCPrimitiveTypeTree_count;
    private int JCReturn_count;
    private int JCSkip_count;
    private int JCSwitch_count;
    private int JCSynchronized_count; 
    private int JCThrow_count;
    private int JCTry_count;
    private int JCTypeApply_count;
    private int JCTypeCast_count;
    private int JCTypeParameter_count;
    private int JCUnary_count;
    private int JCVariableDecl_count;
    private int JCWhileLoop_count;
    private int JCWildcard_count;
    private int LetExpr_count; 
    private int TypeBoundKind_count;
    private Set<JCTree> visited;
    
    public NodeCountingVisitor() {
        this.visited = new HashSet<JCTree>();
    }
    
    public String[] list() {
        // node name 要排序。輸出範例如下：
        return new String[]{
            "JCAnnotation " + String.valueOf(this.JCAnnotaion_count),
            "JCArrayAccess " + String.valueOf(this.JCArrayAccess_count),
            "JCArrayTypeTree " + String.valueOf(this.JCArrayTypeTree_count),
            "JCAssert " + String.valueOf(this.JCAssert_count),
            "JCAssign " + String.valueOf(this.JCAssign_count),
            "JCAssignOp " + String.valueOf(this.JCAssignOp_count),
            "JCBinary " + String.valueOf(this.JCBinary_count),
            "JCBlock " + String.valueOf(this.JCBlock_count),
            "JCBreak " + String.valueOf(this.JCBreak_count),
            "JCCase " + String.valueOf(this.JCCase_count),
            "JCCatch " + String.valueOf(this.JCCatch_count),
            "JCClassDecl " + String.valueOf(this.JCClassDecl_count),
            "JCCompilationUnit " + String.valueOf(this.JCCompilationUnit_count),
            "JCConditional " + String.valueOf(this.JCConditional_count),
            "JCContinue " + String.valueOf(this.JCContinue_count),
            "JCDoWhileLoop " + String.valueOf(this.JCDoWhileLoop_count),
            "JCEnhancedForLoop " + String.valueOf(this.JCEnhancedForLoop_count),
            "JCErroneous " + String.valueOf(this.JCErroneous_count),
            "JCExpressionStatement " + String.valueOf(this.JCExpressionStatement_count),
            "JCFieldAccess " + String.valueOf(this.JCFieldAccess_count),
            "JCForLoop " + String.valueOf(this.JCForLoop_count),
            "JCIdent " + String.valueOf(this.JCIdent_count),
            "JCIf " + String.valueOf(this.JCIf_count),
            "JCImport " + String.valueOf(this.JCImport_count),
            "JCInstanceOf " + String.valueOf(this.JCInstanceOf_count),
            "JCLabeledStatement " + String.valueOf(this.JCLabeledStatement_count),
            "JCLiteral " + String.valueOf(this.JCLiteral_count),
            "JCMethodDecl " + String.valueOf(this.JCMethodDecl_count),
            "JCMethodInvocation " + String.valueOf(this.JCMethodInvocation_count),
            "JCModifiers " + String.valueOf(this.JCModifiers_count),
            "JCNewArray " + String.valueOf(this.JCNewArray_count),
            "JCNewClass " + String.valueOf(this.JCNewClass_count),
            "JCParens " + String.valueOf(this.JCParens_count),
            "JCPrimitiveTypeTree " + String.valueOf(this.JCPrimitiveTypeTree_count),
            "JCReturn " + String.valueOf(this.JCReturn_count),
            "JCSkip " + String.valueOf(this.JCSkip_count),
            "JCSwitch " + String.valueOf(this.JCSwitch_count),
            "JCSynchronized " + String.valueOf(this.JCSynchronized_count),
            "JCThrow " + String.valueOf(this.JCThrow_count),
            "JCTry " + String.valueOf(this.JCTry_count),
            "JCTypeApply " + String.valueOf(this.JCTypeApply_count),
            "JCTypeCast " + String.valueOf(this.JCTypeCast_count),
            "JCTypeParameter " + String.valueOf(this.JCTypeParameter_count),
            "JCUnary " + String.valueOf(this.JCUnary_count),
            "JCVariableDecl " + String.valueOf(this.JCVariableDecl_count),
            "JCWhileLoop " + String.valueOf(this.JCWhileLoop_count),
            "JCWildcard " + String.valueOf(this.JCWildcard_count),
            "LetExpr " + String.valueOf(this.LetExpr_count),
            "TypeBoundKind " + String.valueOf(this.TypeBoundKind_count),
        };
    }
    
    public void count(JCTree tree) {
        if (tree != null) { 
            tree.accept(this);
            this.visited.add(tree);
        }
    }
    
    public void count(List<? extends JCTree> trees) {
        if (trees != null)
        for (List<? extends JCTree> l = trees; l.nonEmpty(); l = l.tail)
            count(l.head);
    }
    
    @Override
    public void visitTopLevel(JCCompilationUnit tree) {
        if (!this.visited.contains(tree)) {
            this.JCCompilationUnit_count++;
            this.visited.add(tree);
        }
        count(tree.packageAnnotations);
        count(tree.pid);
        count(tree.defs);
    }

    @Override
    public void visitImport(JCImport tree) {
        if (!this.visited.contains(tree)) this.JCImport_count++;
        count(tree.qualid);
    }

    @Override
    public void visitClassDef(JCClassDecl tree) {
        if (!this.visited.contains(tree)) this.JCClassDecl_count++;
        count(tree.mods);
        count(tree.typarams);
        count(tree.extending);
        count(tree.implementing);
        count(tree.defs);
    }

    @Override
    public void visitMethodDef(JCMethodDecl tree) {
        if (!this.visited.contains(tree)) this.JCMethodDecl_count++;
        count(tree.mods);
        count(tree.restype);
        count(tree.typarams);
        count(tree.params);
        count(tree.thrown);
        count(tree.body);
    }

    @Override
    public void visitVarDef(JCVariableDecl tree) {
        if (!this.visited.contains(tree)) this.JCVariableDecl_count++;
        count(tree.mods);
        count(tree.vartype);
        count(tree.init);
    }

    @Override
    public void visitSkip(JCSkip tree) {
        if (!this.visited.contains(tree)) this.JCSkip_count++;
    }

    @Override
    public void visitBlock(JCBlock tree) {
        if (!this.visited.contains(tree)) this.JCBlock_count++;
        count(tree.stats);
    }

    @Override
    public void visitDoLoop(JCDoWhileLoop tree) {
        if (!this.visited.contains(tree)) this.JCDoWhileLoop_count++;
        count(tree.body);
        count(tree.cond);
    }

    @Override
    public void visitWhileLoop(JCWhileLoop tree) {
        if (!this.visited.contains(tree)) this.JCWhileLoop_count++;
        count(tree.cond);
        count(tree.body);
    }

    @Override
    public void visitForLoop(JCForLoop tree) {
        if (!this.visited.contains(tree)) this.JCForLoop_count++;
        count(tree.init);
        count(tree.cond);
        count(tree.step);
        count(tree.body);
    }

    @Override
    public void visitForeachLoop(JCEnhancedForLoop tree) {
        if (!this.visited.contains(tree)) this.JCEnhancedForLoop_count++;
        count(tree.var);
        count(tree.expr);
        count(tree.body);
    }

    @Override
    public void visitLabelled(JCLabeledStatement tree) {
        if (!this.visited.contains(tree)) this.JCLabeledStatement_count++;
        count(tree.body);
    }

    @Override
    public void visitSwitch(JCSwitch tree) {
        if (!this.visited.contains(tree)) this.JCSwitch_count++;
        count(tree.selector);
        count(tree.cases);
    }

    @Override
    public void visitCase(JCCase tree) {
        if (!this.visited.contains(tree)) this.JCCase_count++;
        count(tree.pat);
        count(tree.stats);
    }

    @Override
    public void visitSynchronized(JCSynchronized tree) {
        if (!this.visited.contains(tree)) this.JCSynchronized_count++;
        count(tree.lock);
        count(tree.body);
    }

    @Override
    public void visitTry(JCTry tree) {
        if (!this.visited.contains(tree)) this.JCTry_count++;
        count(tree.body);
        count(tree.catchers);
        count(tree.finalizer);
    }

    @Override
    public void visitCatch(JCCatch tree) {
        if (!this.visited.contains(tree)) this.JCCatch_count++;
        count(tree.param);
        count(tree.body);
    }

    @Override
    public void visitConditional(JCConditional tree) {
        if (!this.visited.contains(tree)) this.JCConditional_count++;
        count(tree.cond);
        count(tree.truepart);
        count(tree.falsepart);
    }

    @Override
    public void visitIf(JCIf tree) {
        if (!this.visited.contains(tree)) this.JCIf_count++;
        count(tree.cond);
        count(tree.thenpart);
        count(tree.elsepart);
    }

    @Override
    public void visitExec(JCExpressionStatement tree) {
        if (!this.visited.contains(tree)) this.JCExpressionStatement_count++;
        count(tree.expr);
    }

    @Override
    public void visitBreak(JCBreak tree) {
        if (!this.visited.contains(tree)) this.JCBreak_count++;
    }

    @Override
    public void visitContinue(JCContinue tree) {
        if (!this.visited.contains(tree)) this.JCContinue_count++;
    }

    @Override
    public void visitReturn(JCReturn tree) {
        if (!this.visited.contains(tree)) this.JCReturn_count++;
        count(tree.expr);
    }

    @Override
    public void visitThrow(JCThrow tree) {
        if (!this.visited.contains(tree)) this.JCThrow_count++;
        count(tree.expr);
    }

    @Override
    public void visitAssert(JCAssert tree) {
        if (!this.visited.contains(tree)) this.JCAssert_count++;
        count(tree.cond);
        count(tree.detail);
    }

    @Override
    public void visitApply(JCMethodInvocation tree) {
        if (!this.visited.contains(tree)) this.JCMethodInvocation_count++;
        count(tree.meth);
        count(tree.args);
    }

    @Override
    public void visitNewClass(JCNewClass tree) {
        if (!this.visited.contains(tree)) this.JCNewClass_count++;
        count(tree.encl);
        count(tree.clazz);
        count(tree.args);
        count(tree.def);
    }

    @Override
    public void visitNewArray(JCNewArray tree) {
        if (!this.visited.contains(tree)) this.JCNewArray_count++;
        count(tree.elemtype);
        count(tree.dims);
        count(tree.elems);
    }

    @Override
    public void visitParens(JCParens tree) {
        if (!this.visited.contains(tree)) this.JCParens_count++;
        count(tree.expr);
    }

    @Override
    public void visitAssign(JCAssign tree) {
        if (!this.visited.contains(tree)) this.JCAssign_count++;
        count(tree.lhs);
        count(tree.rhs);
    }

    @Override
    public void visitAssignop(JCAssignOp tree) {
        if (!this.visited.contains(tree)) this.JCAssignOp_count++;
        count(tree.lhs);
        count(tree.rhs);
    }

    @Override
    public void visitUnary(JCUnary tree) {
        if (!this.visited.contains(tree)) this.JCUnary_count++;
        count(tree.arg);
    }

    @Override
    public void visitBinary(JCBinary tree) {
        if (!this.visited.contains(tree)) this.JCBinary_count++;
        count(tree.lhs);
        count(tree.rhs);
    }

    @Override
    public void visitTypeCast(JCTypeCast tree) {
        if (!this.visited.contains(tree)) this.JCTypeCast_count++;
        count(tree.clazz);
        count(tree.expr);
    }

    @Override
    public void visitTypeTest(JCInstanceOf tree) {
        if (!this.visited.contains(tree)) this.JCInstanceOf_count++;
        count(tree.expr);
        count(tree.clazz);
    }

    @Override
    public void visitIndexed(JCArrayAccess tree) {
        if (!this.visited.contains(tree)) this.JCArrayAccess_count++;
        count(tree.indexed);
        count(tree.index);
    }

    @Override
    public void visitSelect(JCFieldAccess tree) {
        if (!this.visited.contains(tree)) this.JCFieldAccess_count++;
        count(tree.selected);
    }

    @Override
    public void visitIdent(JCIdent tree) {
        if (!this.visited.contains(tree)) this.JCIdent_count++;
    }

    @Override
    public void visitLiteral(JCLiteral tree) {
        if (!this.visited.contains(tree)) this.JCLiteral_count++;
    }

    @Override
    public void visitTypeIdent(JCPrimitiveTypeTree tree) {
        if (!this.visited.contains(tree)) this.JCPrimitiveTypeTree_count++;
    }

    @Override
    public void visitTypeArray(JCArrayTypeTree tree) {
        if (!this.visited.contains(tree)) this.JCArrayTypeTree_count++;
        count(tree.elemtype);
    }

    @Override
    public void visitTypeApply(JCTypeApply tree) {
        if (!this.visited.contains(tree)) this.JCTypeApply_count++;
        count(tree.clazz);
        count(tree.arguments);
    }

    @Override
    public void visitTypeParameter(JCTypeParameter tree) {
        if (!this.visited.contains(tree)) this.JCTypeParameter_count++;
        count(tree.bounds);
    }

    @Override
    public void visitWildcard(JCWildcard tree) {
        if (!this.visited.contains(tree)) this.JCWildcard_count++;
        count(tree.kind);
        if (tree.inner != null)
            count(tree.inner);
    }

    @Override
    public void visitTypeBoundKind(TypeBoundKind that) {
        if (!this.visited.contains(that)) this.TypeBoundKind_count++;
    }

    @Override
    public void visitModifiers(JCModifiers tree) {
        if (!this.visited.contains(tree)) this.JCModifiers_count++;
        count(tree.annotations);
    }

    @Override
    public void visitAnnotation(JCAnnotation tree) {
        if (!this.visited.contains(tree)) this.JCAnnotaion_count++;
        count(tree.annotationType);
        count(tree.args);
    }

    @Override
    public void visitErroneous(JCErroneous tree) {
        if (!this.visited.contains(tree)) this.JCErroneous_count++;
    }

    @Override
    public void visitLetExpr(LetExpr tree) {
        if (!this.visited.contains(tree)) this.LetExpr_count++;
        count(tree.defs);
        count(tree.expr);
    }

    @Override
    public void visitTree(JCTree that) {
        assert false;
    }

}
