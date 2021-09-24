package com.puissance4;

import java.util.ArrayList;

enum ColorOfPieces {
    RED,
    GREEN
}

public class Player {

    private int teamColor;
    private String name;

    public Player(int teamColor, String name) {
        this.teamColor = teamColor;
        this.name = name;
    }
}
