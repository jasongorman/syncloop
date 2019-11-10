package com.codemanship.jointaccount;

import com.codemanship.syncloop.SyncLoop;

import static com.codemanship.syncloop.SyncLoop.execute;

public class AccountHolder {

    private final BankAccount account;
    private boolean unemployed = false;

    public AccountHolder(BankAccount account){
        this.account = account;
    }

    public void buy(double price){
        if(!unemployed){
            execute(() -> account.debit(price))
                    .when(() -> account.getBalance() >= price, this);
        }
    }

    public void payday(double salary){
        account.credit(salary);
    }

    public void sack() {
        unemployed = true;
    }
}
