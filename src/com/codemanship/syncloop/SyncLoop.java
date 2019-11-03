package com.codemanship.syncloop;

import java.util.function.Supplier;

public abstract class SyncLoop {

    public static RunnableLoop execute(Runnable action)
    {
        return new RunnableLoop(action);
    }

    public static <T> SupplierLoop<T> execute(Supplier<T> supplier){
        return new SupplierLoop<>(supplier);
    }

}
