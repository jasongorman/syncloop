package com.codemanship.loading;

import java.util.List;

import static com.codemanship.syncloop.SyncLoop.execute;

class Loader implements Runnable {

    private final LoadingBay bay;
    private final List<Parcel> parcels;

    Loader(LoadingBay bay, List<Parcel> parcels) {
        this.bay = bay;
        this.parcels = parcels;
    }

    private void loadAll() {
        execute(() -> {
            bay.load(parcels.get(parcels.size() - 1));
            parcels.remove(parcels.size() - 1);
        })
                .until(parcels::isEmpty, this);
    }

    @Override
    public void run() {
        loadAll();
    }
}
