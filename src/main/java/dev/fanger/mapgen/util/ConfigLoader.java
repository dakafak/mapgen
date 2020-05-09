package dev.fanger.mapgen.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigLoader {

    public static JSONObject jsonFromFile(String filePath) throws IOException {
        StringBuilder jsonStringBuilder = new StringBuilder();

        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {
            jsonStringBuilder.append(readLine);
        }
        bufferedReader.close();

        return jsonFromString(jsonStringBuilder.toString());
    }

    public static JSONObject jsonFromResource(String resourcePath) throws IOException {
        StringBuilder jsonStringBuilder = new StringBuilder();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("exampleConfig.json");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {
            jsonStringBuilder.append(readLine);
        }
        bufferedReader.close();

        return jsonFromString(jsonStringBuilder.toString());
    }

    public static JSONObject jsonFromString(String jsonString) {
        return new JSONObject(jsonString);
    }

}
