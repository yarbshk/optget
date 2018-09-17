package com.github.yarbshk.optget.sample.run;

import com.github.yarbshk.optget.processor.run.OptionalGetterAgent;

import java.lang.reflect.Method;

public class Application {

    static {
        OptionalGetterAgent.init();
    }

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO("Bar");
        Method method = targetDTO.getClass().getMethod("getField");
        System.out.println("Result is: " + method.invoke(targetDTO));
    }
}
