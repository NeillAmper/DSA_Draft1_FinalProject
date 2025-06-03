
package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class Dungeon {
    private Map<String, DungeonRoom> rooms; // Like a dictionary for fast lookup
    private DungeonRoom startRoom;

    public Dungeon() {
        rooms = new HashMap<>();
        createDungeonMap();
    }

    private void createDungeonMap() {
        // Create rooms
        DungeonRoom entrance = new DungeonRoom("Entrance");
        DungeonRoom trapRoom = new DungeonRoom("Trap Room");
        DungeonRoom treasureRoom = new DungeonRoom("Treasure Room");
        DungeonRoom puzzleRoom = new DungeonRoom("Puzzle Chamber");
        DungeonRoom restRoom = new DungeonRoom("Rest Area");
        DungeonRoom bossLair = new DungeonRoom("Boss Lair");

        // Connect rooms (define map)
        entrance.connectRoom(trapRoom);
        entrance.connectRoom(treasureRoom);

        trapRoom.connectRoom(puzzleRoom);
        treasureRoom.connectRoom(restRoom);

        puzzleRoom.connectRoom(bossLair);
        restRoom.connectRoom(bossLair);

        // Add to map for lookup
        rooms.put(entrance.getName(), entrance);
        rooms.put(trapRoom.getName(), trapRoom);
        rooms.put(treasureRoom.getName(), treasureRoom);
        rooms.put(puzzleRoom.getName(), puzzleRoom);
        rooms.put(restRoom.getName(), restRoom);
        rooms.put(bossLair.getName(), bossLair);

        startRoom = entrance;
    }

    public DungeonRoom getStartRoom() {
        return startRoom;
    }

    public DungeonRoom getRoom(String name) {
        return rooms.get(name);
    }
}