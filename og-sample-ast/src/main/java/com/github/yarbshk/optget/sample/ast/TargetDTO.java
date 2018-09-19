package com.github.yarbshk.optget.sample.ast;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public class TargetDTO {

    private Object obj;
    private String str;
    private boolean bool;

    public TargetDTO(Object obj, String str, boolean bool) {
        this.obj = obj;
        this.str = str;
        this.bool = bool;
    }

    public String getStr() {
        return str;
    }
}
