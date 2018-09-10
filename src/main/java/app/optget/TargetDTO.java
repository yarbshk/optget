package app.optget;

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public abstract class TargetDTO {

    private String field;

    public TargetDTO(String field) {
        this.field = field;
    }
}
