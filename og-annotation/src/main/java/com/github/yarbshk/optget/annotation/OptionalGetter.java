package com.github.yarbshk.optget.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// NOTE: The ASM library (see the :og-processors:byt module) works during runtime,
// therefore it's required to set the retention policy to RUNTIME instead of SOURCE
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OptionalGetter {
}
