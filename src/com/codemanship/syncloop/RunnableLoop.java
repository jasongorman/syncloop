package com.codemanship.syncloop;

import java.util.function.Supplier;

public class RunnableLoop {
    private final Runnable action;

    public RunnableLoop(Runnable action) {
        this.action = action;
    }

    public void until(Supplier<Boolean> condition, Object locked) {
        while(true){
            synchronized (locked) {
                if (condition.get()) break;
                else {
                    action.run();
                }
            }
        }
    }

    public void when(Supplier<Boolean> condition, Object locked) {
        while(true){
            synchronized (locked) {
                if (condition.get()) {
                    action.run();
                    break;
                }
            }
        }
    }
}
