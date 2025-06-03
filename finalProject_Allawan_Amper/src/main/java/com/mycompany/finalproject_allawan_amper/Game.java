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
        DungeonRoom currentRoom = dungeon.getStartRoom();
        Set<String> visited = new HashSet<>();

        while (!exitGame && hero.getHp() > 0) {
            System.out.println("\nYou are now in: " + currentRoom.getName());
            visited.add(currentRoom.getName());

            // Show choices at EVERY room (including the entrance, before any battle)
            List<DungeonRoom> options = currentRoom.getConnections();
            if (options.isEmpty()) {
                System.out.println("No more rooms to explore.");
                break;
            }
            System.out.println("\nWhere do you want to go next?");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i).getName() + (visited.contains(options.get(i).getName()) ? " (visited)" : ""));
            }
            System.out.println((options.size() + 1) + ". Exit dungeon");

            int choice = -1;
            while (choice < 1 || choice > options.size() + 1) {
                System.out.print("Enter choice: ");
                try {
                    choice = Integer.parseInt(scanner.next());
                } catch (Exception e) {
                    choice = -1;
                }
            }
            if (choice == options.size() + 1) {
                System.out.println("You chose to exit the dungeon.");
                exitGame = true;
                break;
            } else {
                currentRoom = options.get(choice - 1);
            }

            // After moving, run the encounter
            if (currentRoom.getName().equalsIgnoreCase("Boss Lair")) {
                System.out.println("The Boss emerges!");
                Monster boss = new Monster("Dungeon Boss");
                runBattle(boss, scanner);
                break;
            } else if (!visited.contains(currentRoom.getName()) || currentRoom.getConnections().size() == 1) {
                // Only fight in a room if you haven't visited it before, or it's a dead-end
                if (!firstNames.isEmpty() && !lastNames.isEmpty()) {
                    int firstIndex = random.nextInt(firstNames.size());
                    int lastIndex = random.nextInt(lastNames.size());
                    String monsterName = firstNames.remove(firstIndex) + " " + lastNames.remove(lastIndex);
                    Monster monster = new Monster(monsterName);
                    System.out.println("YOU ENCOUNTERED AN ENEMY: " + monsterName + "!");
                    runBattle(monster, scanner);
                    if (hero.getHp() <= 0) break;
                } else {
                    System.out.println("No more unique monster names left!");
                    break;
                }
            } else {
                System.out.println("You've already cleared this room. It's eerily quiet...");
            }
        }
        System.out.println("Game Over.");
    }

    private void runBattle(Monster monster, Scanner scanner) {
        while (hero.getHp() > 0 && monster.getHp() > 0) {
            System.out.println("Player Hp: " + hero.getHp());
            System.out.println(monster.getName() + " Hp: " + monster.getHp());
            hero.playerTurn(monster, scanner, random);
            if (monster.getHp() <= 0) {
                System.out.println("Congratulations! You defeated " + monster.getName() + "!");
                break;
            }
            monster.monsterTurn(hero, random);
            if (hero.getHp() <= 0) {
                System.out.println("You lost! " + monster.getName() + " wins!");
                break;
            }
        }
    }
}