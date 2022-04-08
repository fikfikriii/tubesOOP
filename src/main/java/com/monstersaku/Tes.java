package com.monstersaku;
import java.util.List;
import java.util.ArrayList;

public class Tes {
    int x;
    public Tes(int x){
        this.x = x;
    }

    public void min(){
        this.x -= 1;
    }

    public void set(int y){
        this.x = y;
    }
    public static void main(String[] args) {
        List<Tes> l1 = new ArrayList<Tes>();
        List<Tes> l2 = new ArrayList<Tes>();
        Tes x = new Tes(5);
        l1.add(x);
        l2.add(x);
        x.set(6);
        System.out.println(l1.get(0).x);
    }
}
