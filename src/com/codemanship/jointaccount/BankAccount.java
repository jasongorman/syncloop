package com.codemanship.jointaccount;

public class BankAccount {

    private double balance = 0;

    public void credit(double amount){
        System.out.println("CREDIT: " + amount + " - balance: " + balance);
        balance += amount;
    }

    public void debit(double amount){
        if(amount > balance){
            throw new RuntimeException("Insufficient funds");
        }
        System.out.println("DEBIT: " + amount + " - balance: " + balance);
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
