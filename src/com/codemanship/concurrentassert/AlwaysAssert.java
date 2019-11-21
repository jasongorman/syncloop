package com.codemanship.concurrentassert;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

class AlwaysAssert {

    private final int threadCount;
    private ExecutorService executor;
    private long timeout;
    private boolean running;
    private boolean passed;

    AlwaysAssert(int threadCount, long timeout){
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

        Runnable always = () -> {
            while (running) {
                passed = passed && assertion.get();
            }
        };

        ExecutorService alwaysExecutor = Executors.newFixedThreadPool(1);
        alwaysExecutor.submit(always);

        for (int i = 0; i < threadCount; i++) {
            for (Runnable function:
                 functions) {
                executor.submit(function);
            }
        }
        executor.shutdown();
        alwaysExecutor.shutdown();

        try {
//            alwaysExecutor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            running = false;
        }

        if(!passed){
            fail();
        }
    }

}
