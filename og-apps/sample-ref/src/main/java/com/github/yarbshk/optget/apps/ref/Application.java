package com.github.yarbshk.optget.apps.ref;

import static com.github.yarbshk.optget.commons.ReflectionUtils.listObjectFields;

public class Application {

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO(new Object(), "Baz", false);
        try {
            listObjectFields(targetDTO);
        } catch (NoSuchMethodException e) {
            // It needs a time to refactor source files therefore getter methods are not visible for the first time
            System.out.println("Getter methods are not generated yet. Please, run the app once more.");
        }
    }
}
