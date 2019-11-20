package com.goblinstudios.equationeaters.GUIClasses;

import com.goblinstudios.equationeaters.GameAssets;

public class ViewUpdater implements Runnable {

    static int iconView;
    static int iconPic;
    static boolean visibility;
    static boolean isRunning = false;

    @Override

    public void run() {
        while (isRunning) {
            System.out.println("run vu");
            GameAssets.gameActivity.updateImage(iconView, visibility, iconPic);
            isRunning = false;
        }
    }

}