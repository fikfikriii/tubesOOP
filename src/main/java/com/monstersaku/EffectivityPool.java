/*
EffectivityPool dipake buat nyimpen objek-objek effectivity monstersaku.
*/

package com.monstersaku;
import java.util.List;
import java.util.ArrayList;

public class EffectivityPool {
    private List<Effectivity> listEffectivity;

    public EffectivityPool(){
        List<Effectivity> listEffectivity = new ArrayList<Effectivity>();
        this.listEffectivity = listEffectivity;
    }

    public void addEffectivity(Effectivity m){
        (this.listEffectivity).add(m);
    }

    public void printeffectivityPool(){
        System.out.println("---Ini adalah list effectivitys monstersaku---");
        for (Effectivity eff : listEffectivity){
            eff.print();
        }
    }
}
