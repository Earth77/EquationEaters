package com.goblinstudios.equationeaters.Math;

public class GameTile {

    public int firstNumber;
    public int secondNumber;
    public int totalSum;
    public boolean activeTile = false;
    public boolean eatenTile = false;


    public GameTile(int fi, int si) {
        firstNumber = fi;
        secondNumber = si;
        totalSum = fi + si;
        activeTile = true;
        eatenTile = false;
    }

    public GameTile(boolean activeTileIn, boolean eatenTileIn) {

        activeTile = activeTileIn;
        eatenTile = eatenTileIn;
    }

}
