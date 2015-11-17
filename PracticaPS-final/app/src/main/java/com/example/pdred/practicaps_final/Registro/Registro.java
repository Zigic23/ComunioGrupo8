package com.example.pdred.practicaps_final.Registro;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Login.LoginActivity;
import com.example.pdred.practicaps_final.R;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.example.pdred.practicaps_final.Clases.Equipo.generarEquipoAleatorio;
import static com.example.pdred.practicaps_final.Clases.Equipo.getIDs;
import static com.example.pdred.practicaps_final.Clases.Equipo.parsearJugadores;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentComunidad;
import static com.example.pdred.practicaps_final.UsuarioEstatico.isComunidadNueva;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setFichar;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroEquipo;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroNuevaComunidad;


public class Registro extends AppCompatActivity {

    private boolean nombreValido=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        TextView tVNombreComunidad = (TextView) findViewById(R.id.tVComunidad);
        tVNombreComunidad.setText("Comunidad: "+getCurrentComunidad());
        Button bValidar = (Button) findViewById(R.id.bValidar);
        bValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String usuario = ((EditText)findViewById(R.id.eTUsuario)).getText().toString();
                new asyncValidar().execute(usuario);
            }
        });
        Button bRegistrar = (Button) findViewById(R.id.bRegistrar);
        bRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String usuario = ((EditText)findViewById(R.id.eTUsuario)).getText().toString();
                String password1= ((EditText)findViewById(R.id.eTPass1)).getText().toString();
                String password2= ((EditText)findViewById(R.id.eTPass2)).getText().toString();
                String equipo= ((EditText)findViewById(R.id.eTNombreEquipo)).getText().toString();

                if(nombreValido){
                    if (password1.equals(password2)){
                        new asynRegistrar().execute(usuario,password1,equipo);
                    }else{
                        Toast toastRegistrar = Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT);
                        toastRegistrar.show();
                    }
                }else{
                    Toast toastValidar = Toast.makeText(getApplicationContext(),"Usuario no válido",Toast.LENGTH_SHORT);
                    toastValidar.show();
                }
            }
        });
    }
    class asynRegistrar extends AsyncTask<String,String,String>{
        String user;
        String pass;
        String equipo;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(Registro.this);
            pDialog.setMessage("Registrando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            user=params[0];
            pass=params[1];
            equipo=params[2];
            String comunidad = getCurrentComunidad();
            String respuesta = "ERROR AL REGISTRAR";

            try{
                // Obtenemos el HTML del registro -> Introduce nuevos datos en la base de datos
                if (isComunidadNueva()){ //Si la comunidad es nueva
                    String urlRegistrarNueva = setRegistroNuevaComunidad(comunidad,user,pass,equipo);
                    respuesta = getHtml(urlRegistrarNueva);
                }else{ //Si la comunidad ya existe
                    String urlRegistrar = setRegistroComunidad(comunidad,user,pass,equipo);
                    respuesta = getHtml(urlRegistrar);
                }
                // AQUI GENERAMOS EL EQUIPO ALEATORIO
                    String urlEquipo = setRegistroEquipo(comunidad);
                    // Obtenemos como un Array de Strings todos los jugadores libres
                    ArrayList<String> jugadores = getAllLines(urlEquipo);
                    // Parseamos los jugadores al tipo Jugador
                    ArrayList<Jugador> jugadoresLibres = parsearJugadores(jugadores);
                    // Generamos un equipo aleatorio
                    ArrayList<Jugador> equipoGenerado = generarEquipoAleatorio(jugadoresLibres);
                    // Obtenemos el ID de los jugadores del equipo generado
                    String fichajes = getIDs(equipoGenerado);
                    // Los fichamos a todos
                    String urlFichar = setFichar(user,fichajes);
                    getHtml(urlFichar);

            }catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            if (result.equals("ERROR Al REGISTRAR")){
                Toast toastValidar = Toast.makeText(getApplicationContext(),"ERROR AL REGISTRAR",Toast.LENGTH_SHORT);
                toastValidar.show();
            }else{
                Toast toastValidarError = Toast.makeText(getApplicationContext(),"REGISTRO COMPLETO",Toast.LENGTH_LONG);
                toastValidarError.show();
                Intent nuevaActividad;
                nuevaActividad = new Intent(Registro.this, LoginActivity.class);
                startActivity(nuevaActividad);
            }
        }

    }
    class asyncValidar extends AsyncTask <String,String,String>{
        String user;
        protected void OnPreExecute(){
            ProgressDialog pDialog = new ProgressDialog(Registro.this);
            pDialog.setMessage("Validando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params){
            user = params[0];
            String respuesta = "ERROR VALIDAR";
            String urlValidar = "http://comunio.garcy.es/?funcion=validarUsuario&usuario="+user;
            try {
                respuesta = getHtml(urlValidar);
            } catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            if (result.equals("EXISTE")){
                nombreValido=false;
                Toast toastValidar = Toast.makeText(getApplicationContext(),"Usuario no válido. Pulse Validar de neuvo",Toast.LENGTH_SHORT);
                toastValidar.show();
            }else{
                nombreValido=true;
                Toast toastValidarError = Toast.makeText(getApplicationContext(),"Usuario válido",Toast.LENGTH_SHORT);
                toastValidarError.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
