// The processor resolves and inserts "import package.name;" automatically

import com.github.yarbshk.optget.annotation.OptionalGetter;

@OptionalGetter
public class SampleDTO {

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
