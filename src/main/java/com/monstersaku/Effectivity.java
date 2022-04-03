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

    public void print(){
        System.out.println("Source: " + this.source + "; Target: " + this.target + "; Effectivity: " + this.effectivity);
    }
}
