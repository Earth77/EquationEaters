package com.goblinstudios.equationeaters.Math;

import com.goblinstudios.equationeaters.OpenGLRendering.GameScreen;

import java.util.Random;

public class TileMap {

    public static GameTile tileList[][];
    public static Random rand = new Random();
    public static int rollCorrectAnswer = 0;
    public static int randomNumerator = 0;
    public static int randomDenominator = 0;
    public static int magicNumber = 0;
    public static int numDesignatedAnswers = 0;

    public TileMap(int playPosX, int playPosY, int targetNum) {

        magicNumber = targetNum;
        tileList = new GameTile[4][5];

        int correctEquation = 0;


        do {

            numDesignatedAnswers = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if ((i + j != playPosX + playPosY) || i != playPosX || j != playPosY) {

                        if (numDesignatedAnswers < 5) {
                            rollCorrectAnswer = rand.nextInt(5);
                        } else {
                            rollCorrectAnswer = 1;
                        }

                        if (rollCorrectAnswer == 0) {
                            randomNumerator = rand.nextInt(targetNum);
                            tileList[i][j] = new GameTile(randomNumerator, targetNum - randomNumerator);
                            numDesignatedAnswers++;
                        } else {
                            do {
                                randomNumerator = rand.nextInt(targetNum);
                                randomDenominator = rand.nextInt(targetNum);
                            } while (randomNumerator + randomDenominator == targetNum);
                            tileList[i][j] = new GameTile(randomNumerator, randomDenominator);
                        }
                    } else {
                        tileList[i][j] = new GameTile(false, true);
                    }
                }
            }

        } while (numDesignatedAnswers < 4);

        System.out.println("num designated answers : " + numDesignatedAnswers);
    }

    public static void refill(int playPosX, int playPosY, int targetNum) {

        numDesignatedAnswers = 0;
        GameScreen.currentLevelCorrectAnswers = 0;
        magicNumber = targetNum;
        tileList = new GameTile[4][5];

        int correctEquation = 0;


        do {

            numDesignatedAnswers = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    if ((i + j != playPosX + playPosY) || i != playPosX || j != playPosY) {

                        if (numDesignatedAnswers < 5) {
                            rollCorrectAnswer = rand.nextInt(5);
                        } else {
                            rollCorrectAnswer = 1;
                        }

                        if (rollCorrectAnswer == 0) {
                            randomNumerator = rand.nextInt(targetNum);
                            tileList[i][j] = new GameTile(randomNumerator, targetNum - randomNumerator);
                            numDesignatedAnswers++;
                        } else {
                            do {
                                randomNumerator = rand.nextInt(targetNum);
                                randomDenominator = rand.nextInt(targetNum);
                            } while (randomNumerator + randomDenominator == targetNum);
                            tileList[i][j] = new GameTile(randomNumerator, randomDenominator);
                        }
                    } else {
                        tileList[i][j] = new GameTile(false, true);
                    }
                }
            }

        } while (numDesignatedAnswers < 4);
        System.out.println("num designated answers : " + numDesignatedAnswers);
    }

}
