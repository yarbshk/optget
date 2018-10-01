package com.github.yarbshk.optget.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

import java.io.IOException;
import java.io.InputStream;

/**
 * Instantiate the generated DTO from a JSON file for convenience.
 */
public class SampleBuilder {

    public static final String CLASS_NAME = "SampleDTO";

    private InputStream jsonInputStream;
    private String packageName;

    public SampleBuilder setJsonInputStream(InputStream jsonInputStream) {
        this.jsonInputStream = jsonInputStream;
        return this;
    }

    public SampleBuilder setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public Object build() {
        String canonicalClassName = this.packageName + "." + CLASS_NAME;
        ObjectMapper mapper = new ObjectMapper();
        VisibilityChecker<?> visibilityChecker = mapper.getVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(visibilityChecker);
        try {
            return mapper.readValue(this.jsonInputStream, Class.forName(canonicalClassName));
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object of(Class<?> aClass) {
        return new SampleBuilder()
                .setJsonInputStream(aClass.getClassLoader().getResourceAsStream(CLASS_NAME + ".json"))
                .setPackageName(aClass.getPackage().getName())
                .build();
    }
}
