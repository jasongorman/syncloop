package com.codemanship.example;

import com.codemanship.concurrentjunit.ConcurrentAssert;
import com.codemanship.concurrentjunit.ThreadPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadingTest {

    private static ThreadPool pool;

    @BeforeClass
    public static void setUp(){
        pool = new ThreadPool(2);
    }

    @AfterClass
    public static void tearDown(){
        pool.stop();
    }

    @Test
    public void loadsTruck(){
        List<Parcel> parcels = new ArrayList<>();

        for(int i = 1; i <= 100000; i++){
            parcels.add(new Parcel());
        }

        LoadingBay bay = new LoadingBay(10);
        Truck truck = new Truck(parcels.size());

        BayLoader loader = new BayLoader(bay, parcels);
        TruckLoader unloader = new TruckLoader(bay, truck);

        ThreadPool pool = new ThreadPool(2);

        ConcurrentAssert.assertConcurrent(Arrays.asList(loader, unloader), () -> truck.isLoaded(), 1, 1000, pool);
    }
}
