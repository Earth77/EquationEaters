package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLES30;

import com.goblinstudios.equationeaters.GUIClasses.ButtonUpdater;
import com.goblinstudios.equationeaters.GUIClasses.ButtonUpdaterTwo;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdater;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterEight;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterFive;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterFour;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterNine;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterSeven;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterSix;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterTen;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterThree;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterTwo;
import com.goblinstudios.equationeaters.GUIClasses.ImageUpdater;
import com.goblinstudios.equationeaters.GUIClasses.ProgressBarUpdater;
import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.TileMap;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.Math.Vector4f;
import com.goblinstudios.equationeaters.R;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import static com.goblinstudios.equationeaters.GameAssets.deviceHeight;
import static com.goblinstudios.equationeaters.GameAssets.deviceWidth;

public class GameScreen extends OpenGLRenderer {

    Sprite gameScreen;
    Sprite selectedBoxScreen;

    static MovingCharacter movingChar;
    ArrayList<Vector4f> movingUp = new ArrayList();
    ArrayList<Vector4f> movingDown = new ArrayList();
    ArrayList<Vector4f> movingLeft = new ArrayList();
    ArrayList<Vector4f> movingRight = new ArrayList();
    ArrayList<Vector4f> eating = new ArrayList();
    ArrayList<Vector4f> prepareToEat = new ArrayList();
    ArrayList<Vector4f> standStill = new ArrayList();
    ArrayList<Vector4f> sick = new ArrayList();

    int frameNumber = 0;

    public static double lastTime, currentTime, deltaTime, sickTime, gameOverTime;
    public static double totalTime;
    boolean lastTimeSet = false;

    Font font;
    Font bigFont;

    Random rand = new Random();

    TileMap gameMap;

    public static int targetSum = 0;

    public static int score = 0;
    public static int level = 1;
    public static int lives = 2;
    public static int currentLevelCorrectAnswers = 0;
    public static int coins = 0;

    public static int numOffsetOne = 0;
    public static int numOffsetTwo = 0;

    public static boolean gameStarted = false;
    public static boolean gameEnded = false;
    public static boolean gameEndedUILoaded = false;
    public static boolean gameTimeRunning = false;

    public static EditTextUpdater etu;
    public static EditTextUpdaterTwo etu2;
    public static EditTextUpdaterThree etu3;
    public static EditTextUpdaterFour etu4;
    public static EditTextUpdaterFive etu5;
    public static EditTextUpdaterSix etu6;
    public static EditTextUpdaterSeven etu7;
    public static EditTextUpdaterEight etu8;
    public static EditTextUpdaterNine etu9;
    public static EditTextUpdaterTen etu10;

    public static ProgressBarUpdater pbu;

    public static ButtonUpdater bu;
    public static ButtonUpdaterTwo bu2;

    public static ImageUpdater iu;


    public GameScreen(Context context, int widthIn, int heightIn) {
        super(context, widthIn, heightIn);
        //movingUp.add(new Vector4f(256,0,128,128));
        //movingUp.add(new Vector4f(256,128,128,128));
        movingDown.add(new Vector4f(256,256,128,128));
        movingDown.add(new Vector4f(256,384,128,128));
        eating.add(new Vector4f(0,0,128,128));
        eating.add(new Vector4f(0,128,128,128));
        prepareToEat.add(new Vector4f(0,128,128,128));
        movingUp.add(new Vector4f(0,256,128,128));
        movingUp.add(new Vector4f(0,384,128,128));
        //movingLeft.add(new Vector4f(128,0,128,128));
        //movingLeft.add(new Vector4f(128,128,128,128));
        movingLeft.add(new Vector4f(128,256,128,128));
        movingLeft.add(new Vector4f(128,384,128,128));
        //movingRight.add(new Vector4f(384,0,128,128));
        //movingRight.add(new Vector4f(384,128,128,128));
        movingRight.add(new Vector4f(384,256,128,128));
        movingRight.add(new Vector4f(384,384,128,128));
        standStill.add(new Vector4f(256,384,128,128));
        sick.add(new Vector4f(128,0,128,128));
        sick.add(new Vector4f(128,128,128,128));
        createSprites();
        etu = new EditTextUpdater();
        etu2 = new EditTextUpdaterTwo();
        etu3 = new EditTextUpdaterThree();
        etu4 = new EditTextUpdaterFour();
        etu5 = new EditTextUpdaterFive();
        etu6 = new EditTextUpdaterSix();
        etu7 = new EditTextUpdaterSeven();
        etu8 = new EditTextUpdaterEight();
        etu9 = new EditTextUpdaterNine();
        etu10 = new EditTextUpdaterTen();
        bu = new ButtonUpdater();
        bu2 = new ButtonUpdaterTwo();
        pbu = new ProgressBarUpdater();
        iu = new ImageUpdater();
    }

    private void createSprites() {
 //       System.out.println("creating sprite : " + GameAssets.openingScreenTexture + ":" + screenWidth + " : " + screenHeight);
        gameScreen = new Sprite(screenWidth, screenHeight, (int)(screenWidth), (int)(screenHeight), GameAssets.gameScreenTexture, GameAssets.gameScreenTextureWidth, GameAssets.gameScreenTextureHeight,0, false, false, new Vector4f(0,224,512,800));
 //       System.out.println("selected  box width : " + (99.5 * screenWidth / 480) + " --- " + (93 * screenHeight / 800));
        selectedBoxScreen = new Sprite(screenWidth, screenHeight, 115 * screenWidth / 480, 113 * screenHeight / 800, GameAssets.simpleBoxTexture, GameAssets.simpleBoxTextureWidth,0, false, false, new Vector4f(0,0,128,128));
        movingChar = new MovingCharacter(rand.nextInt(4),rand.nextInt(5));
        movingChar.movingUp = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, movingUp, 0);
        movingChar.movingDown = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, movingDown, 0);
        movingChar.movingRight = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, movingRight, 0);
        movingChar.movingLeft = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, movingLeft, 0);
        movingChar.eating  = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, eating, 0);
        movingChar.prepareToEat = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, prepareToEat, 0);
        movingChar.standStill = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(64 * screenWidth / 1080,64 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, standStill, 0);
        movingChar.sick = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(192 * screenWidth / 1080,192 * screenHeight / 2042), new Vector4f(0,0,0,0), 512, true, sick, 0);
        font = new Font(GameAssets.fontTexture, new Vector2f(0,0), new Vector4f(0,0,128,128), GameAssets.fontTextureWidth, true, -2 * screenWidth / 1080, 16,16,16 * screenWidth / 1080,16 * screenHeight / 2042);
        bigFont = new Font(GameAssets.bigFontTexture, new Vector2f(0,0), new Vector4f(0,0,512,512), GameAssets.bigFontTextureWidth, true, -16 * screenWidth/ 1080, 64,64,64 * screenWidth / 1080,64 * screenHeight / 2042);
        do {
            targetSum = rand.nextInt(11);
        } while (targetSum < 5 || targetSum > 10);
        gameMap = new TileMap(movingChar.tilePosition.x,movingChar.tilePosition.y, targetSum);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        currentTime = System.nanoTime();

        if (!lastTimeSet) {
            deltaTime = 0;
        } else {
            deltaTime = (currentTime - lastTime) / 10000000;
      //      System.out.println("dt : " + deltaTime);
        }

        frameNumber++;
      //  System.out.println("gs ods");
        GLES30.glClearColor(19.0f / 255.0f, 189.0f / 225.0f, 103.0f / 225.0f, 0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        //      System.out.println("using program shader int : " + shaderInt);
        GLES30.glUseProgram(shaderInt);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        if (gameStarted) {

            gameScreen.draw(shaderInt, 0, new Vector2f(deviceWidth / 2, deviceHeight / 2));
            movingChar.move(deltaTime);
            movingChar.draw(shaderInt, frameNumber, new Vector2f(movingChar.position.x, movingChar.position.y));
            if (modelContext.isTileSelected) {
                //       System.out.println("Selected tile x: " + modelContext.selectedTile.x + " --- " + modelContext.selectedTile.y);
                //       System.out.println("Drawing x : " + ((40 * deviceWidth / 480) + (modelContext.selectedTile.x * 100 * deviceWidth / 480)) + " -- drawing y : " + (((800 - 223) * deviceHeight / 800) - (modelContext.selectedTile.y * 91 * deviceHeight / 800)));
                if (!MovingCharacter.arrivedAtDestination) {
                    selectedBoxScreen.draw(shaderInt, 0, new Vector2f((74.9f * deviceWidth / 480) + (modelContext.selectedTile.x * 109.25f * deviceWidth / 480), (((800 - 164.5f) * deviceHeight / 800) - (modelContext.selectedTile.y * 107.75f * deviceHeight / 800))));
                } else {
                    //System.out.println("new color box");
                    selectedBoxScreen.drawColor(shaderInt, 0, new Vector2f((74.9f * deviceWidth / 480) + (modelContext.selectedTile.x * 109.25f * deviceWidth / 480), (((800 - 164.5f) * deviceHeight / 800) - (modelContext.selectedTile.y * 107.75f * deviceHeight / 800))), 100);
                }
            }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (gameMap.tileList[i][j].activeTile) {

                    if ((gameMap.tileList[i][j].firstNumber > 9 && gameMap.tileList[i][j].firstNumber < 100) && gameMap.tileList[i][j].secondNumber < 10) {
                        numOffsetTwo = 1;
                    } else {
                        numOffsetTwo = 0;
                    }

                    if (gameMap.tileList[i][j].firstNumber > 99 && gameMap.tileList[i][j].secondNumber < 10) {
                        numOffsetTwo = 2;
                    } else if (gameMap.tileList[i][j].firstNumber > 99 && gameMap.tileList[i][j].secondNumber < 100) {
                        numOffsetTwo = 1;
                    }


                    if ((gameMap.tileList[i][j].secondNumber > 9 && gameMap.tileList[i][j].secondNumber < 100) && gameMap.tileList[i][j].firstNumber < 10) {
                        numOffsetOne = 1;
                    } else {
                        numOffsetOne = 0;
                    }

                    if (gameMap.tileList[i][j].secondNumber > 99 && gameMap.tileList[i][j].firstNumber < 10) {
                        numOffsetOne = 2;
                    } else if (gameMap.tileList[i][j].secondNumber > 99 && gameMap.tileList[i][j].firstNumber < 100) {
                        numOffsetOne = 1;
                    }

                    if (gameMap.tileList[i][j].firstNumber < 10 && gameMap.tileList[i][j].secondNumber <10) {
                        numOffsetOne = 0;
                        numOffsetTwo = 0;
                    }

                    if (gameMap.tileList[i][j].firstNumber > 9 && gameMap.tileList[i][j].firstNumber < 100 && gameMap.tileList[i][j].secondNumber > 9 && gameMap.tileList[i][j].secondNumber < 100) {
                        numOffsetOne = 0;
                        numOffsetTwo = 0;
                    }



                    bigFont.draw(shaderInt," " + gameMap.tileList[i][j].firstNumber, new Vector2f((74 * deviceWidth / 480) + (i * 109f * deviceWidth / 480) - (numOffsetTwo * (18 * deviceWidth / 1080)),((800 - 145) * deviceHeight / 800) - (j * 108.5f * deviceHeight / 800)));
                    bigFont.draw(shaderInt,"<" + gameMap.tileList[i][j].secondNumber, new Vector2f((74 * deviceWidth / 480) + (i * 109f * deviceWidth / 480) - (numOffsetOne * (18 * deviceWidth / 1080)),((800 - 170) * deviceHeight / 800) - (j * 108.5f * deviceHeight / 800)));
                    bigFont.draw(shaderInt,";;;", new Vector2f((74 * deviceWidth / 480) + (i * 109f * deviceWidth / 480),((800 - 188) * deviceHeight / 800) - (j * 108.5f * deviceHeight / 800)));

                }
            }
        }

        } else {

            if (totalTime > 200 && !gameStarted && !gameEnded) {
                EditTextUpdaterThree.resourceID = R.id.startingNum;
                EditTextUpdaterThree.text = "1";
                EditTextUpdaterThree.visibility = true;
                EditTextUpdaterThree.isRunning = true;
                GameAssets.gameActivity.runOnUiThread(etu3);
            } else if (totalTime > 100 && totalTime < 201 && !gameStarted && !gameEnded) {
                EditTextUpdaterThree.resourceID = R.id.startingNum;
                EditTextUpdaterThree.text = "2";
                EditTextUpdaterThree.visibility = true;
                EditTextUpdaterThree.isRunning = true;
                GameAssets.gameActivity.runOnUiThread(etu3);
            }

            if (!gameEndedUILoaded) {

            }

        }

        if (GameScreen.lives == 2) {
            movingChar.standStill.drawFront(shaderInt,frameNumber,new Vector2f((91 * deviceWidth / 480) + (2.05f * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (6.07f * 90.75f * deviceHeight / 800))),-0.02f);
        }

        if (GameScreen.lives > 0) {
            movingChar.standStill.drawFront(shaderInt,frameNumber, new Vector2f((91 * deviceWidth / 480) + (1.8f * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (6.07f * 90.75f * deviceHeight / 800))),-0.02f);
        }

       // bigFont.draw(shaderInt, "Lunch Number", new Vector2f((91 * deviceWidth / 480) + (1.5f * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (5f * 90.75f * deviceHeight / 800)));
       // gameMap.magicNumber = 12345;
       // bigFont.drawDouble(shaderInt, gameMap.magicNumber + "", new Vector2f((91 * deviceWidth / 480) + (1.5f * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (5.55f * 90.75f * deviceHeight / 800)));

      //  bigFont.drawOnePointFive(shaderInt, "Level", new Vector2f((91 * deviceWidth / 480) + (1.5f * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (-1.9f * 90.75f * deviceHeight / 800)));
       // bigFont.drawOnePointFive(shaderInt, "" + level, new Vector2f((91 * deviceWidth / 480) + (1.5f * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (-1.55f * 90.75f * deviceHeight / 800)));
      //  bigFont.draw(shaderInt, "Score " + score, new Vector2f((91 * deviceWidth / 480) + (1.5f * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (-1.2f * 90.75f * deviceHeight / 800)));


        if (!lastTimeSet) {
            lastTime = System.nanoTime();
            lastTimeSet = true;
        } else {
            lastTime = currentTime;
        }

        totalTime += deltaTime;
        //System.out.println(totalTime / 100);
        if (totalTime / 100 > 3 && !gameStarted && gameEnded == false) {
            System.out.println("game started");
            gameStarted = true;
            EditTextUpdater.resourceID = R.id.gameEntryText;
            EditTextUpdater.text = "";
            EditTextUpdater.visibility = false;
            EditTextUpdater.isRunning = true;
            GameAssets.gameActivity.runOnUiThread(etu);

            EditTextUpdaterTwo.resourceID = R.id.startingNum;
            EditTextUpdaterTwo.text = "";
            EditTextUpdaterTwo.visibility = false;
            EditTextUpdaterTwo.isRunning = true;
            GameAssets.gameActivity.runOnUiThread(etu2);
        }

        gameOverTime += deltaTime;
        if (gameOverTime / 100 > 0.8f && gameTimeRunning) {
            gameStarted = false;
            gameEnded = true;
            swapGUI();
        }
    }

    public static void checkGameOver() {

        if (lives < 1) {
            gameOverTime = 0;
            gameTimeRunning = true;
        }

    }

    public static void swapGUI() {
        closeGameUI();
        openGameOverUI();
    }

    public static void closeGameUI() {
        EditTextUpdater.resourceID = R.id.levelText;
        EditTextUpdater.text = "";
        EditTextUpdater.visibility = false;
        EditTextUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu);

        EditTextUpdaterTwo.resourceID = R.id.scoreText;
        EditTextUpdaterTwo.text = "";
        EditTextUpdaterTwo.visibility = false;
        EditTextUpdaterTwo.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu2);

        EditTextUpdaterThree.resourceID = R.id.lunchNumberText;
        EditTextUpdaterThree.text = "";
        EditTextUpdaterThree.visibility = false;
        EditTextUpdaterThree.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu3);

        EditTextUpdaterFour.resourceID = R.id.lunchNumberNum;
        EditTextUpdaterFour.text = "";
        EditTextUpdaterFour.visibility = false;
        EditTextUpdaterFour.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu4);

        EditTextUpdaterFive.resourceID = R.id.livesText;
        EditTextUpdaterFive.text = "";
        EditTextUpdaterFive.visibility = false;
        EditTextUpdaterFive.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu5);
    }

    public static void openGameOverUI() {

        EditTextUpdaterSix.resourceID = R.id.lvlAchievedText;
        EditTextUpdaterSix.text = "Level Achieved " + level;
        EditTextUpdaterSix.visibility = true;
        EditTextUpdaterSix.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu6);

        EditTextUpdaterSeven.resourceID = R.id.scoreAchievedText;
        EditTextUpdaterSeven.text = "Score " + score;
        EditTextUpdaterSeven.visibility = true;
        EditTextUpdaterSeven.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu7);

        EditTextUpdaterEight.resourceID = R.id.coinsAchievedText;
        EditTextUpdaterEight.text = "Coins " + coins;
        EditTextUpdaterEight.visibility = true;
        EditTextUpdaterEight.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu8);

        ProgressBarUpdater.resourceID = R.id.progressBarIMG;
        ProgressBarUpdater.visibility = true;
        ProgressBarUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(pbu);

        ButtonUpdater.resourceID = R.id.btnPlayAgain;
        ButtonUpdater.text = "Play Again";
        ButtonUpdater.visibility = true;
        ButtonUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(bu);

        ButtonUpdaterTwo.resourceID = R.id.btnShop;
        ButtonUpdaterTwo.text = "Spend Coins";
        ButtonUpdaterTwo.visibility = true;
        ButtonUpdaterTwo.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(bu2);

        ImageUpdater.resourceID = R.id.imageView;
        ImageUpdater.visibility = true;
        ImageUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(iu);
    }

    public static void closeGameOverGUI() {

        EditTextUpdaterSix.resourceID = R.id.lvlAchievedText;
        EditTextUpdaterSix.text = "Level Achieved " + level;
        EditTextUpdaterSix.visibility = false;
        EditTextUpdaterSix.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu6);

        EditTextUpdaterSeven.resourceID = R.id.scoreAchievedText;
        EditTextUpdaterSeven.text = "Score " + score;
        EditTextUpdaterSeven.visibility = false;
        EditTextUpdaterSeven.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu7);

        EditTextUpdaterEight.resourceID = R.id.coinsAchievedText;
        EditTextUpdaterEight.text = "Coins " + coins;
        EditTextUpdaterEight.visibility = false;
        EditTextUpdaterEight.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(etu8);

        ProgressBarUpdater.resourceID = R.id.progressBarIMG;
        ProgressBarUpdater.visibility = false;
        ProgressBarUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(pbu);

        ButtonUpdater.resourceID = R.id.btnPlayAgain;
        ButtonUpdater.text = "Play Again";
        ButtonUpdater.visibility = false;
        ButtonUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(bu);

        ButtonUpdaterTwo.resourceID = R.id.btnShop;
        ButtonUpdaterTwo.text = "Spend Coins";
        ButtonUpdaterTwo.visibility = false;
        ButtonUpdaterTwo.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(bu2);

        ImageUpdater.resourceID = R.id.imageView;
        ImageUpdater.visibility = false;
        ImageUpdater.isRunning = true;
        GameAssets.gameActivity.runOnUiThread(iu);

    }


}
