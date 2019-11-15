package com.codemanship.loadingbay;

import java.util.ArrayList;
import java.util.List;

class Truck {

    private final int manifestTotal;
    private List<Parcel> manifest = new ArrayList<>();

    Truck(int manifestTotal){
        this.manifestTotal = manifestTotal;
    }

    void load(Parcel parcel) {
        manifest.add(parcel);
    }

    Boolean isLoaded(){
        if(manifest.size() == manifestTotal){
            System.out.println("TRUCK LOADED");
        }
        return manifest.size() == manifestTotal;
    }
}
