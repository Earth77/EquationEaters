package com.goblinstudios.equationeaters.OpenGLRendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Parton 2019
 **/

public class FileReader {

    public String[] readFile(String file) throws IOException {

        List<String> stringList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new java.io.FileReader (file));
        String         line = null;
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringList.add(line + "\n");
            }

            String returnList[] = new String[stringList.size()];
            returnList = stringList.toArray(returnList);
            return returnList;
        } finally {
            reader.close();
        }
    }



}