package com.example.freeforplay.Controladores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnectGames {
    private static final String URL_BASE = "https://www.freetogame.com/api";

    public static String getRequest(String endpoint){
        HttpURLConnection http = null;
        String content = null;

        try {
            //Completa la direccion en la que estan los datos
            URL url = new URL( URL_BASE + endpoint );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }

        } catch (MalformedURLException e) {
            //Excepcion por URL
            e.printStackTrace();
        } catch (IOException e) {
            //Excepción por http = (HttpConnectCartas)url.openConnection();
            e.printStackTrace();
        } finally {

            if( http != null ) http.disconnect();
        }
        return content;
    }

    public static int postRequest( String strUrl, String params ){

        HttpURLConnection http = null;
        int responseCode = -1;
        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setDoOutput(true);

            PrintWriter writer = new PrintWriter(http.getOutputStream());
            writer.print(params); //Aquí se le pasaría la variable creada query
            writer.flush();
            responseCode = http.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null) http.disconnect();
        }
        return responseCode;
    }
}
