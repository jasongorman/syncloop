package com.codemanship.jointaccount;

import com.codemanship.syncloop.SyncLoop;

public class RatRace implements Runnable {

    private final AccountHolder rat;
    private int funding = 10000;

    public RatRace(AccountHolder rat) {
        this.rat = rat;
    }

    @Override
    public void run() {
        SyncLoop.execute(() -> {
            rat.payday(1000);
            deductFunds(1000);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).until(() -> funding == 0, this);

    }

    public void deductFunds(int amount) {
        funding -= amount;
        if(funding == 0){
            rat.sack();
        }
    }
}
