package com.codemanship.example;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertTrue;

public class LoadingTest {

    private ExecutorService executor;
    private LoadingBay bay;
    private Truck truck;

    @Test
    public void loadsTruck() {
        for (int x = 0; x < 10000; x++) {
            repeat();
        }
    }

    public void repeat() {

        List<Parcel> parcels = new ArrayList<>();

        for(int i = 1; i <= 1000; i++){
            parcels.add(new Parcel());
        }

        bay = new LoadingBay(10);
        truck = new Truck(parcels.size());

        BayLoader loader = new BayLoader(bay, parcels);
        TruckLoader unloader = new TruckLoader(bay, truck);

        executor = Executors.newFixedThreadPool(2);
        executor.submit(loader);
        executor.submit(unloader);

        executor.shutdown();

        try {
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(bay.isEmpty());
        assertTrue(truck.isLoaded());
    }
}
