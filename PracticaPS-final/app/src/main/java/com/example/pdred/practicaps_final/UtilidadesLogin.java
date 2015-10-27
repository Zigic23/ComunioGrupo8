package com.example.pdred.practicaps_final;

/**
 * Created by Michel on 22/10/2015.
 */
import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UtilidadesLogin {

    public static String setUsuarioContraseña(String usuario, String password){
        return "http://comunio.garcy.es/?funcion=login&usuario="+usuario+"&contrase%C3%B1a="+password;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getHtml(String url) throws IOException {
        // Construimos una conexion
        URL nuevaUrl = new URL(url);
        URLConnection conexion = nuevaUrl.openConnection();

        StringBuilder html = null;
        // Leemos, como un String, la primera linea del HTML
        try (
                InputStream in = conexion.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);}
        }catch (IOException e) {}
        // Comprobamos que se ha incluido algo en el html
        if (html == null){
            String mensajeError = "ERROR. NO HTML";
            return mensajeError;
            // Si contiene algo, lo devolvemos
        }else{return html.toString();}
    }
}
