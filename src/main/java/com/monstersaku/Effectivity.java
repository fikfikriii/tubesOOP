package com.monstersaku;

import javax.lang.model.element.Element;

public class Effectivity {
    private ElementType source;
    private ElementType target;
    private float effectivity;

    public Effectivity(ElementType source, ElementType target, float effectivity){
        this.source = source;
        this.target = target;
        this.effectivity = effectivity;
    }

    // Getter
    public ElementType getSource(){
        return this.source;
    }

    public ElementType getTarget(){
        return this.target;
    }

    public float getEffectivity(){
        return this.effectivity;
    }

    // Other methods
    public void print(){
        System.out.println("Source: " + this.source + "; Target: " + this.target + "; Effectivity: " + this.effectivity);
    }
}
