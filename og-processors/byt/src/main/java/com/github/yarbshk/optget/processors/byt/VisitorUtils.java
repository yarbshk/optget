package com.github.yarbshk.optget.processors.byt;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.github.yarbshk.optget.commons.ProcessorUtils.buildGetterName;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class VisitorUtils {

    public static String toGetterName(String name, String descriptor) {
        boolean logical = Objects.equals(descriptor, Type.getDescriptor(boolean.class));
        return buildGetterName(name, logical);
    }

    public static String toFieldName(String getterName) {
        String fieldName = getterName.replaceFirst("(get|is)", "");
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
    }

    public static String getOptionalFactoryMethodName(String descriptor) {
        return isPrimitive(descriptor) ? "of" : "ofNullable";
    }

    private static boolean isPrimitive(String descriptor) {
        try {
            Field field = Type.class.getDeclaredField("PRIMITIVE_DESCRIPTORS");
            field.setAccessible(true);
            return field.get(null).toString().contains(descriptor);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertPrimitiveToWrapper(MethodVisitor mv, String descriptor) {
        switch (Type.getType(descriptor).getSort()) {
            case Type.BOOLEAN:
                toWrapper(mv, descriptor, Boolean.class);
                break;
            case Type.BYTE:
                toWrapper(mv, descriptor, Byte.class);
                break;
            case Type.CHAR:
                toWrapper(mv, descriptor, Character.class);
                break;
            case Type.SHORT:
                toWrapper(mv, descriptor, Short.class);
                break;
            case Type.INT:
                toWrapper(mv, descriptor, Integer.class);
                break;
            case Type.FLOAT:
                toWrapper(mv, descriptor, Float.class);
                break;
            case Type.LONG:
                toWrapper(mv, descriptor, Long.class);
                break;
            case Type.DOUBLE:
                toWrapper(mv, descriptor, Double.class);
                break;
        }
    }

    private static void toWrapper(MethodVisitor mv, String desc, Class<?> aClass) {
        String owner = Type.getInternalName(aClass);
        String descriptor = String.format("(%s)%s", desc, Type.getDescriptor(aClass));
        mv.visitMethodInsn(INVOKESTATIC, owner, "valueOf", descriptor, false);
    }
}
