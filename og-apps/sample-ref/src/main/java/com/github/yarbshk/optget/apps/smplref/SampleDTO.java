package com.github.yarbshk.optget.apps.smplref;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public class SampleDTO {

    private Object obj;
    private String str;
    private boolean bool;

    public String getStr() {
        return str;
    }
}
