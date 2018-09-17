package com.github.yarbshk.optget.sample.ast;

import java.lang.reflect.Method;

public class Application {

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO("Foo");
        Method method = targetDTO.getClass().getMethod("getField");
        System.out.print("Result is: " + method.invoke(targetDTO));
    }
}
