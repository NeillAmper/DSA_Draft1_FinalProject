
package com.mycompany.finalproject_allawan_amper;

public class DamageSpell extends Spell {
    private final int damage;

    public DamageSpell(String name, int cost, int damage) {
        super(name, cost);
        this.damage = damage;
    }

    @Override
    public void cast(Character caster, Character target) {
        target.setHp(target.getHp() - damage);
        System.out.println(caster.getName() + " casts " + name + " on " + target.getName() + ", dealing " + damage + " damage!");
    }
}