
package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class Hero extends Character {
    private final Stack<Integer> jinguBuffStack = new Stack<>();
    private int jinguHitCounter = 0;

    private final int[] doomReflection = new int[2]; // [0]: 0=ready,1=active,2=cd; [1]: turns left
    private final int[] stunCooldown = new int[2]; // [0]: 0=ready,1=cd; [1]: turns left

    // Data structures: HashMap for inventory, Array for spell slots, Stack for buffs
    private final HashMap<String, Integer> inventory = new HashMap<>();
    private final Spell[] spellBook = new Spell[3]; // Array: fixed spell slots

    public Hero() {
        super("Player", 100, 5, 10);
        doomReflection[0] = 0; doomReflection[1] = 0;
        stunCooldown[0] = 0; stunCooldown[1] = 0;
        inventory.put("Potion", 2); // Example item
        spellBook[0] = new DamageSpell("Fireball", 5, 15);
        spellBook[1] = new HealingSpell("Heal", 5, 20);
        spellBook[2] = null;
    }

    public void playerTurn(Monster monster, Scanner scanner, Random random) {
        System.out.println("PLAYER'S TURN!");
        System.out.println("1. Normal Attack");
        String stunStatus = (stunCooldown[0] == 1 ? "Cooldown: " + stunCooldown[1] : "Ready");
        System.out.println("2. Stun Attack (" + stunStatus + ")");
        System.out.println("3. Use Spell");
        String doomStatus = (doomReflection[0] == 2) ? ("Cooldown: " + doomReflection[1])
                : (doomReflection[0] == 1 ? ("Active: " + doomReflection[1] + " turn(s) left")
                : "Ready");
        System.out.println("4. Activate Doom Reflection (" + doomStatus + ")");
        System.out.println("5. Use Inventory Item (Potion)");
        System.out.print("Choose (or type exit): ");

        String input = scanner.next();

        if (input.equalsIgnoreCase("exit")) {
            System.out.println("You exited the game.");
            setHp(0);
            return;
        }

        switch (input) {
            case "1" -> normalAttack(monster, random);
            case "2" -> stunAttack(monster, random);
            case "3" -> useSpell(monster, scanner);
            case "4" -> activateDoomReflection();
            case "5" -> useInventoryItem();
            default -> {
                System.out.println("Invalid input! Please enter 1-5 or 'exit'.");
                playerTurn(monster, scanner, random);
                return;
            }
        }
        manageCooldowns();
    }

    private void normalAttack(Monster monster, Random random) {
        jinguHitCounter++;
        int playerDmg = minDmg + random.nextInt(maxDmg - minDmg + 1);

        if (jinguBuffStack.isEmpty() && jinguHitCounter == 4) {
            System.out.println("Passive Jingu Mastery is activated!");
            for (int i = 0; i < 4; i++) jinguBuffStack.push(1);
            jinguHitCounter = 0;
        }
        if (!jinguBuffStack.isEmpty()) {
            int bonusDmg = (int) Math.round(playerDmg * 1.6);
            playerDmg += bonusDmg;
            int lifesteal = (int) Math.round(playerDmg * 0.80);
            int healed = Math.min(lifesteal, maxHp - hp);
            setHp(hp + healed);
            System.out.println("Jingu Mastery buff: +160% bonus (" + bonusDmg + ") damage and +" + healed + " HP (lifesteal)!");
            jinguBuffStack.pop();
        }
        monster.pushPrevHp(monster.getHp());
        monster.setHp(monster.getHp() - playerDmg);
        System.out.println("You dealt " + playerDmg + " damage to " + monster.getName() + ".");
    }

    private void stunAttack(Monster monster, Random random) {
        if (stunCooldown[0] == 1) {
            System.out.println("Stun Attack is on cooldown! (" + stunCooldown[1] + " turn(s) left)");
        } else {
            jinguHitCounter = 0;
            int stunChance = random.nextInt(4);
            if (stunChance == 0) {
                monster.setStunned(true);
                System.out.println("Stun successful! " + monster.getName() + " is stunned and will skip its turn.");
            } else {
                System.out.println("Stun failed! " + monster.getName() + " is not stunned.");
            }
            stunCooldown[0] = 1;
            stunCooldown[1] = 4;
        }
    }

    private void useSpell(Monster monster, Scanner scanner) {
        System.out.println("Choose spell:");
        for (int i = 0; i < spellBook.length; i++) {
            if (spellBook[i] != null) {
                System.out.println((i + 1) + ". " + spellBook[i].getName());
            }
        }
        System.out.print("Spell #: ");
        int spellIdx = scanner.nextInt() - 1;
        if (spellIdx < 0 || spellIdx >= spellBook.length || spellBook[spellIdx] == null) {
            System.out.println("Invalid spell selection.");
            return;
        }
        spellBook[spellIdx].cast(this, monster);
    }

    private void activateDoomReflection() {
        if (doomReflection[0] == 1) {
            System.out.println("Doom Reflection is already active! (" + doomReflection[1] + " turn(s) left)");
        } else if (doomReflection[0] == 2) {
            System.out.println("Doom Reflection is on cooldown! (" + doomReflection[1] + " turn(s) left)");
        } else {
            doomReflection[0] = 1;
            doomReflection[1] = 3;
            System.out.println("You activate DOOM REFLECTION! Next 3 attacks will be reflected.");
        }
    }

    private void useInventoryItem() {
        if (inventory.getOrDefault("Potion", 0) > 0) {
            setHp(hp + 30);
            inventory.put("Potion", inventory.get("Potion") - 1);
            System.out.println("You used a Potion! Restored 30 HP. Potions left: " + inventory.get("Potion"));
        } else {
            System.out.println("No Potions left!");
        }
    }

    private void manageCooldowns() {
        if (stunCooldown[0] == 1) {
            stunCooldown[1]--;
            if (stunCooldown[1] <= 0) {
                stunCooldown[0] = 0;
                stunCooldown[1] = 0;
                System.out.println("Stun Attack cooldown finished! Skill is ready to use again.");
            }
        }
        if (doomReflection[0] == 2) {
            doomReflection[1]--;
            if (doomReflection[1] <= 0) {
                doomReflection[0] = 0;
                doomReflection[1] = 0;
                System.out.println("Doom Reflection cooldown finished! Skill is ready to use again.");
            }
        }
    }

    public boolean isDoomReflectionActive() {
        return doomReflection[0] == 1 && doomReflection[1] > 0;
    }
    public void decrementDoomReflection() {
        doomReflection[1]--;
        if (doomReflection[1] <= 0) {
            doomReflection[0] = 2; // cooldown
            doomReflection[1] = 6;
            System.out.println("Doom Reflection has ended! Cooldown started.");
        } else {
            System.out.println("Doom Reflection remains active for " + doomReflection[1] + " more turn(s).");
        }
    }
    public boolean isDoomReflectionOnCooldown() {
        return doomReflection[0] == 2;
    }
}