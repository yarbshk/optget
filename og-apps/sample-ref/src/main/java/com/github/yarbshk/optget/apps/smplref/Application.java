package com.github.yarbshk.optget.apps.smplref;

import com.github.yarbshk.optget.commons.SampleBuilder;

import static com.github.yarbshk.optget.commons.ReflectionUtils.printObjectFields;

public class Application {

    public static void main(String[] args) {
        try {
            printObjectFields(SampleBuilder.of(Application.class));
        } catch (NoSuchMethodException e) {
            System.out.println("Getter methods are not generated yet. Please, run the app once more.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
