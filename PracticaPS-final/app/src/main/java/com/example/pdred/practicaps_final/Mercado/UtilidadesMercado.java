package com.example.pdred.practicaps_final.Mercado;

import android.annotation.TargetApi;
import android.os.Build;

import com.example.pdred.practicaps_final.Clases.Jugador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Zigic on 05/11/2015.
 */
public class UtilidadesMercado {

    public static String setMercadoComunidad(String comunidad){
        return "http://comunio.garcy.es/?funcion=mercado&comunidad=" + comunidad;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getHtml(String url) throws IOException {
        // Construimos una conexion
        URL nuevaUrl = new URL(url);
        URLConnection conexion = nuevaUrl.openConnection();
        StringBuilder html = null;
        // Leemos, como un String
        try (
                InputStream in = conexion.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);

            }
        }catch (IOException e) {e.printStackTrace();}
        // Comprobamos que se ha incluido algo en el html
        if (html == null){
            String mensajeError = null;
            return mensajeError;
            // Si contiene algo, lo devolvemos
        }else{return html.toString();}
    }


}
