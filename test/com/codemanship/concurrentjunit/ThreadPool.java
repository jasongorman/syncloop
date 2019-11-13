package com.codemanship.concurrentjunit;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    private final List<Thread> threads;
    private final Queue<Runnable> queue;
    private Boolean terminated;

    public ThreadPool(int threadCount){
        terminated = false;
        threads = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();

        Runnable loop = () -> {

            while(!terminated){

                Runnable task = null;

                //synchronized (this) {
                if(!queueEmpty()) {
                    task = queue.remove();
                }
                //}

                if(task != null) {
                    task.run();
                }

            }
        };

        for (int i = 0; i < threadCount; i++){
            Thread thread = new Thread(loop);
            thread.start();
            threads.add(thread);
        }

        System.out.println("Thread pool started. Active threads = " + threads.size());
    }

    private boolean queueEmpty() {
        return queue.isEmpty();
    }

    public void stop(){
        terminated = true;
    }

    public void submit(Runnable task){
        queue.add(task);
    }
}

