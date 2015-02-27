package main.java.com.lordmau5.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lordmau5 on 26.02.2015.
 */
public class GenericUtil {

    public static List<File> getFiles(String directoryName, String ext) {
        File directory = new File(directoryName);
        if(!directory.exists())
            directory.mkdirs();

        // get all the files from a directory
        List<File> files = new ArrayList<>();
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile() && file.getName().endsWith("." + ext)) {
                files.add(file);
            }
        }
        return files;
    }

    public static String encrypt(String string) {
        try {
            return Base64.encode(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String string) {
        try {
            return new String(Base64.decode(string), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
