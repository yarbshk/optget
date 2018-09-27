package com.github.yarbshk.optget.apps.ref;

import static com.github.yarbshk.optget.commons.LaunchUtils.listObjectFields;

public class Application {

    public static void main(String[] args) throws Exception {
        TargetDTO targetDTO = new TargetDTO(new Object(), "Baz", false);
        listObjectFields(targetDTO);
    }
}
