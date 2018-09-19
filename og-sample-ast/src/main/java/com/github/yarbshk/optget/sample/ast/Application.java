package com.github.yarbshk.optget.sample.ast;

public class Application {

    public static void main(String[] args) throws Exception {
        // It isn't runnable from Intellij IDEA because it needs a plugin to run successfully
        TargetDTO targetDTO = new TargetDTO(new Object(), "Foo", false);
        System.out.println("obj: " + TargetDTO.class.getMethod("getObj").invoke(targetDTO));
        System.out.println("str: " + TargetDTO.class.getMethod("getStr").invoke(targetDTO));
        System.out.println("bool: " + TargetDTO.class.getMethod("isBool").invoke(targetDTO));
    }
}
