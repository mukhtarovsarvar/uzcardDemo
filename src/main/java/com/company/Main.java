package com.company;

public class Main {
    public static void main(String[] args) {

            String balance = "1000000";
            if (balance.equals("0")) {
                System.out.println("0 sum");
            }
            if (balance.length() < 2) {
                System.out.println("0,0" + balance + " sum");
            }
            if (balance.length() < 3) {
                System.out.println("0," + balance + " sum");
            }
        System.out.println(balance.substring(0, balance.length() - 2) + " sum");

    }
}
