package com.goblinstudios.equationeaters.Math;

import java.util.Random;

public class TileMap {

    public static GameTile tileList[][];
    Random rand = new Random();
    int rollCorrectAnswer = 0;
    int randomNumerator = 0;
    int randomDenominator = 0;

    public TileMap(int playPosX, int playPosY, int targetNum) {

        int correctEquation = 0;
        int numDesignatedAnswers = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (i != playPosX && j != playPosY) {

                    if (numDesignatedAnswers < 5) {
                        rollCorrectAnswer = rand.nextInt(5);
                    } else {
                        rollCorrectAnswer = 1;
                    }

                    if (rollCorrectAnswer == 0 || 20 > ((i + 1) + (j * 4) + numDesignatedAnswers)) {
                        randomNumerator = rand.nextInt(targetNum);
                        tileList[i][j] = new GameTile(randomNumerator, targetNum - randomNumerator);
                    } else {
                        do {
                            randomNumerator = rand.nextInt(targetNum);
                            randomDenominator = rand.nextInt(targetNum);
                        } while (randomNumerator + randomDenominator != targetNum);

                        tileList[i][j] = new GameTile(randomNumerator,randomDenominator);
                    }
                }
            }
        }
    }

}
