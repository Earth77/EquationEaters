package com.goblinstudios.equationeaters.OpenGLRendering;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.TileMap;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;

import java.util.LinkedList;

import static com.goblinstudios.equationeaters.GameAssets.deviceHeight;
import static com.goblinstudios.equationeaters.GameAssets.deviceWidth;
import static com.goblinstudios.equationeaters.OpenGLRendering.GameController.modelContext;

public class MovingCharacter {

    enum MoveState {LEFT,DOWN,UP,RIGHT, EATING, PREPARETOEAT, STANDSTILL,SICK}
    MoveState movingState = MoveState.DOWN;

    DynamicSprite movingLeft, movingDown, movingUp, movingRight, eating, prepareToEat, standStill, sick;
    Vector2f position = new Vector2f((91 * deviceWidth / 480) + (2 * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (3 * 90.75f * deviceHeight / 800)));
    Vector2f targetPosition = new Vector2f(position.x, position.y);
    LinkedList<Vector2i> pathList = new LinkedList();
    Vector2i tilePosition = new Vector2i(2,3);
    Vector2i frameSize;
    public static boolean arrivedAtDestination = false;
    public static float eatingTime = 0;
    public static float eatStamp = 0;
    public static float sickTime = 0;
    public static float sickStamp = 0;

    public MovingCharacter(int xTile, int yTile) {
        tilePosition.x = xTile;
        tilePosition.y = yTile;
        position = calcPosition(new Vector2i(xTile,yTile));
        targetPosition = position;
        movingState = MoveState.DOWN;
    }

    public void draw(int renderingProgram, double frameNumber, Vector2f position) {
        switch (movingState) {
            case LEFT:
                movingLeft.drawFront(renderingProgram, frameNumber, position, -0.02f);
                break;

            case RIGHT:
                movingRight.drawFront(renderingProgram, frameNumber, position, -0.02f);
                break;

            case UP:
                movingUp.drawFront(renderingProgram, frameNumber, position, -0.02f);
                break;

            case DOWN:
                movingDown.drawFront(renderingProgram, frameNumber, position, -0.02f);
                break;

            case EATING:
                eating.drawFront(renderingProgram,frameNumber,position, -0.02f);
                break;

            case PREPARETOEAT:
                prepareToEat.drawFront(renderingProgram,frameNumber,position,-0.02f);
                break;

            case STANDSTILL:
                standStill.drawFront(renderingProgram,frameNumber,position,-0.02f);
                break;

            case SICK:
                sick.drawFront(renderingProgram,frameNumber,position,-0.02f);
                break;

        }


        if (movingState == MoveState.EATING) {
            System.out.println("es : " + sickStamp + " st : " + GameScreen.sickTime + " st : " + sickTime);
            eatingTime = (float)(GameScreen.totalTime - eatStamp) / 100;
        }

        if (movingState == MoveState.SICK) {
            System.out.println("ss : " + eatStamp + " ct : " + GameScreen.currentTime + " et : " + eatingTime);
            sickTime = (float)(GameScreen.totalTime - sickStamp) / 100;
        }
    }

    public void move(double deltaTime) {
  //      System.out.println("moving pos x : " + position.x + " -- pos y : " + position.y + " -- target x : " + targetPosition.x + " -- target y " + targetPosition.y);
        if (!pathList.isEmpty()) {
  //          targetPosition.x = (pathList.get(0).x);
   //         targetPosition.y = (pathList.get(0).y);
            targetPosition = calcPosition(pathList.get(0));
        }

        if (targetPosition.x == position.x && targetPosition.y == position.y) {

            //System.out.println(" state : " + movingState + " -- eat time : " + eatingTime);

            if (!pathList.isEmpty()) {
                pathList.removeFirst();
            System.out.println("remove first");
            //              targetPosition.x = (pathList.get(0).x);
            //              targetPosition.y = (pathList.get(0).y);

                if (!pathList.isEmpty()) {
                    System.out.println("calc new position");
                    targetPosition = calcPosition(pathList.get(0));
                }
            } else {
             //   System.out.println("arrive at destination true");
                arrivedAtDestination = true;
                if (movingState != MoveState.EATING && TileMap.tileList[tilePosition.x][tilePosition.y].activeTile) {
                    movingState = MoveState.PREPARETOEAT;
                }

                if (movingState != MoveState.EATING && movingState != MoveState.SICK && movingState != MoveState.PREPARETOEAT || eatingTime > 0.8f && movingState == MoveState.EATING|| !(TileMap.tileList[tilePosition.x][tilePosition.y].activeTile) && eatingTime > 0.8f || (TileMap.tileList[tilePosition.x][tilePosition.y].eatenTile) && eatingTime > 0.8f || sickTime > 0.8f && movingState == MoveState.SICK) {
                    //System.out.println("set state down : " + movingState + " - et - " + eatingTime);
                    movingState = MoveState.DOWN;
                } else{
                    System.out.println("eating");
                }
            }
        }

        if (targetPosition.x - position.x >= 1) {
            arrivedAtDestination = false;
            position.x += (GameAssets.deviceWidth / 256) * deltaTime;
            movingState = MoveState.RIGHT;
            if (targetPosition.x - position.x < 1) {
                position.x = targetPosition.x;
                tilePosition = calcTilePosition(position);
            }
        }

        if (position.x - targetPosition.x >= 1) {
            arrivedAtDestination = false;
            position.x -= (GameAssets.deviceWidth / 256) * deltaTime;
            movingState = MoveState.LEFT;
            if (position.x - targetPosition.x < 1) {
                position.x = targetPosition.x;
                tilePosition = calcTilePosition(position);
            }
        }

        if (position.y - targetPosition.y >= 1) {
            arrivedAtDestination = false;
            position.y -= (GameAssets.deviceWidth / 256) * deltaTime;
            System.out.println("set state down 1");
            movingState = MoveState.DOWN;
            if (position.y - targetPosition.y < 1) {
                position.y = targetPosition.y;
                tilePosition = calcTilePosition(position);
            }
        }

        if (targetPosition.y - position.y >= 1) {
            arrivedAtDestination = false;
            position.y += (GameAssets.deviceWidth / 256) * deltaTime;
            movingState = MoveState.UP;
            if (targetPosition.y - position.y < 1) {
                position.y = targetPosition.y;
                tilePosition = calcTilePosition(position);
            }
        }

    }

    public Vector2f calcPosition(Vector2i posIn) {
        Vector2f newPos = new Vector2f(0,0);
        newPos.x = ((81 * GameAssets.deviceWidth / 480) + (posIn.x * 109f * GameAssets.deviceWidth / 480));
        newPos.y = (((800 - 166) * GameAssets.deviceHeight / 800) - (posIn.y * 107.5f * GameAssets.deviceHeight / 800));
        System.out.println("calc x : " + newPos.x + " -- y " + newPos.y);
        return newPos;
    }

    public Vector2i calcTilePosition(Vector2f tilePos) {
        Vector2i newPos = new Vector2i(0,0);
        newPos.x = Math.round((tilePos.x - (81 * GameAssets.deviceWidth / 480)) / (109 * GameAssets.deviceWidth / 480));
        newPos.y = Math.round((((800 - 166) * GameAssets.deviceHeight / 800) - tilePos.y) / (107.5f * GameAssets.deviceHeight / 800));
        System.out.println("new pos : " + newPos.x + " --- " + newPos.y + "path list -- " + pathList.size());
        return newPos;
    }
}
