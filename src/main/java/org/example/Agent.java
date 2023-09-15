package org.example;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("====== Hook godzilla ======");
        instrumentation.addTransformer(new MyTransformerSkipVerify(), true);   //取消jar验证
        Class[] allLoadedClasses = instrumentation.getAllLoadedClasses();
        for (Class loadedClass : allLoadedClasses) {
            if (loadedClass.getName() == "core.ApplicationConfig") {
                try {
                    instrumentation.retransformClasses(loadedClass);
                    System.out.println("Hook Successfully.");
                } catch (UnmodifiableClassException e) {
                    e.printStackTrace();
                    System.out.println("Hook Failed.");
                }
            }
        }
    }
}
