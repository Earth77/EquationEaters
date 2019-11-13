package com.goblinstudios.equationeaters.OpenGLRendering;

/**
 * @author Greg Parton 2019
 **/

import android.opengl.GLES30;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.Math.Vector4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;


public class DynamicSprite extends Sprite {

    //frame time in frames for each animation
    int frameTime = 6;

    double xVelocity;
    double yVelocity;

    boolean isAnimated;
    public boolean isComplete = false;
    public boolean isRepeating = true;
    
    public DynamicSprite() {
        super();
    }
    
    public DynamicSprite(int textureIn, Vector2i sizeIn, Vector4f texCoordIn, int textureWidthIn, boolean renderableIn, ArrayList<Vector4f> frameListIn, int initialFramePosition) {
        super(textureIn, sizeIn, texCoordIn, textureWidthIn, renderableIn);
        frameList = frameListIn;
        framePosition = initialFramePosition;
        calculateTex(initialFramePosition);
        isAnimated = true;
        frameMin = 0;
        frameMax = frameListIn.size() - 1;
        canvaswidth = GameAssets.deviceWidth;
        canvasheight = GameAssets.deviceHeight;
//        System.out.println("frame list size : " + frameMax);
    }

    public DynamicSprite(int textureIn, Vector2i sizeIn, Vector4f texCoordIn, int textureWidthIn, boolean renderableIn, ArrayList<Vector4f> frameListIn, int initialFramePosition, boolean wideTex) {
        super(textureIn, sizeIn, texCoordIn, textureWidthIn, renderableIn);
        frameList = frameListIn;
        framePosition = initialFramePosition;
        if (!wideTex) {
            calculateTex(initialFramePosition);
        } else {
            System.out.println("dynamic sprite calc tex wide");
            calculateTexWide(0);
        }
        isAnimated = true;
        frameMin = 0;
        frameMax = frameListIn.size() - 1;
        canvaswidth = GameAssets.deviceWidth;
        canvasheight = GameAssets.deviceHeight;
    }

    

@Override public void draw(int renderingProgram,double frameNumber, Vector2f position) {

        if (isAnimated) {
        calculateFrame(frameNumber);
        }
        
        
        float[] texCoords = coordArray.get(framePosition).getCoords();
        System.out.println("begin draw dynamic");
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
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 100);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.02f);
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

public void drawWithFullOpac(int renderingProgram,double frameNumber, Vector2f position) {

        if (isAnimated) {
        calculateFrame(frameNumber);
        }
        
        
        float[] texCoords = coordArray.get(framePosition).getCoords();
        
        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        
        //vert coords, vert coords are always in vbo[0]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(0);
        
        //tex coords, tex coords are always in vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);
        
        //bind our texture
        GLES30.glActiveTexture(textureID);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);
        
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 1);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
        GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);
        
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

@Override public void draw(int renderingProgram,double frameNumber, Vector2f position, int width, int height) {

        if (isAnimated) {
        calculateFrame(frameNumber);
        }
        
        
        float[] texCoords = coordArray.get(framePosition).getCoords();
        
        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoords);
        coordBuf.put(texCoords);
        coordBuf.position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, coordBuf.limit()*4, coordBuf, GLES30.GL_STATIC_DRAW);
        
        //vert coords, vert coords are always in vbo[0]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(0);
        
        //tex coords, tex coords are always in vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, 0);
        GLES30.glEnableVertexAttribArray(1);
        
        //bind our texture
        GLES30.glActiveTexture(textureID);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);
        
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LEQUAL);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 100);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "rotation"), rotation);
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

    public void drawNarrow(int renderingProgram,double frameNumber, Vector2f position, int width, int height, int vboIn[]) {

        if (isAnimated) {
            calculateFrame(frameNumber);
        }


        float[] texCoords = coordArray.get(framePosition).getCoords();
        System.out.println("begin draw dynamic");
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

    @Override public void drawFront(int renderingProgram, double frameNumber, Vector2f position, float zIn) {


        if (isAnimated) {
            calculateFrame(frameNumber);
        }


        float[] texCoordsNew = coordArray.get(framePosition).getCoords();
//        System.out.println("coord array size : " + coordArray.size() + "Textire id : " + textureID + " -- canvas w : " + canvaswidth + " --canvas h : " + canvasheight + " zin : " + zIn + "reflected y " + reflectedY);
        //tex coords, tex coords always go into vbo[1]
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 1);
        FloatBuffer coordBuf = initFloatBuffer(texCoordsNew);
        coordBuf.put(texCoordsNew);
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


        void calculateFrame(double frameNumberIn) {
        if (frameNumberIn % frameTime == 0 && frameNumberIn != 0) {
            framePosition++;
            if (framePosition > frameMax) {
                if (isRepeating) {
                framePosition = frameMin;
                isComplete = true;
                } else {
                framePosition = frameMax - 1;
                isComplete = true;                
                }
            }
        }
    }
        
        void calculateFrame(double frameNumberIn, boolean offset) {
        if (frameNumberIn % frameTime == 0 && frameNumberIn != 0) {
            framePosition++;
            framePosition++;
            if (framePosition > frameMax) {
                if (isRepeating) {
                framePosition = frameMin;
                isComplete = true;
                } else {
                framePosition = frameMax - 1;
                isComplete = true;
                }
            }
        }
    }
        
        void setCanvasDimensions(int widthIn, int heightIn) {
            canvaswidth = widthIn;
            canvasheight = heightIn;
        }
        

    public void updateVelocity() {
        
    }

    public void recalcCoords() {
        calculateTexWide(0);
    }





}