package com.github.yarbshk.optget.processors.byt;

import com.github.yarbshk.optget.annotation.OptionalGetter;
import org.objectweb.asm.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.yarbshk.optget.processors.byt.VisitorUtils.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * All visitor methods are executing in special order (from top to bottom as is)
 * therefore to make desirable modification it's necessary to collect information from ones at first.
 */
public class OptionalGetterAdapter extends ClassVisitor {

    private String internalClassName;
    private boolean isAnnotationPresent;
    private Map<String, String> fields = new HashMap<>();

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
            fields.put(name, descriptor);
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                                     String[] exceptions) {
        if (name.matches("^(get|is).*$")) {
            fields.remove(toFieldName(name));
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        fields.forEach(this::addOptionalGetter);
        super.visitEnd();
    }

    private void addOptionalGetter(String name, String descriptor) {
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
        convertPrimitiveToWrapper(mv, descriptor);
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
}
