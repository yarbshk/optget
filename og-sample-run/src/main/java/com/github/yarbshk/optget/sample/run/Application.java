package com.github.yarbshk.optget.sample.run;

import com.github.yarbshk.optget.processor.run.OptionalGetterAgent;

public class Application {

    static {
        OptionalGetterAgent.init();
    }

    public static void main(String[] args) throws Exception {
        Object x = 1;
        TargetDTO targetDTO = new TargetDTO("Bar", false);
        System.out.println("obj: " + TargetDTO.class.getMethod("getObj").invoke(targetDTO));
        System.out.println("bool: " + TargetDTO.class.getMethod("isBool").invoke(targetDTO));
    }
}
