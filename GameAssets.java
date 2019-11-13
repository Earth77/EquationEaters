package com.goblinstudios.equationeaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector4f;
import com.goblinstudios.equationeaters.OpenGLRendering.OpenGLView;
import com.goblinstudios.equationeaters.OpenGLRendering.Sprite;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class GameAssets {

    public enum GameState {openingScreen, gameScreen, customizeScreen}

    public static GameState gameState = GameState.openingScreen;

    public static int deviceWidth = 0;
    public static int deviceHeight = 0;

    public static int textureOffsetInt = 0;

    public static Vector2f tileSize = new Vector2f(0,0);

    public static int openingScreenTexture = 1;
    public static int openingScreenTextureWidth = 512;
    public static int openingScreenTextureHeight = 1024;

    public static int gameScreenTexture = 1;
    public static int gameScreenTextureWidth = 512;
    public static int gameScreenTextureHeight = 1024;

    public static int simpleBoxTexture = 1;
    public static int simpleBoxTextureWidth = 128;
    public static int simpleBoxTextureHeight = 128;

    public static int firstCharTexture = 1;
    public static int firstCharTextureWidth = 512;
    public static int firstCharTextureHeight = 512;

    public static int fontTexture = 1;
    public static int fontTextureWidth = 128;
    public static int fontTextureHeight = 128;

    public static OpenGLView openGLView;

    public static void loadBaseAssets(Context context) {

        //load textures
        openingScreenTexture = loadTexture(context, R.drawable.openingscreen);
        gameScreenTexture = loadTexture(context, R.drawable.testtilescreen);
        simpleBoxTexture = loadTexture(context, R.drawable.simplebox);
        firstCharTexture = loadTexture(context, R.drawable.testchar);
        fontTexture = loadTexture(context, R.drawable.font);
    }

    static int loadTexture(final Context context, final int resourceId)
    {
        int[] intBuf = {1 + textureOffsetInt};
        IntBuffer textureHandle = initIntBuffer(intBuf);

        System.out.println("pre" + textureHandle.get(0));
        GLES30.glGenTextures(1, textureHandle);
        System.out.println("post" + textureHandle.get(0));

        if (textureHandle.get(0) != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureHandle.get(0));

            // Set filtering
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
            textureOffsetInt++;
        }

        if (textureHandle.get(0) == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle.get(0);
    }

    static FloatBuffer initFloatBuffer(float[] buffer)
    {
        //4= tipe float butuh 4 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer aFloatBuffer = bb.asFloatBuffer();
        aFloatBuffer.put(buffer);
        aFloatBuffer.position(0);
        return aFloatBuffer;
    }

    static IntBuffer initIntBuffer(int[] buffer)
    {
        //2= tipe short butuh 2 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer aIntBuffer = bb.asIntBuffer();
        aIntBuffer.put(buffer);
        aIntBuffer.position(0);
        return aIntBuffer;
    }

    static ShortBuffer initShortBuffer(short[] buffer)
    {
        //2= tipe short butuh 2 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer aShortBuffer = bb.asShortBuffer();
        aShortBuffer.put(buffer);
        aShortBuffer.position(0);
        return aShortBuffer;
    }

    static InputStream makeStream(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        return inputStream;
    }
}
