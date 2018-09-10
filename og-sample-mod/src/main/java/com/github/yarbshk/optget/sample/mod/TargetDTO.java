package com.github.yarbshk.optget.sample.mod;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public abstract class TargetDTO {

    private String field;

    public TargetDTO(String field) {
        this.field = field;
    }
}
