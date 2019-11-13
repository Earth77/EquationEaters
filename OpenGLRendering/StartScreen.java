package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLES30;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector4f;

import javax.microedition.khronos.opengles.GL10;

public class StartScreen extends OpenGLRenderer {

    Sprite openingScreen;

    public StartScreen(Context context, int widthIn, int heightIn) {
        super(context, widthIn, heightIn);
        createSprites();
    }

    private void createSprites() {
        System.out.println("creating sprite : " + GameAssets.openingScreenTextureWidth + " --- " + GameAssets.openingScreenTextureHeight);
        openingScreen = new Sprite(screenWidth, screenHeight, screenWidth, screenHeight, GameAssets.openingScreenTexture, GameAssets.openingScreenTextureWidth, GameAssets.openingScreenTextureHeight,0, false, false, new Vector4f(0,224,480,800));
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClearColor(0.34f, 0.34f, 0.67f, 0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
  //      System.out.println("using program shader int : " + shaderInt);
        GLES30.glUseProgram(shaderInt);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        openingScreen.drawNarrow(shaderInt,0,new Vector2f(GameAssets.deviceWidth / 2,GameAssets.deviceHeight / 2), GameAssets.deviceWidth, GameAssets.deviceHeight);
    }

}
