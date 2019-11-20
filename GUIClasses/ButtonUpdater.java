package com.goblinstudios.equationeaters.GUIClasses;

import com.goblinstudios.equationeaters.GameAssets;

public class ButtonUpdater implements Runnable {

    public static int resourceID;
    public static String text;
    public static boolean visibility;
    public static boolean isRunning = false;

    @Override

    public void run() {
        while (isRunning) {
            System.out.println("run bu");
            GameAssets.gameActivity.updateButton(resourceID, text, visibility);
            isRunning = false;
        }
    }

}