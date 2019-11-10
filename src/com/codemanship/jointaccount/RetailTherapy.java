package com.codemanship.jointaccount;

import java.util.List;
import java.util.Random;

public class RetailTherapy implements Runnable {

    private final List<AccountHolder> shoppers;

    public RetailTherapy(List<AccountHolder> shoppers){
        this.shoppers = shoppers;
    }

    @Override
    public void run() {
        for(int i = 0;i < 1000;i++) {
            for (AccountHolder shopper :
                    shoppers) {
                shopper.buy((new Random().nextDouble()) * 100);
            }
        }
    }
}
