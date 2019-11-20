package com.goblinstudios.equationeaters.OpenGLRendering;

import android.opengl.GLES30;

import com.goblinstudios.equationeaters.GameAssets;
import com.goblinstudios.equationeaters.Math.Vector2f;
import com.goblinstudios.equationeaters.Math.Vector2i;
import com.goblinstudios.equationeaters.Math.Vector4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Font extends Sprite {

    ArrayList<TexCoords> alphabetArray = new ArrayList();
    ArrayList<TexCoords> numberArray = new ArrayList();
    float characterSpacing;
    float characterWidth;
    float characterHeight;
    float renderHeight;
    float renderWidth;
    Vector2f position;
    //track type of sprite
    public enum SpriteType {STATIC, DYNAMIC, CLICKABLE, FONT}
    SpriteType type;
    Vector4f fontTexCoords = new Vector4f(0,0,128,128);

    public Font(int textureIn, Vector2f positionIn, Vector4f texCoordIn, int textureWidthIn, boolean renderableIn, float spacingIn, int widthIn, int heightIn, float renderWidthIn, float renderHeightIn) {
        super(textureIn, new Vector2i(widthIn, heightIn), texCoordIn, textureWidthIn, renderableIn);
        type = SpriteType.FONT;
        characterWidth = widthIn;
        characterHeight = heightIn;
        characterSpacing = spacingIn;
        renderHeight = renderHeightIn;
        renderWidth = renderWidthIn;
        size.x = (int)renderWidth;
        size.y = (int)renderHeight;
        fillFontArrays();
        fontTexCoords = texCoordIn;
        canvaswidth = GameAssets.deviceWidth;
        canvasheight = GameAssets.deviceHeight;
        textureWidth = textureWidthIn;
        textureHeight = textureWidthIn;
    }

    public void draw(int renderingProgram, String stringToRender, Vector2f positionIn) {

        position = positionIn;

        centerPositionX(stringToRender);

        char[] characterArray = stringToRender.toCharArray();

        float tempPosition = position.x + characterSpacing + size.x / 2;

        for (int i = 0; i < characterArray.length; i++) {

            char charToRender = Character.toUpperCase(characterArray[i]);

            int toAsciiAndBack;
            float[] texCoords;

            if (Character.isLetter(charToRender)) {
                toAsciiAndBack = (int)charToRender - 65;
                texCoords = alphabetArray.get(toAsciiAndBack).getCoords();
            } else if (Character.isWhitespace(charToRender)) {
                texCoords = alphabetArray.get(26).getCoords();
            } else {
                toAsciiAndBack = (int)charToRender - 48;
                //       System.out.println(toAsciiAndBack);
                texCoords = numberArray.get(toAsciiAndBack).getCoords();
            }

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

            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 101);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint"), 0);
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

            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), tempPosition);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.02f);

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);

            tempPosition += size.x + characterSpacing;
        }

    }

    public void drawHalfSize(int renderingProgram, String stringToRender, Vector2f positionIn) {

        position = positionIn;

        centerPositionX(stringToRender);

        char[] characterArray = stringToRender.toCharArray();

        float tempPosition = position.x + characterSpacing + (characterArray.length * characterWidth / 3.35f);

        for (int i = 0; i < characterArray.length; i++) {

            char charToRender = Character.toUpperCase(characterArray[i]);

            int toAsciiAndBack;
            float[] texCoords;

            if (Character.isLetter(charToRender)) {
                toAsciiAndBack = (int)charToRender - 65;
                texCoords = alphabetArray.get(toAsciiAndBack).getCoords();
            } else if (Character.isWhitespace(charToRender)) {
                texCoords = alphabetArray.get(26).getCoords();
            } else {
                toAsciiAndBack = (int)charToRender - 48;
                //       System.out.println(toAsciiAndBack);
                texCoords = numberArray.get(toAsciiAndBack).getCoords();
            }

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

            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 101);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint"), 0);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);

            if (!reflectedX) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x / 2);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x / 2);
            }

            if (!reflectedY) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y / 2);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y / 2);
            }

            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), tempPosition);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.02f);

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);

            tempPosition += ((characterWidth + characterSpacing) / 2);
        }

    }


    public void drawDouble(int renderingProgram, String stringToRender, Vector2f positionIn) {

        position = positionIn;

        centerPositionX(stringToRender);

        char[] characterArray = stringToRender.toCharArray();

        float tempPosition = position.x + characterSpacing + ((characterWidth / 2) / (characterArray.length * 1.2f));

        for (int i = 0; i < characterArray.length; i++) {

            char charToRender = Character.toUpperCase(characterArray[i]);

            int toAsciiAndBack;
            float[] texCoords;

            if (Character.isLetter(charToRender)) {
                toAsciiAndBack = (int)charToRender - 65;
                texCoords = alphabetArray.get(toAsciiAndBack).getCoords();
            } else if (Character.isWhitespace(charToRender)) {
                texCoords = alphabetArray.get(26).getCoords();
            } else {
                toAsciiAndBack = (int)charToRender - 48;
                //       System.out.println(toAsciiAndBack);
                texCoords = numberArray.get(toAsciiAndBack).getCoords();
            }

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

            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 101);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint"), 0);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);

            if (!reflectedX) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x * 2);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x * 2);
            }

            if (!reflectedY) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y * 2);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y * 2);
            }

            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), tempPosition);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.02f);

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);

            tempPosition += ((characterWidth + characterSpacing) * 2);
        }

    }

    public void drawOnePointFive(int renderingProgram, String stringToRender, Vector2f positionIn) {

        position = positionIn;

        centerPositionX(stringToRender);

        char[] characterArray = stringToRender.toCharArray();

        float tempPosition = position.x;

        for (int i = 0; i < characterArray.length; i++) {

            char charToRender = Character.toUpperCase(characterArray[i]);

            int toAsciiAndBack;
            float[] texCoords;

            if (Character.isLetter(charToRender)) {
                toAsciiAndBack = (int)charToRender - 65;
                texCoords = alphabetArray.get(toAsciiAndBack).getCoords();
            } else if (Character.isWhitespace(charToRender)) {
                texCoords = alphabetArray.get(26).getCoords();
            } else {
                toAsciiAndBack = (int)charToRender - 48;
                //       System.out.println(toAsciiAndBack);
                texCoords = numberArray.get(toAsciiAndBack).getCoords();
            }

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

            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "opacity"), 101);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "textureToUse"), textureID);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "clrReverse"), 0);
            GLES30.glUniform1i(GLES30.glGetUniformLocation(renderingProgram, "colorTint"), 0);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvaswidth"), canvaswidth);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "canvasheight"), canvasheight);

            if (!reflectedX) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), size.x * 1.5f);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "width"), -size.x * 1.5f);
            }

            if (!reflectedY) {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), size.y * 1.5f);
            } else {
                GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "height"), -size.y * 1.5f);
            }

            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positionx"), tempPosition);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "positiony"), position.y);
            GLES30.glUniform1f(GLES30.glGetUniformLocation(renderingProgram, "zdepth"), -0.02f);

            GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,6);

            tempPosition += ((characterWidth + (characterSpacing * 1.5f)) * 1.5f);
        }

    }


    public void fillFontArrays() {

        int alphabetCounter = 0;

        for (int h = 1; h < 5; h++) {
            for (int i = 0; i < 8; i++) {

                float pixelCoord = 1;
                pixelCoord = pixelCoord / textureWidth;

                float newFrameY = textureWidth + characterHeight * h  - characterHeight;

                //bottom left x and y
                float bl = (0 + characterWidth * i) * pixelCoord;
                //float bl2 = newFrameY - ((textureWidth * pixelCoord) - (characterHeight * h) * pixelCoord);
                float bl2 = (newFrameY * pixelCoord);

                //bottom right x and y
                float br = bl + characterWidth * pixelCoord;
                float br2 = bl2;

                //top right x and y
                float tr = br;
                float tr2 = bl2 + characterHeight * pixelCoord;

                //top left x and y
                float tl = bl;
                float tl2 = tr2;

                //bl = bl + 0.01f;
                //bl2 = bl2 + 0.01f;
                //br = br - 0.02f;
                //br2 = br2 + 0.01f;
                //tr = tr - 0.02f;
                //tr2 = tr2 - 0.02f;
                //tl = tl + 0.01f;
                //tl2 = tl2 - 0.02f;

                TexCoords alphabetCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
                alphabetArray.add(alphabetCoords);

                alphabetCounter++;
                if (alphabetCounter == 28) {
                    break;
                }

            }
        }

        int numberCounter = 0;

        for (int j = 1; j < 3; j++) {
            for (int l = 0; l < 8; l++) {

                float pixelCoord = 1;
                pixelCoord = pixelCoord / textureWidth;

                float newFrameY = fontTexCoords.y;
                newFrameY = textureWidth - (j * pixelCoord);

                //bottom left x and y
                float bl = (0 + characterWidth * l) * pixelCoord;
                System.out.println("Font texture height : " + textureHeight);
                float bl2 = (((textureHeight / 2) + characterHeight * j - characterHeight) * pixelCoord);

                //bottom right x and y
                float br = bl + characterWidth * pixelCoord;
                float br2 = bl2;

                //top right x and y
                float tr = br;
                float tr2 = bl2 + characterHeight * pixelCoord;

                //top left x and y
                float tl = bl;
                float tl2 = tr2;

                //bl = bl + 0.01f;
                //bl2 = bl2 + 0.01f;
                //br = br - 0.02f;
                //br2 = br2 + 0.01f;
                //tr = tr - 0.02f;
                //tr2 = tr2 - 0.02f;
                //tl = tl + 0.01f;
                //tl2 = tl2 - 0.02f;

                TexCoords numberCoords = new TexCoords(tl,tl2,tr,tr2,br,br2,bl,bl2);
//        System.out.println("Building num array : " + numberCounter);
                numberArray.add(numberCoords);

                numberCounter++;
                if (numberCounter == 16) {
                    break;
                }


            }
        }

    }

    void centerPositionX(String stringToRender) {
        position.x += ((- stringToRender.length()) * (renderWidth / 2 + characterSpacing / 2));
    }

}