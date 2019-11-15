package com.codemanship.loadingbay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Program {

    private static ExecutorService executor;

    public static void main(String[] args){
        executor = Executors.newFixedThreadPool(2);

        IntStream.range(0,10000).forEach((x) -> loadTruck());

        executor.shutdown();
    }

    public static void loadTruck() {
        List<Parcel> parcels = new ArrayList<>();

        for(int i = 1; i <= 1000; i++){
            parcels.add(new Parcel());
        }

        LoadingBay bay = new LoadingBay(10);
        Truck truck = new Truck(parcels.size());

        BayLoader bayLoader = new BayLoader(bay, parcels);
        TruckLoader truckLoader = new TruckLoader(bay, truck);

        executor.submit(bayLoader);
        executor.submit(truckLoader);
    }
}
