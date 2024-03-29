package com.codemanship.loadingbay;

import java.util.ArrayList;
import java.util.List;

import static com.codemanship.syncloop.SyncLoop.execute;

class LoadingBay {

    private final int capacity;
    private List<Parcel> parcels = new ArrayList<>();

    LoadingBay(int capacity){
        this.capacity = capacity;
    }

    void load(Parcel parcel){
        execute(() -> parcels.add(parcel))
                .when(() -> !isFull(), this);
    }

    Parcel unload(){
        return execute(() -> parcels.remove(parcels.size() - 1))
                .when(() -> !parcels.isEmpty(), this);
    }

    private Boolean isFull(){
        return parcels.size() == capacity;
    }

    public boolean isEmpty() {
        return parcels.isEmpty();
    }

    public int getParcelCount() {
        return parcels.size();
    }
}
