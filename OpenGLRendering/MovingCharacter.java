package com.goblinstudios.equationeaters.OpenGLRendering;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;

import java.util.LinkedList;

import static com.goblinstudios.equationeaters.GameAssets.deviceHeight;
import static com.goblinstudios.equationeaters.GameAssets.deviceWidth;
import static com.goblinstudios.equationeaters.OpenGLRendering.GameController.modelContext;

public class MovingCharacter {

    enum MoveState {LEFT,DOWN,UP,RIGHT}
    MoveState movingState = MoveState.DOWN;

    DynamicSprite movingLeft, movingDown, movingUp, movingRight;
    Vector2f position = new Vector2f((91 * deviceWidth / 480) + (2 * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (3 * 90.75f * deviceHeight / 800)));
    Vector2f targetPosition = new Vector2f(position.x, position.y);
    LinkedList<Vector2i> pathList = new LinkedList();
    Vector2i tilePosition = new Vector2i(2,3);
    Vector2i frameSize;
    public static boolean arrivedAtDestination = false;

    public MovingCharacter(int xTile, int yTile) {
        position = calcPosition(new Vector2i(xTile,yTile));
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
                movingState = MoveState.DOWN;
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
        newPos.x = ((91 * GameAssets.deviceWidth / 480) + (posIn.x * 99.6f * GameAssets.deviceWidth / 480));
        newPos.y = (((800 - 226) * GameAssets.deviceHeight / 800) - (posIn.y * 90.75f * GameAssets.deviceHeight / 800));
        System.out.println("calc x : " + newPos.x + " -- y " + newPos.y);
        return newPos;
    }

    public Vector2i calcTilePosition(Vector2f tilePos) {
        Vector2i newPos = new Vector2i(0,0);
        newPos.x = Math.round((tilePos.x - (91 * GameAssets.deviceWidth / 480)) / (99.6f * GameAssets.deviceWidth / 480));
        newPos.y = Math.round((((800 - 226) * GameAssets.deviceHeight / 800) - tilePos.y) / (90.75f * GameAssets.deviceHeight / 800));
        System.out.println("new pos : " + newPos.x + " --- " + newPos.y + "path list -- " + pathList.size());
        return newPos;
    }
}
