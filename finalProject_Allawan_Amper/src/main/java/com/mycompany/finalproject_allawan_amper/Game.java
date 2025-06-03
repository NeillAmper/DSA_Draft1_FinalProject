package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class Game {

    private Hero hero;
    private Dungeon dungeon;
    private Random random = new Random();

    // Monster name pools
    private LinkedList<String> firstNames = new LinkedList<>();
    private LinkedList<String> lastNames = new LinkedList<>();

    public Game() {
        this.hero = new Hero();
        this.dungeon = new Dungeon();

        // Populate name pools
        firstNames.addAll(Arrays.asList(
                "Neill", "Joshua", "Mitch", "Reyian", "Nico", "Adriane", "Qiann", "Tans", "Super Human", "Anton"
        ));
        lastNames.addAll(Arrays.asList(
                "From Tiggato", "of Maa", "The Balut", "of Maa", "Bad genius", "Eucare", "Pokemon", "Eucare",
                "Amper", "of Matina Aplaya", "Lerasan", "Canja", "Vergara", "The Hero"
        ));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exitGame = false;

        while (!exitGame && dungeon.hasRooms() && hero.getHp() > 0) {
            String currentRoom = dungeon.nextRoom();
            System.out.println("\nYou enter: " + currentRoom);

            Monster monster;
            String monsterName;
            if (currentRoom.equalsIgnoreCase("Boss Lair")) {
                monsterName = "Dungeon Boss";
                monster = new Monster(monsterName);
            } else {
                // Pull random monster name from pool if available
                if (firstNames.isEmpty() || lastNames.isEmpty()) {
                    System.out.println("No more unique monster names left!");
                    break;
                }
                int firstIndex = random.nextInt(firstNames.size());
                int lastIndex = random.nextInt(lastNames.size());
                monsterName = firstNames.remove(firstIndex) + " " + lastNames.remove(lastIndex);
                monster = new Monster(monsterName);
            }

            System.out.println("YOU ENCOUNTERED AN ENEMY: " + monsterName + "!");

            // Battle loop
            while (hero.getHp() > 0 && monster.getHp() > 0) {
                System.out.println("Player Hp: " + hero.getHp());
                System.out.println(monsterName + " Hp: " + monster.getHp());
                hero.playerTurn(monster, scanner, random);
                if (monster.getHp() <= 0) {
                    System.out.println("Congratulations! You defeated " + monsterName + "!");
                    break;
                }
                monster.monsterTurn(hero, random);
                if (hero.getHp() <= 0) {
                    System.out.println("You lost! " + monsterName + " wins!");
                    break;
                }
            }

            // Continue or exit?
            if (!exitGame && dungeon.hasRooms() && hero.getHp() > 0) {
                while (true) {
                    System.out.print("Proceed to the next room? (yes/exit): ");
                    String nextAction = scanner.next();
                    if (nextAction.equalsIgnoreCase("exit")) {
                        exitGame = true;
                        break;
                    } else if (nextAction.equalsIgnoreCase("yes")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please type 'yes' or 'exit'.");
                    }
                }
            }
        }
        System.out.println("Game Over.");
    }
}
