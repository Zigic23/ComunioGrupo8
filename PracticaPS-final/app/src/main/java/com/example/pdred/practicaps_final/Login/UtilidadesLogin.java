package com.example.pdred.practicaps_final.Login;

/**
 * Created by Michel on 27/10/2015.
 */
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

public class UtilidadesLogin {

    public static String setUsuarioContrasena(String usuario, String password) {
        return "http://comunio.garcy.es/?funcion=login&usuario=" + usuario + "&contrase%C3%B1a=" + password;
    }

    public static String setInfoUsuario(String user){
        return "http://comunio.garcy.es/?funcion=info&usuario=" + user;
    }

    public static String setFuncionEquipo(String user){
        return "http://comunio.garcy.es/?funcion=equipo&usuario=" + user;
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
                html.append(line);}
        }catch (IOException e) {e.printStackTrace();}
        // Comprobamos que se ha incluido algo en el html
        if (html == null){
            String mensajeError = "ERROR. NO HTML";
            return mensajeError;
            // Si contiene algo, lo devolvemos
        }else{return html.toString();}
    }


    /*public static String getInfo(String mensaje, int i){
        String info = "";
        int j = 0;
        while((i >= 0) && (j < mensaje.length())){
            char c = mensaje.charAt(j);
            if (c == '\n'){
                i --;
            }
            if (i == 0){
                for (int k = j; k < mensaje.length();k++){
                    char a = mensaje.charAt(k);
                    if (a != '\n'){info = info + a;}else{return info;}
                }
                break;
            }
            j++;
        }
        return info;
    }*/

    public static boolean checklogindata(String username, String password){
        if (username.equals("")|| password.equals("")){return false;}else {return false;}
    }


}