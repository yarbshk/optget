package com.github.yarbshk.optget.sample.run;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public class TargetDTO {

    private String obj;
    private boolean bool;

    public TargetDTO(String obj, boolean bool) {
        this.obj = obj;
        this.bool = bool;
    }

    public String getObj() {
        return obj;
    }
}
