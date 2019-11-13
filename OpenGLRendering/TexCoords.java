package com.goblinstudios.equationeaters.OpenGLRendering;

/**
 * @author Greg Parton 2019
 **/

public class TexCoords {
    float br;
    float br2;
    float bl;
    float bl2;
    float tr;
    float tr2;
    float tl;
    float tl2;

    public TexCoords(float blin, float bl2in, float brin, float br2in, float trin, float tr2in, float tlin, float tl2in) {
        bl = blin;
        bl2 = bl2in;
        br = brin;
        br2 = br2in;
        tr = trin;
        tr2 = tr2in;
        tl = tlin;
        tl2 = tl2in;
    }

    float[] getCoords() {
        float[] coords = {bl, bl2, br, br2, tr, tr2, tr, tr2, tl, tl2, bl, bl2};
        return coords;
    }
}