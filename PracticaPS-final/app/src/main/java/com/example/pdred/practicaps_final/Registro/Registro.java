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
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentComunidad;
import static com.example.pdred.practicaps_final.UsuarioEstatico.isComunidadNueva;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setFichar;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroEquipo;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setRegistroNuevaComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setValidarUsuario;


public class Registro extends AppCompatActivity {

    private boolean nombreValido = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // Informa al usuario de la comunidad a la que se va a unir
        TextView tVNombreComunidad = (TextView) findViewById(R.id.tVComunidad);
        tVNombreComunidad.setText("Comunidad: "+getCurrentComunidad());
        // Recoje el boton validar del Layout y con la información que ha introducido el usuario (Nombre), comprueba si ya existe
        Button bValidar = (Button) findViewById(R.id.bValidar);
        bValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String usuario = ((EditText)findViewById(R.id.eTUsuario)).getText().toString();
                new asyncValidar().execute(usuario);
            }
        });
        // Recoje el boton del Layout y todos los campos del registro, que usa para crear un nuevo usuario
        Button bRegistrar = (Button) findViewById(R.id.bRegistrar);
        bRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String usuario = ((EditText)findViewById(R.id.eTUsuario)).getText().toString();
                String password1= ((EditText)findViewById(R.id.eTPass1)).getText().toString();
                String password2= ((EditText)findViewById(R.id.eTPass2)).getText().toString();
                String equipo= ((EditText)findViewById(R.id.eTNombreEquipo)).getText().toString();
                // Si el nombre es valido (comprobado)
                if(nombreValido){
                    // Si el usuario es valido, lo introduce a la base de daros
                    if (password1.equals(password2)){ // Solo si las contraseñas son iguales
                        new asynRegistrar().execute(usuario,password1,equipo);
                    }else{ // Si no, muestra el error por pantalla
                        Toast toastRegistrar = Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT);
                        toastRegistrar.show();
                    }
                }else{
                    // Muestra si no se ha validad o el usuario no es valido
                    Toast toastValidar = Toast.makeText(getApplicationContext(),"Valida tu usuario o usuario no válido",Toast.LENGTH_SHORT);
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
            String respuesta = "ERROR";

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
            if (result.equals("ERROR")){
                // Si la respuesta de la base de datos es ERROR, lo muestra por pantalla
                Toast toastValidar = Toast.makeText(getApplicationContext(),"ERROR AL REGISTRAR",Toast.LENGTH_SHORT);
                toastValidar.show();
            }else{
                // Si no, informa al usuario de que ha terminado el registro y salta al LOGIN
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
            String urlValidar = setValidarUsuario(user);

            try {
                // Pide una respuesta a la base de datos
                respuesta = getHtml(urlValidar);
            } catch (IOException e){
                e.printStackTrace();
            }
            return respuesta;
        }
        protected void onPostExecute (String result){
            if (result.equals("EXISTE")){
                // Si existe, pone nombre valido a false
                nombreValido=false;
                Toast toastValidar = Toast.makeText(getApplicationContext(),"Usuario no válido. Pulse Validar de neuvo",Toast.LENGTH_SHORT);
                toastValidar.show();
            }else{
                // Si no existe, pone nombre válido a true
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

}
