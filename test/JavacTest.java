import java.io.*;
import com.sun.tools.javac.Main;
import org.junit.Test;
import static org.junit.Assert.*;

public class JavacTest {
    
    String[] expect = new String[] {
        "JCAnnotation 0",
        "JCArrayAccess 0",
        "JCArrayTypeTree 1",
        "JCAssert 0",
        "JCAssign 1",
        "JCAssignOp 0",
        "JCBinary 0",
        "JCBlock 6", 
        "JCBreak 0",
        "JCCase 0",
        "JCCatch 0",
        "JCClassDecl 3",
        "JCCompilationUnit 2",
        "JCConditional 0",
        "JCContinue 0",
        "JCDoWhileLoop 0",
        "JCEnhancedForLoop 0",
        "JCErroneous 0",
        "JCExpressionStatement 8",
        "JCFieldAccess 9",
        "JCForLoop 0",
        "JCIdent 19",
        "JCIf 0",
        "JCImport 1",
        "JCInstanceOf 0",
        "JCLabeledStatement 0",
        "JCLiteral 5",
        "JCMethodDecl 5",
        "JCMethodInvocation 8",
        "JCModifiers 11",
        "JCNewArray 0",
        "JCNewClass 1",
        "JCParens 0",
        "JCPrimitiveTypeTree 2",
        "JCReturn 0",
        "JCSkip 0",
        "JCSwitch 0",
        "JCSynchronized 0",
        "JCThrow 0",
        "JCTry 0",
        "JCTypeApply 2",
        "JCTypeCast 0",
        "JCTypeParameter 0",
        "JCUnary 0",
        "JCVariableDecl 3",
        "JCWhileLoop 0",
        "JCWildcard 0",
        "LetExpr 0",
        "TypeBoundKind 0",
    };

    @Test
    public void testMain() throws Exception {
        String groupID = new BufferedReader(
                         new FileReader("test" + File.separatorChar + "groupID.txt")).readLine();
        
        String testCaseDir = "test" + File.separatorChar + "case1" + File.separatorChar;
        Main.compile(new String[]{testCaseDir + "Main.java", testCaseDir + "Out.java"});

        FileInputStream fis = new FileInputStream("test" + File.separatorChar + "result_" + groupID);
        ObjectInputStream ois = new ObjectInputStream(fis);
        String[] result = (String[]) ois.readObject();
        assertArrayEquals(expect, result);
    }
}
