package utility;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by sameer on 1/7/2018.
 */

public class TextResourceReader {
   public static String readTextFileFromResource(Context context,
                                                   String filename) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(filename);
            InputStreamReader inputStreamReader =new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + filename, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + filename, nfe);
        }
       return body.toString();

        }}
