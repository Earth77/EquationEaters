package com.goblinstudios.equationeaters.GUIClasses;

import com.goblinstudios.equationeaters.GameAssets;

public class EditTextUpdaterEight implements Runnable {

    public static int resourceID;
    public static String text;
    public static boolean visibility;
    public static boolean isRunning = false;

    @Override

    public void run() {
        while (isRunning) {
            System.out.println("run etu8");
            GameAssets.gameActivity.updateText(resourceID, text, visibility);
            isRunning = false;
        }
    }

}