
package com.mycompany.finalproject_allawan_amper;

public class HealingSpell extends Spell {
    private final int healAmount;

    public HealingSpell(String name, int cost, int healAmount) {
        super(name, cost);
        this.healAmount = healAmount;
    }

    @Override
    public void cast(Character caster, Character target) {
        target.setHp(target.getHp() + healAmount);
        System.out.println(caster.getName() + " casts " + name + " on " + target.getName() + ", healing " + healAmount + " HP!");
    }
}