package com.codemanship.jointaccount;

import java.util.Arrays;

public class Program {

    public static void main(String[] args){
        BankAccount account = new BankAccount();

        AccountHolder jason = new AccountHolder(account);
        AccountHolder sarah = new AccountHolder(account);
        RatRace race1 = new RatRace(jason);
        RatRace race2 = new RatRace(sarah);
        RetailTherapy rt1 = new RetailTherapy(Arrays.asList(jason));
        RetailTherapy rt2 = new RetailTherapy(Arrays.asList(sarah));

        new Thread(race1).start();
        new Thread(race2).start();
        new Thread(rt1).start();
        new Thread(rt2).start();

    }
}
