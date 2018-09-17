package com.github.yarbshk.optget.processor.run;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.*;

import java.util.Optional;

import static org.objectweb.asm.Opcodes.*;

public class OptionalGetterAdapter extends ClassVisitor {

    private String internalClassName;
    private boolean isAnnotationPresent;

    public OptionalGetterAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals(Type.getDescriptor(OptionalGetter.class))) {
            isAnnotationPresent = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        internalClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (isAnnotationPresent) {
            String optionalDescriptor = Type.getDescriptor(Optional.class);
            // Declare a getter method
            String getterName = "get" + StringUtils.capitalize(name);
            String getterSignature = "()" + optionalDescriptor;
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, getterName, getterSignature, null, null);
            // Load a reference onto the stack from a local variable #index
            mv.visitVarInsn(ALOAD, 0);
            // Get a value of an owner field
            mv.visitFieldInsn(GETFIELD, internalClassName, name, descriptor);
            // Invoke a static method and puts the result on the stack
            String optional = Type.getInternalName(Optional.class);
            String optionalSignature = "(" + Type.getDescriptor(Object.class) + ")" + optionalDescriptor;
            mv.visitMethodInsn(INVOKESTATIC, optional, "ofNullable", optionalSignature, false);
            // Return a reference from a method
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        return super.visitField(access, name, descriptor, signature, value);
    }
}
