
package com.mycompany.finalproject_allawan_amper;

public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int minDmg;
    protected int maxDmg;

    public Character(String name, int hp, int minDmg, int maxDmg) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = Math.max(0, Math.min(hp, maxHp)); }
    public int getMaxHp() { return maxHp; }
    public int getMinDmg() { return minDmg; }
    public void setMinDmg(int minDmg) { this.minDmg = minDmg; }
    public int getMaxDmg() { return maxDmg; }
    public void setMaxDmg(int maxDmg) { this.maxDmg = maxDmg; }
}