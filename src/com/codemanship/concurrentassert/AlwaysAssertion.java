package com.codemanship.concurrentassert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

class AlwaysAssertion {

    private final int threadCount;
    private ExecutorService executor;
    private final long timeout;
    private boolean running;
    private boolean passed;

    AlwaysAssertion(int threadCount, long timeout){
        this.timeout = timeout;
        this.threadCount = threadCount;
        this.executor = Executors.newFixedThreadPool(threadCount);
        this.passed = true;
        this.running = true;
    }

    void always(List<Runnable> functions, Supplier<Boolean> assertion) {
        startThreads(functions, assertion);
    }

    private void startThreads(List<Runnable> functions, Supplier<Boolean> assertion) {

        ExecutorService alwaysExecutor = startAssertionThread(assertion);
        startThreadsUnderTest(functions);
        executor.shutdown();
        alwaysExecutor.shutdown();
        awaitThreadsUnderTest();

        if(!passed){
            fail();
        }
    }

    private void awaitThreadsUnderTest() {
        try {
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            running = false;
        }
    }

    private void startThreadsUnderTest(List<Runnable> functions) {
        for (int i = 0; i < threadCount; i++) {
            for (Runnable function:
                 functions) {
                executor.submit(function);
            }
        }
    }

    private ExecutorService startAssertionThread(Supplier<Boolean> assertion) {
        Runnable always = () -> {
            while (running) {
                passed = passed && assertion.get();
            }
        };

        ExecutorService alwaysExecutor = Executors.newFixedThreadPool(1);
        alwaysExecutor.submit(always);
        return alwaysExecutor;
    }

}
