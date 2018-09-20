package lombok.javac.handlers;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
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
        for (JavacNode potentialField : annotationNode.up().down()) {
            if (potentialField.getKind() == AST.Kind.FIELD) {
                tryAddOptionalGetter(potentialField);
            }
        }
    }

    private static void tryAddOptionalGetter(JavacNode fieldNode) {
        JCMethodDecl optionalGetter = createOptionalGetter(fieldNode);
        JavacNode typeNode = fieldNode.up();
        if (!isMethodExist(optionalGetter, typeNode)) {
            injectMethod(typeNode, optionalGetter);
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

        String factoryMethodName = getOptionalFactoryMethodName(((JCVariableDecl)fieldNode.get()).vartype.type);
        JCExpression factoryMethod = genTypeRef(fieldNode, factoryMethodName);
        List<JCExpression> args = List.of(genTypeRef(fieldNode, fieldNode.getName()));
        JCMethodInvocation invocation = treeMaker.Apply(List.nil(), factoryMethod, args);
        JCBlock body = treeMaker.Block(0, List.of(treeMaker.Return(invocation)));

        return treeMaker.MethodDef(mods, name, resType, typarams, params, thrown, body, null);
    }

    private static String getOptionalFactoryMethodName(Type type) {
        String factoryMethodName = type.isPrimitive() ? "of" : "ofNullable";
        return String.format("java.util.Optional.%s", factoryMethodName);
    }

    private static boolean isMethodExist(JCMethodDecl method, JavacNode typeNode) {
        return !methodExists(method.name.toString(), typeNode, -1)
                .equals(MemberExistsResult.NOT_EXISTS);
    }
}
