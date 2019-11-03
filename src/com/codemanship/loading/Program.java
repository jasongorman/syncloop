package com.codemanship.loading;

import java.util.ArrayList;
import java.util.List;

public class Program {

    public static void main(String[] args){
        List<Parcel> parcels = new ArrayList<>();

        for(int i = 1; i <= 100000; i++){
            parcels.add(new Parcel());
        }

        LoadingBay bay = new LoadingBay(10);
        Truck truck = new Truck(parcels.size());

        Loader loader = new Loader(bay, parcels);
        Unloader unloader = new Unloader(bay, truck);

        new Thread(loader).start();
        new Thread(loader).start();
        new Thread(loader).start();
        new Thread(unloader).start();
        new Thread(unloader).start();
    }
}
