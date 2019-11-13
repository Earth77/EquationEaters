package com.goblinstudios.equationeaters.OpenGLRendering;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Greg Parton 2019
 **/

public class ResourceHelper {
    public static String readRawTextFile(Context ctx, int resId)
    {
        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null)
            {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e)
        {
            return null;
        }
        return text.toString();
    }

    public static String readVertShader(Context ctx) {

        InputStream inputStream;

        try {
            inputStream = ctx.getAssets().open("vertex_shader.txt");
        } catch (Exception e) {
            System.out.println("Couldn't load vert shader");
            return "";
        }

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null)
            {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e)
        {
            return null;
        }
        return text.toString();
    }

    public static String readFragShader(Context ctx) {

        InputStream inputStream;

        try {
            inputStream = ctx.getAssets().open("frag_shader.txt");
        } catch (Exception e) {
            System.out.println("Couldn't load frag shader");
            return "";
        }

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null)
            {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e)
        {
            return null;
        }
        return text.toString();
    }

}