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

    //getter
    public List<Effectivity> getListEffecitivty(){
        return this.listEffectivity;
    }

    public void addEffectivity(Effectivity m){
        (this.listEffectivity).add(m);
    }

    public void printeffectivityPool(){
        for (Effectivity eff : listEffectivity){
            System.out.println("Source: " + eff.getSource()+ "; Target: " + eff.getTarget()+ "; Effectivity: " + eff.getEffectivity());
        }
    }
}
