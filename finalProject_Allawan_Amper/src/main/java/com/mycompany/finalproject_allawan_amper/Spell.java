
package com.mycompany.finalproject_allawan_amper;

public abstract class Spell {
    protected String name;
    protected int cost;

    public Spell(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public abstract void cast(Character caster, Character target);
}