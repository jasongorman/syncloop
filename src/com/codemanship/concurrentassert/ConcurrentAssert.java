package com.codemanship.concurrentassert;

import java.util.List;
import java.util.function.Supplier;

public abstract class ConcurrentAssert {
    public static void eventually(List<Runnable> functions, Supplier<Boolean> assertion, int threadCount, long timeout) {
        new EventuallyAssertion(threadCount, timeout).eventually(functions, assertion);
    }

    public static void always(List<Runnable> functions, Supplier<Boolean> assertion, int threadCount, long timeout){
        new AlwaysAssertion(threadCount, timeout).always(functions, assertion);
    }
}
