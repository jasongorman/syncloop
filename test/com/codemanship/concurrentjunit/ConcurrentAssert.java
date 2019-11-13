package com.codemanship.concurrentjunit;

import java.util.List;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class ConcurrentAssert {

    private final int threadCount;
    private static ThreadPool threadPool;
    private long timeout;
    private int running;

    public ConcurrentAssert(int threadCount, long timeout, ThreadPool threadPool){
        this.timeout = timeout;
        this.threadCount = threadCount;
        this.threadPool = threadPool;
    }

    public static void assertConcurrent(List<Runnable> functions, Supplier<Boolean> assertion, int threadCount, long timeout, ThreadPool threadPool) {
        new ConcurrentAssert(threadCount, timeout, threadPool).assertConcurrent(functions, assertion);
    }

    private void assertConcurrent(List<Runnable> functions, Supplier<Boolean> assertion) {
        for (Runnable function:
             functions) {
            startThreads(() -> {
                function.run();
                running--;
            });
        }

        waitForEnd(timeout);
        assertTrue(assertion.get());
    }

    private void waitForEnd(long timeout) {
        while(running > 0){
            if (timedOut()) break;
        }
    }

    private boolean timedOut() {
        long start = System.currentTimeMillis();
        if(System.currentTimeMillis() - start > timeout){
            fail("Test timed out");
            return true;
        }
        return false;
    }

    private void startThreads(Runnable function) {
        for (int i = 0; i < threadCount; i++) {
            threadPool.submit(function);
            running++;
        }
    }

}
