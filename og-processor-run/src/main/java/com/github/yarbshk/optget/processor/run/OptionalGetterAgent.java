package com.github.yarbshk.optget.processor.run;

import java.lang.instrument.Instrumentation;

public class OptionalGetterAgent {

    private static boolean initialized;

    public static void init() {
        if (initialized) return;
        OptionalGetterAgentLoader.loadAgent();
        initialized = true;
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new OptionalGetterTransformer());
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new OptionalGetterTransformer());
    }
}
