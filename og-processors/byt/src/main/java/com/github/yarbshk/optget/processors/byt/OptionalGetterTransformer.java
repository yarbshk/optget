package com.github.yarbshk.optget.processors.byt;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class OptionalGetterTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        try {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, 0);
            OptionalGetterAdapter ca = new OptionalGetterAdapter(cw);
            cr.accept(ca, 0);
            return cw.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
