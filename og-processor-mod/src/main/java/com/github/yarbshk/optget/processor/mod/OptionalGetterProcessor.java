package com.github.yarbshk.optget.processor.mod;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.utils.SourceRoot;
import com.github.yarbshk.optget.annotation.OptionalGetter;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static com.github.javaparser.ast.Modifier.PUBLIC;

@SupportedAnnotationTypes("com.github.yarbshk.optget.annotation.OptionalGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class OptionalGetterProcessor extends AbstractProcessor {

    private static final String JAVA_SOURCE_FILE_EXTENSION = ".java";

    /**
     * Tries to add getter methods for fields of classes annotated with {@link OptionalGetter}.
     * WARNING: The processor modifies all annotated sources!
     * @param annotations annotations
     * @param roundEnv roundEnv
     * @return a status flag of the processor
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OptionalGetter.class)) {
            for (Path sourceRoot : getSourceRoots()) {
                try {
                    SourceRoot sr = new SourceRoot(sourceRoot);
                    CompilationUnit cu = sr.parse(getPackageName(element), getSourceFileName(element));
                    cu.findAll(FieldDeclaration.class)
                            .forEach(OptionalGetterProcessor::tryAddOptionalGetter);
                    sr.saveAll();
                } catch (ParseProblemException e) {
                    processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.WARNING, e.getMessage());
                }
            }
        }
        return true;
    }

    private static Path[] getSourceRoots() {
        String sourcePath = System.getProperty("optget.source.path", "src/main/java:src/test/java");
        return Arrays.stream(sourcePath.split(":"))
                .map(root -> Paths.get(root))
                .toArray(Path[]::new);
    }

    private String getPackageName(Element element) {
        return processingEnv.getElementUtils()
                .getPackageOf(element)
                .getQualifiedName()
                .toString();
    }

    private String getSourceFileName(Element element) {
        return element.getSimpleName()
                .toString() + JAVA_SOURCE_FILE_EXTENSION;
    }

    /**
     * Adds getter methods for each field variable if the such method doesn't exist yet.
     * @param field the declaration of a field in a class
     */
    private static void tryAddOptionalGetter(FieldDeclaration field) {
        if (field.getVariables().isEmpty()) return;
        ClassOrInterfaceDeclaration parentClass = field.getAncestorOfType(ClassOrInterfaceDeclaration.class)
                .orElseThrow(IllegalStateException::new);
        parentClass.tryAddImportToParentCompilationUnit(Optional.class);
        for (VariableDeclarator variable : field.getVariables()) { // Support for declaring multiple variables
            MethodDeclaration optionalGetter = createOptionalGetter(variable);
            if (!parentClass.getMethodsByName(optionalGetter.getNameAsString()).isEmpty()) continue;
            parentClass.addMember(optionalGetter);
        }
    }

    private static MethodDeclaration createOptionalGetter(VariableDeclarator variable) {
        MethodDeclaration method = new MethodDeclaration();
        method.setModifier(PUBLIC, true);
        method.setType("Optional<" + variable.getTypeAsString() + ">");
        method.setName("get" + StringUtils.capitalize(variable.getNameAsString()));
        method.setBody(JavaParser.parseBlock("{return Optional.ofNullable(" + variable.getNameAsString() + ");}"));
        return method;
    }
}