package com.goblinstudios.equationeaters;

import com.goblinstudios.equationeaters.Math.Vector2i;

public class GameModel {

    boolean gameTileSelected[][] = new boolean[4][5];
    public Vector2i selectedTile = new Vector2i(0,0);
    public boolean isTileSelected = false;

    public GameModel() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                gameTileSelected[i][j] = false;
            }
        }
    }

    public void setSelected(int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                gameTileSelected[i][j] = false;
            }
        }
        gameTileSelected[x][y] = true;
        selectedTile = new Vector2i(x,y);
        isTileSelected = true;
        System.out.println("tile " + x + " - " + y + " : is selected");
    }
}
