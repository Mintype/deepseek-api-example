package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        String model = "deepseek-r1:1.5b";
        String prompt = "Why is the sky blue?";
        String fullResponse = "";

        try {
            // Set up an HTTP POST request
            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create request JSON
            JSONObject requestJson = new JSONObject();
            requestJson.put("model", model);
            requestJson.put("prompt", prompt);
            requestJson.put("stream", false);

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(requestJson.toString().getBytes());
            }

            // Get response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String responseLine = br.readLine();
                JSONObject responseJson = new JSONObject(responseLine);
                fullResponse = responseJson.getString("response");
            }

            System.out.println(fullResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}