package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.GameModel;
import com.goblinstudios.equationeaters.Math.MovingAlgorithm;
import com.goblinstudios.equationeaters.Math.Vector2i;

public class GameController implements View.OnTouchListener, View.OnClickListener {

    GLSurfaceView vContext;
    int deviceWidth = 0;
    int deviceHeight = 0;
    float alteredX = 0;
    float alteredY = 0;
    public static GameModel modelContext;
    MovingCharacter movingChar;

    public GameController(GLSurfaceView cIn) {
        vContext = cIn;
        deviceWidth = GameAssets.deviceWidth;
        deviceHeight = GameAssets.deviceHeight;
    }

    public void setChar() {
        movingChar = GameScreen.movingChar;
    }

    public boolean onTouch(View v, MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("touched : " + e.getX() + " --- " + e.getY());
            alteredX = (float)(e.getX() * 480.0 / deviceWidth);
            alteredY = (float)(e.getY() * 800.0 / deviceHeight);
            System.out.println("altered : " + (e.getX() * 480.0 / deviceWidth) + " --- " + (e.getY() * 800.0 / deviceHeight));
            if (alteredY > 180 && alteredY < 270) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 00 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(0,0);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0,0));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 10 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(1,0);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1,0));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 20 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(2,0);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2,0));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 30 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(3,0);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3,0));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
            }

            if (alteredY > 270 && alteredY < 360) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 01 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(0,1);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0,1));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 11 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(1,1);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1,1));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 21 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(2,1);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2,1));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 31 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(3,1);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3,1));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
            }

            if (alteredY > 360 && alteredY < 450) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 02 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(0,2);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0,2));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 12 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(1,2);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1,2));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 22 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(2,2);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2,2));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 32 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(3,2);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3,2));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
            }

            if (alteredY > 450 && alteredY < 540) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 03 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(0,3);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0,3));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 13 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(1,3);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1,3));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 23 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(2,3);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2,3));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 33 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(3,3);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3,3));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
            }

            if (alteredY > 540 && alteredY < 630) {
                if (alteredX < 140 && alteredX > 40) {
                    System.out.println("tile 04 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(0,4);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(0,4));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 140 && alteredX < 240) {
                    System.out.println("tile 14 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(1,4);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(1,4));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 240 && alteredX < 340) {
                    System.out.println("tile 24 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(2,4);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(2,4));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
                if (alteredX > 340 && alteredX < 440) {
                    System.out.println("tile 34 pressed");
                    modelContext.isTileSelected = true;
                    modelContext.setSelected(3,4);
                    MovingAlgorithm.calculatePath(movingChar.tilePosition, new Vector2i(3,4));
                    MovingAlgorithm.printList();
                    movingChar.pathList = MovingAlgorithm.pathList;
                }
            }

        }
        return true;

    }

    public void onClick(View v) {

    }

}