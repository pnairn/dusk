package com.stewsters.dusk.core.flyweight;


public enum DamageType {

    SLASH("Slash"),
    PIERCE("Pierce"),
    BASH("Bash"),
    WOOD("Wood"),
    SILVER("Silver"),
    IRON("Iron"),
    FIRE("Fire"),
    LIGHTNING("Lightning");

    public final String name;

    DamageType(String name) {
        this.name = name;
    }

}
