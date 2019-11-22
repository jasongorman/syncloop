package com.codemanship.loadingbay;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.codemanship.concurrentassert.ConcurrentAssert.always;
import static com.codemanship.concurrentassert.ConcurrentAssert.eventually;

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

        bay = new LoadingBay(50);
        truck = new Truck(parcels.size());

        bayLoader = new BayLoader(bay, parcels);
        truckLoader = new TruckLoader(bay, truck);
    }

    @Test
    @Parameters(method="iterations")
    public void loadsTruck(int n) {
        eventually(Arrays.asList(bayLoader, truckLoader)
                , () -> bay.isEmpty() && truck.isLoaded(), 2, 1000);

    }

    @Test
    @Parameters(method="iterations")
    public void bayIsNeverOverloaded(int n) {
        always(Arrays.asList(bayLoader, truckLoader)
                , () -> bay.getParcelCount() <= 50, 2, 1000);

    }

    private Object[] iterations() {
        return IntStream.range(0, 1000).mapToObj((x) -> x).toArray();
    }

}
