package com.github.yarbshk.optget.sample.run;

import com.github.yarbshk.optget.processor.run.OptionalGetterAgent;

public class Application {

    static {
        OptionalGetterAgent.init();
    }

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO(new Object(), "Baz", false);
        System.out.println("obj: " + TargetDTO.class.getMethod("getObj").invoke(targetDTO));
        System.out.println("str: " + TargetDTO.class.getMethod("getStr").invoke(targetDTO));
        System.out.println("bool: " + TargetDTO.class.getMethod("isBool").invoke(targetDTO));
    }
}
