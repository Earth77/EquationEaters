package com.goblinstudios.equationeaters.OpenGLRendering;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.goblinstudios.equationeaters.GameActivity;
import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.GameModel;

public class OpenGLView extends GLSurfaceView {

    public static OpenGLRenderer masterRenderer;
    public static GameModel gModel = new GameModel();

    public OpenGLView(Context context) {
        super(context);
        init(context);
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        System.out.println("init");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;
        GameAssets.deviceWidth = deviceWidth;
        GameAssets.deviceHeight = deviceHeight;
        if (GameAssets.gameState == GameAssets.GameState.openingScreen) {
            masterRenderer = new StartScreen(context, deviceWidth, deviceHeight);
        } else {
            masterRenderer = new GameScreen(context, deviceWidth, deviceHeight);
            masterRenderer.modelContext = gModel;
            GameActivity.gModel = gModel;
        }
        setRenderer(masterRenderer);
        GameAssets.openGLView = this;
        System.out.println("OPENGLVIEW");
    }

}
