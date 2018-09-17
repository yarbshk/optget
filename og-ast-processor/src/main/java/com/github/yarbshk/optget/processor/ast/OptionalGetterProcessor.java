package com.github.yarbshk.optget.processor.ast;

import com.github.yarbshk.optget.annotation.OptionalGetter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("com.github.yarbshk.optget.annotation.OptionalGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class OptionalGetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OptionalGetter.class)) {
            // TODO: processing
        }
        return true;
    }
}
