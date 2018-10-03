package com.github.yarbshk.optget.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.github.yarbshk.optget.commons.ProcessorUtils.makeGetterName;

public class ReflectionUtils {

    public static void printObjectFields(Object object)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = object.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            String getterName = makeGetterName(field.getName(), field.getType().equals(boolean.class));
            Method getter = aClass.getMethod(getterName);
            System.out.println(field.getName() + ": " + getter.invoke(object));
        }
    }
}
