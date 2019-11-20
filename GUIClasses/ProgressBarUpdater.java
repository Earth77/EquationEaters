package com.goblinstudios.equationeaters.GUIClasses;

import com.goblinstudios.equationeaters.GameAssets;

public class ProgressBarUpdater implements Runnable {

    public static int resourceID;
    public static boolean visibility;
    public static boolean isRunning = false;

    @Override

    public void run() {
        while (isRunning) {
            System.out.println("run progress bar updater");
            GameAssets.gameActivity.updateProgressBar(resourceID,visibility);
            isRunning = false;
        }
    }

}