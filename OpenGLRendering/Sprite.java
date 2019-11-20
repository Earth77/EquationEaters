package com.goblinstudios.equationeaters.OpenGLRendering;

import android.opengl.GLES30;

import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.Math.Vector4f;
import com.goblinstudios.equationeaters.Math.Vector2f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * @author Greg Parton 2019
 **/

public class Sprite {

    float canvaswidth;
    float canvasheight;

    Vector2i size = new Vector2i(0,0);
    int textureID;
    int textureWidth;
    int textureHeight;
    int framePosition;
    int frameMin;
    int frameMax;

    public int rotation = 0;

    boolean reflectedX = false;
    boolean reflectedY = false;
    boolean renderable = true;

    float[] texCoords;

    ArrayList<Vector4f> frameList = new ArrayList();
    ArrayList<TexCoords> coordArray = new ArrayList();

    TexCoords spriteCoords;

    Vector4f texCoordsToRender;

    boolean wideTex = false;

    public Sprite() {

    }

    public Sprite(int textureIn, Vector2i sizeIn, Vector4f texCoordIn, int textureWidthIn, boolean renderableIn) {
        textureID = textureIn;
        size.x = sizeIn.x;
        size.y = sizeIn.y;
        texCoordsToRender = texCoordIn;
        textureWidth = textureWidthIn;
        textureHeight = textureWidthIn;
        renderable = renderableIn;
        calculateTex();
        texCoords = spriteCoords.getCoords();
    }

    public Sprite(float canvW, float canvH, int sizeX, int sizeY, int texID, int textureWidthIn, int rot, boolean refX, boolean refY, Vector4f texCoordIn) {

        canvaswidth = canvW;
        canvasheight = canvH;
        size.x = sizeX;
        size.y = sizeY;
        textureID = texID;
        textureWidth = textureWidthIn;
        textureHeight = textureWidthIn;
        rotation = rot;
        reflectedX = refX;
        reflectedY = refY;
        texCoordsToRender = texCoordIn;
        calculateTex();
        texCoords = spriteCoords.getCoords();


    }

    public Sprite(float canvW, float canvH, int sizeX, int sizeY, int texID, int textureWidthIn, int textureHeightIn, int rot, boolean refX, boolean refY, Vector4f texCoordIn) {

        canvaswidth = canvW;
        canvasheight = canvH;
        size.x = sizeX;
        size.y = sizeY;
        textureID = texID;
        textureWidth = textureWidthIn;
        textureHeight = textureHeightIn;
        rotation = rot;
        reflectedX = refX;
        reflectedY = refY;
        texCoordsToRender = texCoordIn;
        if (textureWidth == textureHeight) {
            calculateTex();
        } else if (textureHeight > textureWidth){
            wideTex = true;
            calculateTexTall();
        }
        texCoords = spriteCoords.getCoords();


    }

    public Sprite(int textureIn, Vector2f sizeIn, Vector4f texCoordIn, int textureWidthIn, boolean renderableIn, int canvWidth, int canvHeight) {
        textureID = textureIn;
        size = new Vector2i((int)sizeIn.x,(int)sizeIn.y);
        texCoordsToRender = new Vector4f(texCoordIn.x,texCoordIn.y, texCoordIn.z, texCoordIn.w);
        textureWidth = textureWidthIn;
        renderable = renderableIn;
        calculateTex();
        canvaswidth = canvWidth;
        canvasheight = canvHeight;
        rotation = 0;
        texCoords = spriteCoords.getCoords();
    }

    public void draw(int renderingProgram, double frameNumber, Vector2f position) {
        /*
        System.out.println("begin draw static");
        System.out.println(texCoords[0]);
        System.out.println(texCoords[1]);
        System.out.println(texCoords[2]);
        System.out.println(texCoords[3]);
        System.out.println(texCoords[4]);
        System.out.println(texCoords[5]);
        System.out.println(texCoords[6]);
        System.out.println(texCoords[7]);
        System.out.println(texCoords[8]);
        System.out.println(texCoords[9]);
        System.out.println(texCoords[10]);
        System.out.println(texCoords[11]);
        System.out.println("end draw");
        */

        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        //bind our texture
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint") , 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), 0);


        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    public void drawColor(int renderingProgram, double frameNumber, Vector2f position, int colorTint) {
        /*
        System.out.println("begin draw static");
        System.out.println(texCoords[0]);
        System.out.println(texCoords[1]);
        System.out.println(texCoords[2]);
        System.out.println(texCoords[3]);
        System.out.println(texCoords[4]);
        System.out.println(texCoords[5]);
        System.out.println(texCoords[6]);
        System.out.println(texCoords[7]);
        System.out.println(texCoords[8]);
        System.out.println(texCoords[9]);
        System.out.println(texCoords[10]);
        System.out.println(texCoords[11]);
        System.out.println("end draw");
        */

        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        //bind our texture
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint") , 100);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), 0);


        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    public void drawFront(int renderingProgram, double frameNumber, Vector2f position, float zIn) {


        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        //bind our texture
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint") , 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), zIn);


        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    public void drawNarrow(int renderingProgram,double frameNumber, Vector2f position, int width, int height) {

        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), (canvasheight));
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.01f);

        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), width);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -width);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), height);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -height);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    public void drawFrontWithOpac(int renderingProgram, double frameNumber, Vector2f position, float zIn, int opac) {


        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        //bind our texture
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), opac);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint") , 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), zIn);


        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    public void draw(int renderingProgram, double frameNumber, Vector2f position, int width, int height) {


        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);

        //bind our texture
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint") , 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);


        if (!reflectedX) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), width);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -width);
        }

        if (!reflectedY) {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), height);
        } else {
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -height);
        }

        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), position.x);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);
    }

    void calculateTex() {

//        System.out.println("tex width : " + textureWidth + " : coordY " + texCoordsToRender.y + " : coordW " + texCoordsToRender.w);

        texCoordsToRender.y = textureWidth - texCoordsToRender.y - texCoordsToRender.w;

        float pixelCoord = 1;
        pixelCoord = pixelCoord / textureWidth;

        //bottom left x and y
        float bl = texCoordsToRender.x * pixelCoord;
        float bl2 = texCoordsToRender.y * pixelCoord;

        //bottom right x and y
        float br = bl + texCoordsToRender.z * pixelCoord;
        float br2 = bl2;

        //top right x and y
        float tr = br;
        float tr2 = bl2 + texCoordsToRender.w * pixelCoord;

        //top left x and y
        float tl = bl;
        float tl2 = tr2;
//        System.out.println("Calc tex bl : " + bl + ":" + bl2 + ":" + br + ":" + br2 + ":" + tr + ":" + tr2 + ":" + tl + ":" + tl2);
//          spriteCoords = new TexCoords(bl,bl2,br,br2,tr,tr2,tl,tl2);
        spriteCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
//        spriteCoords = new TexCoords(0,0,1,0,1,-1,0,-1);
    }

    void calculateTex(int framePosition) {

        for (int i = 0; i < frameList.size(); i++) {
            float pixelCoord = 1;
            pixelCoord = pixelCoord / textureWidth;

            float newFrameY = frameList.get(i).y;
            newFrameY = textureWidth - frameList.get(i).y  - frameList.get(i).w;

            //bottom left x and y
            float bl = frameList.get(i).x * pixelCoord;
            float bl2 = newFrameY * pixelCoord;

            //bottom right x and y
            float br = bl + frameList.get(i).z * pixelCoord;
            float br2 = bl2;

            //top right x and y
            float tr = br;
            float tr2 = bl2 + frameList.get(i).w * pixelCoord;

            //top left x and y
            float tl = bl;
            float tl2 = tr2;

            bl = round(bl, 3);
            bl2 = round(bl2, 3);
            br = round(br, 3);
            br2 = round(br2, 3);
            tr = round(tr, 3);
            tr2 = round(tr2, 3);
            tl = round(tl, 3);
            tl2 = round(tl2, 3);

//            TexCoords newCoords = new TexCoords(bl, bl2, br, br2, tr, tr2, tl, tl2);
            TexCoords newCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
            coordArray.add(newCoords);
        }

    }

    public float round (float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }

    public FloatBuffer initFloatBuffer(float[] buffer)
    {
        //4= tipe float butuh 4 byte
        ByteBuffer bb = ByteBuffer.allocateDirect(buffer.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer aFloatBuffer = bb.asFloatBuffer();
        aFloatBuffer.put(buffer);
        aFloatBuffer.position(0);
        return aFloatBuffer;
    }

    void calculateTexWide(int framePosition) {
        System.out.println("Calc tex wide");
        for (int i = 0; i < frameList.size(); i++) {
            System.out.println("testing : " + i);
            float pixelCoord = 2;
            pixelCoord = pixelCoord / textureWidth;

            float newFrameY = frameList.get(i).y;
            newFrameY = (textureWidth / 2) - frameList.get(i).y - frameList.get(i).w;

            //bottom left x and y
            float bl = (float)(frameList.get(i).x * pixelCoord / 2);
            float bl2 = newFrameY * pixelCoord;

            //bottom right x and y
            float br = (float)(bl + frameList.get(i).z * pixelCoord / 2);
            float br2 = bl2;

            //top right x and y n
            float tr = br;
            float tr2 = (float)(bl2 + frameList.get(i).w * pixelCoord);

            //top left x and y
            float tl = bl;
            float tl2 = tr2;

            bl = round(bl, 3);
            bl2 = round(bl2, 3);
            br = round(br, 3);
            br2 = round(br2, 3);
            tr = round(tr, 3);
            tr2 = round(tr2, 3);
            tl = round(tl, 3);
            tl2 = round(tl2, 3);

//            TexCoords newCoords = new TexCoords(bl,bl2,br,br2,tr,tr2,tl,tl2);
            TexCoords newCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
            System.out.println("sprite coords created ***");
            spriteCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
            coordArray.add(newCoords);
        }
    }

    void calculateTexTall() {

//        System.out.println("tex width : " + textureWidth + " : coordY " + texCoordsToRender.y + " : coordW " + texCoordsToRender.w);

        texCoordsToRender.y = textureHeight - texCoordsToRender.y - texCoordsToRender.w;

        float pixelCoord = 1;
        pixelCoord = pixelCoord / textureWidth;

        //bottom left x and y
        float bl = texCoordsToRender.x * pixelCoord;
        float bl2 = texCoordsToRender.y * pixelCoord / 2;

        //bottom right x and y
        float br = bl + texCoordsToRender.z * pixelCoord;
        float br2 = bl2;

        //top right x and y
        float tr = br;
        float tr2 = bl2 + texCoordsToRender.w * pixelCoord / 2;

        //top left x and y
        float tl = bl;
        float tl2 = tr2;
//        System.out.println("Calc tex bl : " + bl + ":" + bl2 + ":" + br + ":" + br2 + ":" + tr + ":" + tr2 + ":" + tl + ":" + tl2);
//          spriteCoords = new TexCoords(bl,bl2,br,br2,tr,tr2,tl,tl2);
        spriteCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
//        spriteCoords = new TexCoords(0,0,1,0,1,-1,0,-1);
        }

}
