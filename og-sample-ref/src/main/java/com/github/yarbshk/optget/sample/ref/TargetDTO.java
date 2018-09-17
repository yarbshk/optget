package com.github.yarbshk.optget.sample.ref;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public class TargetDTO {

    private String field;

    public TargetDTO(String field) {
        this.field = field;
    }
}
