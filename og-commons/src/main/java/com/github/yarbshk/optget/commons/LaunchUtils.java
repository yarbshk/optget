package com.github.yarbshk.optget.commons;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LaunchUtils {
    public static void listObjectFields(Object object)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = object.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            Method getter = aClass.getMethod(getGetterName(field));
            System.out.println(field.getName() + ": " + getter.invoke(object));
        }
    }

    private static String getGetterName(Field field) {
        String prefix = field.getType().equals(boolean.class) ? "is" : "get";
        String suffix = StringUtils.capitalize(field.getName());
        return prefix + suffix;
    }
}
