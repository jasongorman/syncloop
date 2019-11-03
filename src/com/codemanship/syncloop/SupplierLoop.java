package com.codemanship.syncloop;

import java.util.function.Supplier;

public class SupplierLoop<T> {

    private final Supplier<T> supplier;

    public SupplierLoop(Supplier<T> supplier){
        this.supplier = supplier;
    }

    public T until(Supplier<Boolean> condition, Object locked) {
        T result = null;

        while(true){
            synchronized (locked) {
                if (condition.get()) break;
                else {
                    result = supplier.get();
                }
            }
        }
        return result;
    }

    public T when(Supplier<Boolean> condition, Object locked) {
        T result;
        while(true){
            synchronized (locked) {
                if (condition.get()) {
                    result = supplier.get();
                    break;
                }
            }
        }
        return result;    }
}
