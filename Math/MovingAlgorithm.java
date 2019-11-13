package com.goblinstudios.equationeaters.Math;

import java.util.LinkedList;
import java.util.Random;

public class MovingAlgorithm {

    static boolean coinFlip = false;
    static Random rand = new Random();
    public static LinkedList<Vector2i> pathList = new LinkedList();
    static boolean pathFound = false;
    static int iterationCount = 0;

    public static LinkedList<Vector2i> calculatePath(Vector2i occupiedTile, Vector2i targetTile) {

        System.out.println("occupied tile : " + occupiedTile.x + " -- " + occupiedTile.y + " -- target tile : " + targetTile.x + " -- " + targetTile.y);

        iterationCount = 0;
        pathList.clear();
        pathFound = false;
 //       pathList.add(occupiedTile);

        if (targetTile.x > occupiedTile.x) {
            pathList.add(new Vector2i(occupiedTile.x + 1, occupiedTile.y));
        } else if (targetTile.x < occupiedTile.x) {
            pathList.add(new Vector2i(occupiedTile.x - 1, occupiedTile.y));
        } else if (targetTile.y > occupiedTile.y) {
            pathList.add(new Vector2i(occupiedTile.x, occupiedTile.y + 1));
        } else if (targetTile.y < occupiedTile.y) {
            pathList.add(new Vector2i(occupiedTile.x, occupiedTile.y - 1));
        }

        if (!pathList.isEmpty()) {
            if (pathList.get(0).x == targetTile.x && pathList.get(0).y == targetTile.y) {
                pathFound = true;
            }
        }

        while (!pathFound && !pathList.isEmpty()) {
            System.out.println("iterate : count " + iterationCount);
            iterate(targetTile);
            iterationCount++;
        }

        return pathList;
    }

    public static void iterate(Vector2i targetTile) {

        int horizont = rand.nextInt(1);
        if (horizont == 0) {
            coinFlip = false;
        } else {
            coinFlip = true;
        }

        if (coinFlip) {
            if (targetTile.x > pathList.get(iterationCount).x) {
            pathList.add(new Vector2i(pathList.get(iterationCount).x + 1, pathList.get(iterationCount).y));
            if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                pathFound = true;
            }
            return;
            } else if (targetTile.x < pathList.get(iterationCount).x) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x - 1, pathList.get(iterationCount).y));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            } else if (targetTile.y > pathList.get(iterationCount).y) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x, pathList.get(iterationCount).y + 1));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            } else if (targetTile.y < pathList.get(iterationCount).y) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x, pathList.get(iterationCount).y - 1));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            }
        } else {
            if (targetTile.y > pathList.get(iterationCount).y) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x, pathList.get(iterationCount).y + 1));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            } else if (targetTile.y < pathList.get(iterationCount).y) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x, pathList.get(iterationCount).y - 1));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            } else if (targetTile.x > pathList.get(iterationCount).x) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x + 1, pathList.get(iterationCount).y));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            } else if (targetTile.x < pathList.get(iterationCount).x) {
                pathList.add(new Vector2i(pathList.get(iterationCount).x - 1, pathList.get(iterationCount).y));
                if (pathList.get(iterationCount + 1).x == targetTile.x && pathList.get(iterationCount + 1).y == targetTile.y) {
                    pathFound = true;
                }
                return;
            }

        }

    }

    public static void printList() {
        for (int i = 0; i < pathList.size(); i++) {
            System.out.println("i : " + i + " -- x : " + pathList.get(i).x + " ---- y : " + pathList.get(i).y);
        }
    }

}
