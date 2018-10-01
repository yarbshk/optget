package com.github.yarbshk.optget.apps.smplbyt;

import com.github.yarbshk.optget.commons.GenerateSample;
import com.github.yarbshk.optget.commons.SampleBuilder;
import com.github.yarbshk.optget.processors.byt.OptionalGetterAgent;

import static com.github.yarbshk.optget.commons.ReflectionUtils.printObjectFields;

@GenerateSample
public class Application {

    static {
        OptionalGetterAgent.init();
    }

    public static void main(String[] args) {
        try {
            printObjectFields(SampleBuilder.of(Application.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
