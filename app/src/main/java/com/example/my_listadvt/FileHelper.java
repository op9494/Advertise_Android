package com.example.my_listadvt;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {

    public static Uri saveFile(InputStream inputStream, String filename) {
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            // create a temporary file
            File outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File outputFile = File.createTempFile(filename, ".mp4", outputDir);
            outputStream = new FileOutputStream(outputFile);
            bufferedInputStream = new BufferedInputStream(inputStream);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            // return the URI of the saved file
            return Uri.fromFile(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            // close the streams
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
