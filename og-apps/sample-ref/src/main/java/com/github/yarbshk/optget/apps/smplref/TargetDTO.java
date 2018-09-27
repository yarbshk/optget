package com.github.yarbshk.optget.apps.smplref;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import java.util.Optional;

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

    public Optional getObj() {
        return Optional.ofNullable(obj);
    }

    public Optional isBool() {
        return Optional.of(bool);
    }
}
