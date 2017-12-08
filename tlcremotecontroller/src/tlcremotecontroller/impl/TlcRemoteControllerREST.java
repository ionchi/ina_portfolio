package tlcremotecontroller.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class TlcRemoteControllerREST {
    private String id;

    public TlcRemoteControllerREST(String id) {
                this.id = id;
    }

    public void changeSemaphoreStatus(String semaphoreUrl, String action) {
        try {

            URL url = new URL(semaphoreUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            String input = "{action:"+action+"}";

            Random random = new Random();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(String.format(input, random.nextInt(30), random.nextInt(20)));
            osw.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
