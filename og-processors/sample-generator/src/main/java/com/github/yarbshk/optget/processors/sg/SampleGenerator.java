package com.github.yarbshk.optget.processors.sg;

import com.github.yarbshk.optget.commons.GenerateSample;
import com.github.yarbshk.optget.commons.SampleBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The project consists from the three sample projects (see the :og-apps module)
 * each of its needs a DTO to illustrate how a specific annotation processor works.
 * So {@link SampleGenerator} generates such DTOs for us automatically.
 */
@SupportedAnnotationTypes("com.github.yarbshk.optget.commons.GenerateSample")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SampleGenerator extends AbstractProcessor {

    private static final String TEMPLATE_NAME = "template.java";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateSample.class)) {
            Map<String, String> values = getTemplateValues(element);
            try {
                Writer writer = processingEnv.getFiler()
                        .createSourceFile(values.get("className")) // Generate a DTO source file
                        .openWriter();
                writer.write(getSourceFileContent(values));
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private Map<String, String> getTemplateValues(Element element) {
        Elements elUtils = processingEnv.getElementUtils();
        Map<String, String> values = new HashMap<>();
        values.put("packageName", elUtils.getPackageOf(element).getQualifiedName().toString());
        values.put("className", SampleBuilder.CLASS_NAME);
        return values;
    }

    private String getSourceFileContent(Map<String, String> values) throws IOException {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(TEMPLATE_NAME);
        StringSubstitutor sub = new StringSubstitutor(values);
        return sub.replace(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
    }
}
