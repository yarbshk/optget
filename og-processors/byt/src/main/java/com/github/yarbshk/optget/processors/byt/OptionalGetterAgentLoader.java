package com.github.yarbshk.optget.processors.byt;

import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;

public class OptionalGetterAgentLoader {

    public static void loadAgent() {
        try {
            VirtualMachine vm = VirtualMachine.attach(getRunningVMPid());
            vm.loadAgent(System.getenv("OG_JARPATH"));
            vm.detach();
        } catch (AgentLoadException e) {
            throw new RuntimeException("The OG_JARPATH environment variable is not correct: " +
                    System.getenv("OG_JARPATH"), e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRunningVMPid() {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        return nameOfRunningVM.substring(0, nameOfRunningVM.indexOf('@'));
    }
}
