package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class Monster extends Character {

    private boolean stunned = false;
    private boolean desperateGambitActive = false;
    private boolean desperateGambitNerf = false;
    private boolean desperateGambitUsed = false;
    private final Stack<Integer> desperateGambitStack = new Stack<>();
    private final Stack<Integer> monsterHpStack = new Stack<>();
    // Dictionary alternative (Java): Map for status effects
    private Map<String, Boolean> statusEffects = new HashMap<>();

    public Monster(String name) {
        super(name, 100, 5, 10);
        statusEffects.put("poisoned", false);
    }

    public void setStunned(boolean stunned) { this.stunned = stunned; }
    public void pushPrevHp(int hp) { monsterHpStack.push(hp); }
    public boolean isDesperateGambitActive() { return desperateGambitActive; }
    public boolean isDesperateGambitNerf() { return desperateGambitNerf; }
    public boolean isDesperateGambitUsed() { return desperateGambitUsed; }

    public void monsterTurn(Hero hero, Random random) {
        if (hp <= 0 && !desperateGambitActive && !desperateGambitNerf && !desperateGambitUsed) {
            System.out.println(name + "'s passive activates: 'Desperate Gambit'!");
            hp = 50;
            desperateGambitActive = true;
            desperateGambitStack.push(3);
            desperateGambitUsed = true;
            minDmg = 5;
            maxDmg = 15;
            System.out.println(name + " regains 50% HP: " + hp + " and is empowered for 3 turns! (+10% damage)");
        }
        if (hp <= 0) return;
        if (stunned) {
            System.out.println(name + " is stunned and cannot attack this turn.\n");
            stunned = false;
            return;
        }
        int monsterDmg = minDmg + random.nextInt(maxDmg - minDmg + 1);

        // Desperate Gambit empowered/nerfed
        if (desperateGambitActive && !desperateGambitStack.isEmpty()) {
            monsterDmg = monsterDmg + (monsterDmg * 10 / 100);
            int turnsLeft = desperateGambitStack.pop() - 1;
            if (turnsLeft > 0) desperateGambitStack.push(turnsLeft);
            else {
                desperateGambitActive = false;
                desperateGambitNerf = true;
                System.out.println("Desperate Gambit's power fades. " + name + " now deals half damage and its max damage returns to normal!");
                minDmg = 5;
                maxDmg = 10;
            }
        } else if (desperateGambitNerf) {
            monsterDmg = monsterDmg / 2;
        }
        // Doom Reflection skill
        if (hero.isDoomReflectionActive()) {
            int reflectRoll = random.nextInt(4);
            if (reflectRoll == 0) {
                System.out.println("DOOM REFLECTION: Perfect reflect! " + name + " takes " + monsterDmg + " damage, you take 0!");
                setHp(hp - monsterDmg);
            } else {
                int reflected = monsterDmg / 2;
                System.out.println("DOOM REFLECTION: You reflect " + reflected + " damage back! You take " + monsterDmg + " damage.");
                setHp(hp - reflected);
                hero.setHp(hero.getHp() - monsterDmg);
            }
            hero.decrementDoomReflection();
        } else {
            // Normal monster attack
            System.out.println(name.toUpperCase() + "'S TURN!");
            System.out.println(name + " attacks and deals " + monsterDmg + " damage to you.");
            hero.setHp(hero.getHp() - monsterDmg);
            System.out.println("Your remaining hp: " + hero.getHp());
        }
        // Monster Regeneration passive
        int regenChance = random.nextInt(4);
        if (!monsterHpStack.isEmpty() && regenChance == 0) {
            int prevHp = monsterHpStack.peek();
            System.out.println(name + " activates passive: 'Regeneration'!");
            System.out.println("Regeneration: " + name + " restores its HP to " + prevHp);
            setHp(prevHp);
        }
    }
}