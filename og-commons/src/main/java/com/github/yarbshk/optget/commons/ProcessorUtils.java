package com.github.yarbshk.optget.commons;

import org.apache.commons.lang3.StringUtils;

public class ProcessorUtils {

    public static String buildGetterName(String fieldName, boolean logical) {
        String prefix = logical ? "is" : "get";
        String capitalizedFieldName = StringUtils.capitalize(fieldName);
        return prefix + capitalizedFieldName;
    }
}
