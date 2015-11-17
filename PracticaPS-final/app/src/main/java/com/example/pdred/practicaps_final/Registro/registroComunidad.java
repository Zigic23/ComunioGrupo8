package com.example.pdred.practicaps_final.Registro;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pdred.practicaps_final.R;
import com.example.pdred.practicaps_final.UsuarioEstatico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static com.example.pdred.practicaps_final.UsuarioEstatico.setComunidadNueva;
import static com.example.pdred.practicaps_final.UsuarioEstatico.setCurrentComunidad;

public class RegistroComunidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comunidad);
        Button bUnirseC = (Button) findViewById(R.id.bUnirseC);
        bUnirseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comunidad = ((EditText) findViewById(R.id.eTUnirseC)).getText().toString();
                new asyncComunidad().execute(comunidad);
            }
        });
        Button bUnirseNC = (Button) findViewById(R.id.bUnirseNC);
        bUnirseNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String comunidad = ((EditText)findViewById(R.id.eTUnirseC)).getText().toString();
                new asyncNuevaComunidad().execute(comunidad);
            }
        });
    }
    class asyncComunidad extends AsyncTask<String,String,String> {
        String comunidad;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(RegistroComunidad.this);
            pDialog.setMessage("VALIDANDO COMUNIDAD...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            comunidad=params[0];
            String respuesta = "ERROR AL UNIRSE A LA COMUNIDAD";
            String urlComunidad = "http://comunio.garcy.es/?funcion=validarComunidad&comunidad="+comunidad;
            try{
                // Obtenemos el HTML del registro -> Introduce nuevos datos en la base de datos
                respuesta = getHtml(urlComunidad);
                // Creamos una sesion, creando un usuario estatico a partir del nombre de usuario y cargando su informacion
            }catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            if (result.equals("EXISTE")){
                Intent nuevaActividad;
                nuevaActividad = new Intent(RegistroComunidad.this, Registro.class);
                setCurrentComunidad(comunidad);
                setComunidadNueva(false);
                startActivity(nuevaActividad);
                //Toast toastValidar = Toast.makeText(getApplicationContext(),"ERROR AL UNIRSE A LA COMUNIDAD",Toast.LENGTH_SHORT);
                //toastValidar.show();
            }else{
                Toast toastValidarError = Toast.makeText(getApplicationContext(),"COMUNIDAD NO VALIDA",Toast.LENGTH_LONG);
                toastValidarError.show();
                // Intent nuevaActividad;
                //nuevaActividad = new Intent(RegistroComunidad.this, Registro.class);
                //startActivity(nuevaActividad);
            }
        }
    }
    class asyncNuevaComunidad extends AsyncTask<String,String,String> {
        String comunidad;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(RegistroComunidad.this);
            pDialog.setMessage("VALIDANDO COMUNIDAD...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            comunidad=params[0];
            String respuesta = "COMUNIDAD NO VALIDA ";
            String urlComunidad = "http://comunio.garcy.es/?funcion=validarComunidad&comunidad="+comunidad;
            try{
                // Obtenemos el HTML del registro -> Introduce nuevos datos en la base de datos
                respuesta = getHtml(urlComunidad);
                // Creamos una sesion, creando un usuario estatico a partir del nombre de usuario y cargando su informacion
            }catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            if (result.equals("ERROR")){

                Intent nuevaActividad;
                nuevaActividad = new Intent(RegistroComunidad.this, Registro.class);
                UsuarioEstatico a = new UsuarioEstatico();
                setCurrentComunidad(comunidad);
                setComunidadNueva(true);
                startActivity(nuevaActividad);

            }else{

                Toast toastValidar = Toast.makeText(getApplicationContext(),"COMUNIDAD NO VALIDA",Toast.LENGTH_SHORT);
                toastValidar.show();
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getHtml(String url) throws IOException {
        // Construimos una conexion
        URL nuevaUrl = new URL(url);
        URLConnection conexion = nuevaUrl.openConnection();

        StringBuilder html = null;
        // Leemos, como un String, la primera linea del HTML
        try (InputStream in = conexion.getInputStream()) {
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