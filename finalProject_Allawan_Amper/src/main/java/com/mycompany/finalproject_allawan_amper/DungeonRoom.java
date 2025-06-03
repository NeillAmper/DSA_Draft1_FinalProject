
package com.mycompany.finalproject_allawan_amper;

import java.util.*;

public class DungeonRoom {
    private String name;
    private List<DungeonRoom> connections;

    public DungeonRoom(String name) {
        this.name = name;
        this.connections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<DungeonRoom> getConnections() {
        return connections;
    }

    public void connectRoom(DungeonRoom room) {
        if (!connections.contains(room)) {
            connections.add(room);
            // For undirected graph
            room.connections.add(this);
        }
    }
}