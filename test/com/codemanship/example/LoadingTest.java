package com.codemanship.example;

import com.codemanship.com.codemanship.concurrentassert.ConcurrentAssert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.codemanship.com.codemanship.concurrentassert.ConcurrentAssert.assertConcurrent;

@RunWith(JUnitParamsRunner.class)
public class LoadingTest {

    private LoadingBay bay;
    private Truck truck;
    private BayLoader bayLoader;
    private TruckLoader truckLoader;

    @Before
    public void setUp() {
        List<Parcel> parcels = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            parcels.add(new Parcel());
        }

        bay = new LoadingBay(10);
        truck = new Truck(parcels.size());

        bayLoader = new BayLoader(bay, parcels);
        truckLoader = new TruckLoader(bay, truck);
    }

    @Test
    @Parameters
    public void loadsTruck(int x) {
        assertConcurrent(Arrays.asList(bayLoader, truckLoader)
                , () -> bay.isEmpty() && truck.isLoaded(), 4, 1000);

    }

    private Object[] parametersForLoadsTruck() {
        return IntStream.range(0, 10000).mapToObj((x) -> x).toArray();
    }

}
