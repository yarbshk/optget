package com.github.yarbshk.optget.sample.ref;

import java.lang.reflect.Method;

public class Application {

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO("Baz");
        Method method = targetDTO.getClass().getMethod("getField");
        System.out.println("Result is: " + method.invoke(targetDTO));
    }
}
