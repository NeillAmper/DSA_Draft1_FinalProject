
package com.mycompany.finalproject_allawan_amper;

import java.util.LinkedList;

public class Dungeon {
    // LinkedList for dynamic room management
    private LinkedList<String> rooms = new LinkedList<>();

    public Dungeon() {
        // Example setup
        rooms.add("Entrance");
        rooms.add("Trap Room");
        rooms.add("Treasure Room");
        rooms.add("Boss Lair");
    }

    public String nextRoom() {
        if (!rooms.isEmpty()) return rooms.poll();
        return null;
    }

    public boolean hasRooms() {
        return !rooms.isEmpty();
    }
}