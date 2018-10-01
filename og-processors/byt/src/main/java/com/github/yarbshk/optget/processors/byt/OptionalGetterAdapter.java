package com.github.yarbshk.optget.processors.byt;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import org.objectweb.asm.*;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

import static com.github.yarbshk.optget.commons.ProcessorUtils.buildGetterName;
import static org.objectweb.asm.Opcodes.*;

public class OptionalGetterAdapter extends ClassVisitor {

    private String internalClassName;
    private boolean isAnnotationPresent;

    public OptionalGetterAdapter(ClassVisitor cv) {
        super(ASM6, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        internalClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals(Type.getDescriptor(OptionalGetter.class))) {
            isAnnotationPresent = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (isAnnotationPresent) {
            tryAddOptionalGetter(name, descriptor);
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    private void tryAddOptionalGetter(String name, String descriptor) { // TODO: add if doesn't exist
        String optionalDescriptor = Type.getDescriptor(Optional.class);
        // Declare a getter method
        String getterName = toGetterName(name, descriptor);
        String getterSignature = "()" + optionalDescriptor;
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, getterName, getterSignature, null, null);
        // Load a reference onto the stack from a local variable
        mv.visitVarInsn(ALOAD, 0);
        // Put a field value onto the stack
        mv.visitFieldInsn(GETFIELD, internalClassName, name, descriptor);
        // Convert the field value to a wrapper object if the value is a primitive
        convertPrimitiveToWrapperObject(mv, descriptor);
        // Invoke a static method and puts the result onto the stack
        String optional = Type.getInternalName(Optional.class);
        String optionalSignature = "(" + Type.getDescriptor(Object.class) + ")" + optionalDescriptor;
        String factoryMethodName = getOptionalFactoryMethodName(descriptor);
        mv.visitMethodInsn(INVOKESTATIC, optional, factoryMethodName, optionalSignature, false);
        // Return an optional object of the value
        mv.visitInsn(ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private static String toGetterName(String name, String descriptor) {
        boolean logical = Objects.equals(descriptor, Type.getDescriptor(boolean.class));
        return buildGetterName(name, logical);
    }

    private static void convertPrimitiveToWrapperObject(MethodVisitor mv, String descriptor) {
        switch (Type.getType(descriptor).getSort()) {
            case Type.BOOLEAN:
                invokeValueOf(mv, descriptor, Boolean.class);
                break;
            case Type.BYTE:
                invokeValueOf(mv, descriptor, Byte.class);
                break;
            case Type.CHAR:
                invokeValueOf(mv, descriptor, Character.class);
                break;
            case Type.SHORT:
                invokeValueOf(mv, descriptor, Short.class);
                break;
            case Type.INT:
                invokeValueOf(mv, descriptor, Integer.class);
                break;
            case Type.FLOAT:
                invokeValueOf(mv, descriptor, Float.class);
                break;
            case Type.LONG:
                invokeValueOf(mv, descriptor, Long.class);
                break;
            case Type.DOUBLE:
                invokeValueOf(mv, descriptor, Double.class);
                break;
        }
    }

    private static void invokeValueOf(MethodVisitor mv, String primitiveDesc, Class<?> aClass) {
        String owner = Type.getInternalName(aClass);
        String descriptor = String.format("(%s)%s", primitiveDesc, Type.getDescriptor(aClass));
        mv.visitMethodInsn(INVOKESTATIC, owner, "valueOf", descriptor, false);
    }

    private static String getOptionalFactoryMethodName(String descriptor) {
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
}
