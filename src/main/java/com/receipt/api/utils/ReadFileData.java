package com.receipt.api.utils;

import com.receipt.api.data.operations.CartDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFileData {


    public static String getFileDataAsString(String fileName) throws IOException {
        ClassLoader classLoader = CartDB.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream)
        throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                 = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
