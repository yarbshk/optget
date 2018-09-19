package lombok.javac.handlers;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import lombok.core.AST;
import lombok.core.AnnotationValues;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;

import static lombok.javac.handlers.JavacHandlerUtil.*;

public class OptionalGetterHandler extends JavacAnnotationHandler<OptionalGetter> {

    @Override
    public void handle(AnnotationValues<OptionalGetter> annotation, JCAnnotation ast, JavacNode annotationNode) {
        deleteAnnotationIfNeccessary(annotationNode, OptionalGetter.class);
        JavacNode typeNode = annotationNode.up();
        for (JavacNode potentialField : typeNode.down()) { // Enumerate type children
            if (potentialField.getKind() != AST.Kind.FIELD) continue;
            JCMethodDecl getter = createOptionalGetter(potentialField);
            MemberExistsResult memberExistsResult = methodExists(getter.name.toString(), typeNode, 0);
            if (memberExistsResult != MemberExistsResult.NOT_EXISTS) continue;
            injectMethod(typeNode, getter);
        }
    }

    private static JCMethodDecl createOptionalGetter(JavacNode fieldNode) {
        JavacTreeMaker treeMaker = fieldNode.getTreeMaker();

        JCModifiers           mods     = treeMaker.Modifiers(Flags.PUBLIC);
        Name                  name     = fieldNode.toName(toGetterName(fieldNode));
        JCExpression          resType  = genTypeRef(fieldNode, "java.util.Optional");
        List<JCTypeParameter> typarams = List.nil();
        List<JCVariableDecl>  params   = List.nil();
        List<JCExpression>    thrown   = List.nil();

        JCExpression ofNullable = genTypeRef(fieldNode, "java.util.Optional.ofNullable");
        List<JCExpression> args = List.of(genTypeRef(fieldNode, fieldNode.getName()));
        JCMethodInvocation invocation = treeMaker.Apply(List.nil(), ofNullable, args);
        JCBlock body = treeMaker.Block(0, List.of(treeMaker.Return(invocation)));

        return treeMaker.MethodDef(mods, name, resType, typarams, params, thrown, body, null);
    }
}
