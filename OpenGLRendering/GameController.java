package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdater;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterThree;
import com.goblinstudios.equationeaters.GUIClasses.EditTextUpdaterTwo;
import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.GameModel;
import com.goblinstudios.equationeaters.Math.MovingAlgorithm;
import com.goblinstudios.equationeaters.Math.TileMap;
import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.R;

public class GameController implements View.OnTouchListener, View.OnClickListener {

    GLSurfaceView vContext;
    int deviceWidth = 0;
    int deviceHeight = 0;
    float alteredX = 0;
    float alteredY = 0;
    public static GameModel modelContext;
    MovingCharacter movingChar;
    boolean firstTouch = true ;

    public GameController(GLSurfaceView cIn) {
        vContext = cIn;
        deviceWidth = GameAssets.deviceWidth;
        deviceHeight = GameAssets.deviceHeight;
    }

    public void setChar() {
        movingChar = GameScreen.movingChar;
    }

    public boolean onTouch(View v, MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_UP && firstTouch && GameScreen.gameStarted && movingChar.arrivedAtDestination) {

            System.out.println("touched : " + e.getX() + " --- " + e.getY() + GameScreen.gameStarted);
            alteredX = (float)(e.getX() * 480.0 / deviceWidth);
            alteredY = (float)(e.getY() * 800.0 / deviceHeight);
            System.out.println("altered : " + (e.getX() * 480.0 / deviceWidth) + " --- " + (e.getY() * 800.0 / deviceHeight));
            if (alteredY > 80 + 50 && alteredY < 190 + 50) {
                if (alteredX < 140 && alteredX > 40) {
                    if (movingChar.tilePosition.x != 0 || movingChar.tilePosition.y != 0) {
                        System.out.println("tile 00 pressed");
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(0, 0);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0, 0));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(0,0);
                    }
                }
                if (alteredX > 140 && alteredX < 240) {
                    if (movingChar.tilePosition.x != 1 || movingChar.tilePosition.y != 0) {
                        System.out.println("tile 10 pressed");
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(1, 0);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1, 0));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(1,0);
                    }
                }
                if (alteredX > 240 && alteredX < 340) {
                    if (movingChar.tilePosition.x != 2 || movingChar.tilePosition.y != 0) {
                        System.out.println("tile 20 pressed");
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(2, 0);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2, 0));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(2,0);
                    }
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 30 pressed");
                    if (movingChar.tilePosition.x != 3 || movingChar.tilePosition.y != 0) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(3, 0);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3, 0));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(3,0);
                    }
                }
            }

            if (alteredY > 190 + 50 && alteredY < 300 + 50) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 01 pressed");
                    if (movingChar.tilePosition.x != 0 || movingChar.tilePosition.y != 1) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(0, 1);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0, 1));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(0,1);
                    }
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 11 pressed");
                    if (movingChar.tilePosition.x != 1 || movingChar.tilePosition.y != 1) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(1, 1);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1, 1));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(1,1);
                    }
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 21 pressed");
                    if (movingChar.tilePosition.x != 2 || movingChar.tilePosition.y != 1) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(2, 1);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2, 1));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(2,1);
                    }
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 31 pressed");
                    if (movingChar.tilePosition.x != 3 || movingChar.tilePosition.y != 1) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(3, 1);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3, 1));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(3,1);
                    }
                }
            }

            if (alteredY > 300 + 50 && alteredY < 410 + 50) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 02 pressed");
                    if (movingChar.tilePosition.x != 0 || movingChar.tilePosition.y != 2) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(0, 2);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0, 2));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(0,2);
                    }
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 12 pressed");
                    if (movingChar.tilePosition.x != 1 || movingChar.tilePosition.y != 2) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(1, 2);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1, 2));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(1,2);
                    }
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 22 pressed");
                    if (movingChar.tilePosition.x != 2 || movingChar.tilePosition.y != 2) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(2, 2);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2, 2));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(2,2);
                    }
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 32 pressed");
                    if (movingChar.tilePosition.x != 3 || movingChar.tilePosition.y != 2) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(3, 2);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3, 2));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(3,2);
                    }
                }
            }

            if (alteredY > 410 + 50 && alteredY < 520 + 50) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 03 pressed");
                    if (movingChar.tilePosition.x != 0 || movingChar.tilePosition.y != 3) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(0, 3);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0, 3));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(0,3);
                    }
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 13 pressed");
                    if (movingChar.tilePosition.x != 1 || movingChar.tilePosition.y != 3) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(1, 3);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1, 3));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(1,3);
                    }
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 23 pressed");
                    if (movingChar.tilePosition.x != 2 || movingChar.tilePosition.y != 3) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(2, 3);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2, 3));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(2,3);
                    }
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 33 pressed");
                    if (movingChar.tilePosition.x != 3 || movingChar.tilePosition.y != 3) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(3, 3);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3, 3));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(3,3);
                    }
                }
            }

            if (alteredY > 520 + 50 && alteredY < 630 + 50) {
                if (alteredX < 140 && alteredX > 40) {
                    if (movingChar.tilePosition.x != 0 || movingChar.tilePosition.y != 4) {
                        System.out.println("tile 04 pressed");
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(0, 4);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0, 4));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(0,4);
                    }
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 14 pressed");
                    if (movingChar.tilePosition.x != 1 || movingChar.tilePosition.y != 4) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(1, 4);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1, 4));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(1,4);
                    }
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 24 pressed");
                    if (movingChar.tilePosition.x != 2 || movingChar.tilePosition.y != 4) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(2, 4);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2, 4));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(2,4);
                    }
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 34 pressed");
                    if (movingChar.tilePosition.x != 3 || movingChar.tilePosition.y != 4) {
                        modelContext.isTileSelected = true;
                        modelContext.setSelected(3, 4);
                        MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3, 4));
                        MovingAlgorithm.printList();
                        movingChar.pathList = MovingAlgorithm.pathList;
                    } else {
                        eatTile(3,4);
                    }
                }
            }

        } else if (e.getAction() == MotionEvent.ACTION_UP){
            System.out.println("first touch");
            firstTouch = true;
        }
        return true;

    }

    public void onClick(View v) {

    }

    public void eatTile(int tileX, int tileY) {
        movingChar.eatStamp = (float)GameScreen.totalTime;
        movingChar.eatingTime = 0;
        if (TileMap.tileList[tileX][tileY].activeTile && !TileMap.tileList[tileX][tileY].eatenTile) {

            movingChar.movingState = MovingCharacter.MoveState.EATING;
            TileMap.tileList[tileX][tileY].activeTile = false;
            TileMap.tileList[tileX][tileY].eatenTile = true;
            if (TileMap.tileList[tileX][tileY].totalSum == TileMap.magicNumber) {
                GameScreen.score++;

                EditTextUpdater.resourceID = R.id.scoreText;
                EditTextUpdater.text = "Score " + GameScreen.score;
                EditTextUpdater.visibility = true;
                EditTextUpdater.isRunning = true;
                GameAssets.gameActivity.runOnUiThread(GameScreen.etu);

                GameScreen.currentLevelCorrectAnswers++;

                if (GameScreen.currentLevelCorrectAnswers == TileMap.numDesignatedAnswers) {
                    GameScreen.level++;
                    EditTextUpdaterTwo.resourceID = R.id.levelText;
                    EditTextUpdaterTwo.text = "Level " + GameScreen.level;
                    EditTextUpdaterTwo.visibility = true;
                    EditTextUpdaterTwo.isRunning = true;
                    GameAssets.gameActivity.runOnUiThread(GameScreen.etu2);
                    int randomNum = 0;

                    do {
                        randomNum = TileMap.rand.nextInt(11 + ((GameScreen.level - 1) * (GameScreen.level -1 )));
                    } while (randomNum <= TileMap.magicNumber && randomNum < 1000);
                    EditTextUpdaterThree.resourceID = R.id.lunchNumberNum;
                    EditTextUpdaterThree.text = "" + randomNum;
                    EditTextUpdaterThree.visibility = true;
                    EditTextUpdaterThree.isRunning = true;
                    GameAssets.gameActivity.runOnUiThread(GameScreen.etu3);

                    TileMap.refill(movingChar.tilePosition.x, movingChar.tilePosition.y, randomNum);
                }
            } else {
                GameScreen.lives--;
                movingChar.movingState = MovingCharacter.MoveState.SICK;
                GameScreen.checkGameOver();
            }
        }

    }

}