package com.github.yarbshk.optget.sample.ast;

import java.lang.reflect.Method;

public class Application {

    public static void main(String[] args) throws Exception {
        // It isn't runnable from Intellij IDEA because it needs a plugin to run
        TargetDTO targetDTO = new TargetDTO("Foo");
        Method method = targetDTO.getClass().getMethod("getField");
        System.out.println("Result is: " + method.invoke(targetDTO));
    }
}
