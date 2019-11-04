package com.codemanship.example;

import static com.codemanship.syncloop.SyncLoop.execute;

public class TruckLoader implements Runnable {

    private final LoadingBay bay;
    private final Truck truck;

    TruckLoader(LoadingBay bay, Truck truck) {
        this.bay = bay;
        this.truck = truck;
    }

    void loadTruck() {
        execute(() -> truck.load(bay.unload()))
                .until(truck::isLoaded, this);
    }

    @Override
    public void run() {
        loadTruck();
    }
}
