package com.github.yarbshk.optget.processor.run;

import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;

public class OptionalGetterAgentLoader {

    public static void loadAgent() {
        try {
            VirtualMachine vm = VirtualMachine.attach(getRunningVMPid());
            vm.loadAgent(getAgentPath());
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRunningVMPid() {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        return nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
    }

    private static String getAgentPath() {
        // TODO: getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
        return "/home/dev/Documents/Sandbox/optget/og-processor-run/build/libs/og-processor-run-1.0-SNAPSHOT.jar";
    }
}
