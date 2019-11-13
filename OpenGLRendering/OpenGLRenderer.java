package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.GameModel;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector4f;
import com.goblinstudios.equationeaters.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Greg Parton 2019
 **/

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    Context context;
    FileReader reader = new FileReader();
    static int shaderInt = -100;
    private IntBuffer vao;
    private int vaoIB[] = new int[1];
    private int vbo[] = new int[2];
    int screenWidth;
    int screenHeight;
    Sprite openScreen;
    GameModel modelContext;

    public OpenGLRenderer(Context ctx, int widthIn, int heightIn) {
        context = ctx;
        screenWidth = widthIn;
        screenHeight = heightIn;
        openScreen = new Sprite(screenWidth, screenHeight, screenWidth, screenHeight, 1, GameAssets.openingScreenTextureWidth, 0, false, false, new Vector4f(0,224,480,800));
        System.out.println("Creating renderer : " + screenWidth + " -- " + screenHeight);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
     //   if (shaderInt == -100) {
            System.out.println("@ setup verts and shader");
            GameAssets.loadBaseAssets(context);
            shaderInt = setupShaders(gl10, eglConfig);
     //   }

        setupVertices();
        GLES30.glClearColor(1f, 0, 0, 1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        screenWidth = i;
        screenHeight = i1;
        System.out.println("Surface changed screen width : " + screenWidth + " -- screen height : " + screenHeight);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClearColor(0.34f, 0.34f, 0.67f, 0);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glUseProgram(shaderInt);
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        openScreen.draw(shaderInt,0,new Vector2f(GameAssets.deviceWidth / 2,GameAssets.deviceHeight / 2));
    }

    int setupShaders(GL10 gl, EGLConfig eglConfig) {

        String vshaderSource = "";

        try {
            vshaderSource = ResourceHelper.readRawTextFile(context, R.raw.vertex_shader);
        } catch (Exception e) {
            System.out.println("Couldn't read vert shader!");
            System.out.println(e);
        };

        String fshaderSource = "";

        try {
            fshaderSource = ResourceHelper.readRawTextFile(context, R.raw.frag_shader);
        } catch (Exception e) {
            System.out.println("Couldn't read frag shader!");
            System.out.println(e);
        };

        int vShader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vShader, vshaderSource); //3 is the count of lines source
        GLES30.glCompileShader(vShader);

        int fShader = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(fShader, fshaderSource); //4 is the count of lines of source
        GLES30.glCompileShader(fShader);

        int vfProgram = GLES30.glCreateProgram();
        GLES30.glAttachShader(vfProgram, vShader);
        GLES30.glAttachShader(vfProgram, fShader);
        GLES30.glLinkProgram(vfProgram);

        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(vfProgram, GLES30.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0]==0) {
            System.out.println("Error linking program: " + GLES30.glGetProgramInfoLog(vfProgram));
        }

        GLES30.glDeleteShader(vShader);
        GLES30.glDeleteShader(fShader);
        System.out.println("setup shader complete : " + vfProgram);
        return vfProgram;

    }

    void setupVertices() {
        //the vert positions is just a placeholder, actual vertex positions are calculated in the vert shader based on values from the sprite
        float[] vertexPositions =
                {1, 1, 0.0f,
                        1, 1, 0.0f,
                        1, 1, 0.0f,
                        1, 1, 0.0f,
                        1, 1, 0.0f,
                        1, 1, 0};

        vao = initIntBuffer(vaoIB);

        GLES30.glGenVertexArrays(1, vao);
        GLES30.glBindVertexArray(vao.get(0));
        GLES30.glGenBuffers(vbo.length, vbo, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0]);
        FloatBuffer vertBuf = initFloatBuffer(vertexPositions);
        vertBuf.put(vertexPositions);
        vertBuf.position(0);
        System.out.println("vert buf limit : " + vertBuf.limit());
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,vertBuf.limit()*4, vertBuf, GLES30.GL_STATIC_DRAW);
    }

    private void createSprites() {
 //       System.out.println("Screen w : " + screenWidth + " || Screen h : " + screenHeight);
 //       testBack = new Sprite(screenWidth, screenHeight, screenWidth, screenHeight, textureID, 1024, 0, false, false, new Vector4f(0,224,400,800));
    }

    private FloatBuffer initFloatBuffer(float[] buffer)
    {
        //4= tipe float butuh 4 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer aFloatBuffer = bb.asFloatBuffer();
        aFloatBuffer.put(buffer);
        aFloatBuffer.position(0);
        return aFloatBuffer;
    }

    private IntBuffer initIntBuffer(int[] buffer)
    {
        //2= tipe short butuh 2 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer aIntBuffer = bb.asIntBuffer();
        aIntBuffer.put(buffer);
        aIntBuffer.position(0);
        return aIntBuffer;
    }

    private ShortBuffer initShortBuffer(short[] buffer)
    {
        //2= tipe short butuh 2 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 2);
        bb.order(ByteOrder.nativeOrder());
        ShortBuffer aShortBuffer = bb.asShortBuffer();
        aShortBuffer.put(buffer);
        aShortBuffer.position(0);
        return aShortBuffer;
    }



}
