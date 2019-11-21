package com.codemanship.concurrentassert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertTrue;

public class ConcurrentAssert {

    private final int threadCount;
    private ExecutorService executor;
    private long timeout;

    public ConcurrentAssert(int threadCount, long timeout){
        this.timeout = timeout;
        this.threadCount = threadCount;
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    public static void eventually(List<Runnable> functions, Supplier<Boolean> assertion, int threadCount, long timeout) {
        new ConcurrentAssert(threadCount, timeout).eventually(functions, assertion);
    }

    private void eventually(List<Runnable> functions, Supplier<Boolean> assertion) {
        startThreads(functions);
        assertTrue(assertion.get());
    }

    private void startThreads(List<Runnable> functions) {
        for (int i = 0; i < threadCount; i++) {
            for (Runnable function:
                 functions) {
                executor.submit(function);
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
