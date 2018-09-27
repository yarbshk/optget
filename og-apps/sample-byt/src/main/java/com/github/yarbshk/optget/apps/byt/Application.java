package com.github.yarbshk.optget.apps.byt;

import com.github.yarbshk.optget.processors.byt.OptionalGetterAgent;

import static com.github.yarbshk.optget.commons.ReflectionUtils.listObjectFields;

public class Application {

    static {
        OptionalGetterAgent.init();
    }

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO(new Object(), "Bar", false);
        listObjectFields(targetDTO);
    }
}
