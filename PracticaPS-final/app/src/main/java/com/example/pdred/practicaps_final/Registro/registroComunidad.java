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

import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.setComunidadNueva;
import static com.example.pdred.practicaps_final.UsuarioEstatico.setCurrentComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setExisteComunidad;

public class registroComunidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comunidad);
        // Recoje el boton unirse a una comunidad existente
        Button bUnirseC = (Button) findViewById(R.id.bUnirseC);
        bUnirseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasa al BACKGROUND para comprobar que existe dicha comunidad
                String comunidad = ((EditText) findViewById(R.id.eTUnirseC)).getText().toString();
                new asyncComunidad().execute(comunidad);
            }
        });
        // Recoje el boton unirse a una comunidad nueva (Y la crear)
        Button bUnirseNC = (Button) findViewById(R.id.bUnirseNC);
        bUnirseNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // Pasa al BACKGROUND para crear una nueva comunidad
                String comunidad = ((EditText)findViewById(R.id.eTUnirseC)).getText().toString();
                new asyncNuevaComunidad().execute(comunidad);
            }
        });
    }

    // BACKGROUND que comprueba que la comunidad existe
    class asyncComunidad extends AsyncTask<String,String,String> {
        String comunidad;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(registroComunidad.this);
            pDialog.setMessage("VALIDANDO COMUNIDAD...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            comunidad=params[0];
            String respuesta = "ERROR AL UNIRSE A LA COMUNIDAD";
            String urlComunidad = setExisteComunidad(comunidad);
            try{
                // Comrprueba que la comunidad SI existe
                respuesta = getHtml(urlComunidad);
            }catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            // Si existe, pasa a la siguiente fase el registro
            if (result.equals("EXISTE")){
                Intent nuevaActividad;
                nuevaActividad = new Intent(registroComunidad.this, Registro.class);
                setCurrentComunidad(comunidad);
                setComunidadNueva(false);
                startActivity(nuevaActividad);
            }else{
                // En caso contrario, muestra el error por pantalla
                Toast toastValidarError = Toast.makeText(getApplicationContext(),"La comunidad no existe",Toast.LENGTH_LONG);
                toastValidarError.show();
            }
        }
    }

    // BACKGROUND que valida si la comunidad no existe
    class asyncNuevaComunidad extends AsyncTask<String,String,String> {
        String comunidad;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(registroComunidad.this);
            pDialog.setMessage("VALIDANDO COMUNIDAD...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            comunidad=params[0];
            String respuesta = "COMUNIDAD NO VALIDA ";
            String urlComunidad = setExisteComunidad(comunidad);
            try{
                // Comprueba que la comunida NO existe
                respuesta = getHtml(urlComunidad);
            }catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            // Si no existe, pasa a la siguiente fase del registro
            if (result.equals("ERROR")){
                Intent nuevaActividad;
                nuevaActividad = new Intent(registroComunidad.this, Registro.class);
                // Guarda esta nueva comunidad para poder crearla en la siguiente fase del registro junto con el usuario
                setCurrentComunidad(comunidad);
                setComunidadNueva(true);
                startActivity(nuevaActividad);

            }else{
                Toast toastValidar = Toast.makeText(getApplicationContext(),"La comunidad ya existe",Toast.LENGTH_SHORT);
                toastValidar.show();
            }
        }
    }

}