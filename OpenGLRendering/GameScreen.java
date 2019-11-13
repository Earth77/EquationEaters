package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLES30;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.GameModel;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.Math.Vector4f;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import static com.goblinstudios.equationeaters.GameAssets.deviceHeight;
import static com.goblinstudios.equationeaters.GameAssets.deviceWidth;

public class GameScreen extends OpenGLRenderer {

    Sprite gameScreen;
    Sprite selectedBoxScreen;

    static MovingCharacter movingChar;
    ArrayList<Vector4f> movingUp = new ArrayList();
    ArrayList<Vector4f> movingDown = new ArrayList();
    ArrayList<Vector4f> movingLeft = new ArrayList();
    ArrayList<Vector4f> movingRight = new ArrayList();

    int frameNumber = 0;

    double lastTime, currentTime, deltaTime;
    boolean lastTimeSet = false;

    Font font;

    Random rand = new Random();

    public GameScreen(Context context, int widthIn, int heightIn) {
        super(context, widthIn, heightIn);
        movingUp.add(new Vector4f(256,0,128,128));
        movingUp.add(new Vector4f(256,128,128,128));
        movingUp.add(new Vector4f(256,256,128,128));
        movingUp.add(new Vector4f(256,384,128,128));
        movingDown.add(new Vector4f(0,0,128,128));
        movingDown.add(new Vector4f(0,128,128,128));
        movingDown.add(new Vector4f(0,256,128,128));
        movingDown.add(new Vector4f(0,384,128,128));
        movingLeft.add(new Vector4f(128,0,128,128));
        movingLeft.add(new Vector4f(128,128,128,128));
        movingLeft.add(new Vector4f(128,256,128,128));
        movingLeft.add(new Vector4f(128,384,128,128));
        movingRight.add(new Vector4f(384,0,128,128));
        movingRight.add(new Vector4f(384,128,128,128));
        movingRight.add(new Vector4f(384,256,128,128));
        movingRight.add(new Vector4f(384,384,128,128));
        createSprites();
    }

    private void createSprites() {
 //       System.out.println("creating sprite : " + GameAssets.openingScreenTexture + ":" + screenWidth + " : " + screenHeight);
        gameScreen = new Sprite(screenWidth, screenHeight, screenWidth, screenHeight, GameAssets.gameScreenTexture, GameAssets.gameScreenTextureWidth, GameAssets.gameScreenTextureHeight,0, false, false, new Vector4f(0,224,480,800));
 //       System.out.println("selected  box width : " + (99.5 * screenWidth / 480) + " --- " + (93 * screenHeight / 800));
        selectedBoxScreen = new Sprite(screenWidth, screenHeight, 100 * screenWidth / 480, 93 * screenHeight / 800, GameAssets.simpleBoxTexture, GameAssets.simpleBoxTextureWidth,0, false, false, new Vector4f(0,0,128,128));
        movingChar = new MovingCharacter(rand.nextInt(4),rand.nextInt(5));
        movingChar.movingUp = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(128,128), new Vector4f(0,0,0,0), 512, true, movingUp, 0);
        movingChar.movingDown = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(128,128), new Vector4f(0,0,0,0), 512, true, movingDown, 0);
        movingChar.movingRight = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(128,128), new Vector4f(0,0,0,0), 512, true, movingRight, 0);
        movingChar.movingLeft = new DynamicSprite(GameAssets.firstCharTexture, new Vector2i(128,128), new Vector4f(0,0,0,0), 512, true, movingLeft, 0);
        font = new Font(GameAssets.fontTexture, new Vector2f(0,0), new Vector4f(0,0,128,128), GameAssets.fontTextureWidth, true, 0, 16,16,16,16);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        currentTime = System.nanoTime();

        if (!lastTimeSet) {
            deltaTime = 0;
        } else {
            deltaTime = (currentTime - lastTime) / 10000000;
      //      System.out.println("dt : " + deltaTime);
        }

        frameNumber++;
      //  System.out.println("gs ods");
        GLES30.glClearColor(0.34f, 0.34f, 0.67f, 0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        //      System.out.println("using program shader int : " + shaderInt);
        GLES30.glUseProgram(shaderInt);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        gameScreen.draw(shaderInt,0,new Vector2f(deviceWidth / 2, deviceHeight / 2));
        movingChar.move(deltaTime);
        movingChar.draw(shaderInt,frameNumber,new Vector2f(movingChar.position.x, movingChar.position.y));
        if (modelContext.isTileSelected) {
     //       System.out.println("Selected tile x: " + modelContext.selectedTile.x + " --- " + modelContext.selectedTile.y);
     //       System.out.println("Drawing x : " + ((40 * deviceWidth / 480) + (modelContext.selectedTile.x * 100 * deviceWidth / 480)) + " -- drawing y : " + (((800 - 223) * deviceHeight / 800) - (modelContext.selectedTile.y * 91 * deviceHeight / 800)));
            if (!MovingCharacter.arrivedAtDestination) {
                selectedBoxScreen.draw(shaderInt, 0, new Vector2f((91 * deviceWidth / 480) + (modelContext.selectedTile.x * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (modelContext.selectedTile.y * 90.75f * deviceHeight / 800))));
            } else {
                System.out.println("new color box");
                selectedBoxScreen.drawColor(shaderInt, 0, new Vector2f((91 * deviceWidth / 480) + (modelContext.selectedTile.x * 99.6f * deviceWidth / 480), (((800 - 226) * deviceHeight / 800) - (modelContext.selectedTile.y * 90.75f * deviceHeight / 800))), 100);
            }
        }

        font.draw(shaderInt, "2 ; 8", new Vector2f((91 * deviceWidth / 480) + (modelContext.selectedTile.x * 99.6f * deviceWidth / 480),((800 - 226) * deviceHeight / 800) - (modelContext.selectedTile.y * 90.75f * deviceHeight / 800)));

        if (!lastTimeSet) {
            lastTime = System.nanoTime();
            lastTimeSet = true;
        } else {
            lastTime = currentTime;
        }
    }
}
