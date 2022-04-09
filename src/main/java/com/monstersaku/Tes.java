package com.monstersaku;
import java.util.List;
import java.util.ArrayList;

public class Tes {
    
    public static void main(String[] args) {
        List<List<String>> addresses = new ArrayList<List<String>>();

        List<String> singleAddress = new ArrayList<String>();
        singleAddress.add("17 Fake Street");
        singleAddress.add("Phoney town");
        singleAddress.add("Makebelieveland");
        
        addresses.add(singleAddress);

        List<String> singleAddress2 = new ArrayList<String>();
        singleAddress2.add("17 Fake Streetewe");
        singleAddress2.add("Phoneywefwe town");
        singleAddress2.add("Makebeliewgveland");

        addresses.add(singleAddress2);

        System.out.println(addresses.get(0));

        double d = 1.0 / 3.0;
        System.out.println(d);
    }
}
