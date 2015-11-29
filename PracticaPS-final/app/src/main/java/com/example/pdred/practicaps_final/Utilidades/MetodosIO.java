package com.example.pdred.practicaps_final.Utilidades;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Michel on 03/11/2015.
 */
public class MetodosIO {

    // DADA UNA URL Y UNA POSICION TE DEVUELVE LA LINEA QUE OCUPA ESA POSICION(EMPIEZA EN 0)
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getLine(String url, int k) throws IOException {
        // Construimos una conexion
        URL nuevaUrl = new URL(url);
        URLConnection conexion = nuevaUrl.openConnection();
        ArrayList<String> lineas = new ArrayList<>();
        int i = 0;
        // Leemos, como un String
        try (InputStream in = conexion.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            for (String line; (line = reader.readLine()) != null; ) {
                lineas.add(i,line);
                i++;}
        }catch (IOException e) {e.printStackTrace();}
        // Comprobamos que se ha incluido algo en el html
        if (lineas.isEmpty()){
            String mensajeError = "ERROR. NO HTML";
            return mensajeError;
            // Si contiene algo, lo devolvemos
        }else{return lineas.get(k);}
    }

    // DADA UNA URL, TE DEVUELVE EN UNA LISTA SUS LINEAS
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static ArrayList<String> getAllLines(String url) throws IOException {
        // Construimos una conexion
        URL nuevaUrl = new URL(url);
        URLConnection conexion = nuevaUrl.openConnection();
        ArrayList<String> lineas = new ArrayList<>();
        int i = 0;
        // Leemos, como un String
        try (InputStream in = conexion.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                for (String line; (line = reader.readLine()) != null; ) {
                    lineas.add(i,line);
                    i++;}
        }catch (IOException e) {}
        return lineas;
    }
}
