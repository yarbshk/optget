package com.github.yarbshk.optget.sample.ref;

import com.github.yarbshk.optget.annotation.OptionalGetter;

import java.util.Optional;

@OptionalGetter
public class TargetDTO {

    private String field;

    public TargetDTO(String field) {
        this.field = field;
    }

    public Optional<String> getField() {
        return Optional.ofNullable(field);
    }
}
