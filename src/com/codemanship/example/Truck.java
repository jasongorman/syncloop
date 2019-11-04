package com.codemanship.example;

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
        System.out.println("PARCEL LOADED: " + manifest.size());
    }

    Boolean isLoaded(){
        if(manifest.size() == manifestTotal){
            System.out.println("TRUCK LOADED");
        }
        return manifest.size() == manifestTotal;
    }
}
