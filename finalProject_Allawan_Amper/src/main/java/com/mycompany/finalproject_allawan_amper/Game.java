
package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class Game {
    private final Hero hero;
    private final Random random = new Random();
    // Monster name pools (LinkedLists for O(1) removal)
    private final LinkedList<String> firstNames = new LinkedList<>();
    private final LinkedList<String> lastNames = new LinkedList<>();

    public Game() {
        this.hero = new Hero();
        // Populate name pools (add as many as you wish)
        firstNames.addAll(Arrays.asList(
                "Neill", "Joshua", "Mitch", "Reyian", "Nico", "Adriane", "Qiann", "Tans", "Super Human", "Anton"
        ));
        lastNames.addAll(Arrays.asList(
                "From Tiggato", "of Maa", "The Balut", "of Maa", "Bad genius", "Eucare", "Pokemon", "Eucare", "Amper", "of Matina Aplaya", "Lerasan", "Canja", "Vergara", "The Hero"
        ));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exitGame = false;

        while (!exitGame && !firstNames.isEmpty() && !lastNames.isEmpty() && hero.getHp() > 0) {
            // Generate a random monster name
            int firstIndex = random.nextInt(firstNames.size());
            int lastIndex = random.nextInt(lastNames.size());
            String monsterName = firstNames.remove(firstIndex) + " " + lastNames.remove(lastIndex);

            Monster monster = new Monster(monsterName);
            System.out.println("\nYOU ENCOUNTERED AN ENEMY: " + monsterName + "!");

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
            if (!exitGame && (!firstNames.isEmpty() && !lastNames.isEmpty()) && hero.getHp() > 0) {
                while (true) {
                    System.out.print("Do you want to continue to fight another monster? (yes/exit): ");
                    String nextAction = scanner.next();
                    if (nextAction.equalsIgnoreCase("exit")) {
                        System.out.println("You exited the game.");
                        exitGame = true;
                        break;
                    } else if (nextAction.equalsIgnoreCase("yes")) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please type 'yes' to continue or 'exit' to quit.");
                    }
                }
            } else if (firstNames.isEmpty() || lastNames.isEmpty()) {
                System.out.println("No more unique monster names left. You have defeated all possible monsters!");
                exitGame = true;
            }
        }

        System.out.println("Game Over.");
    }
}