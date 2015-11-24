package com.example.pdred.practicaps_final.Equipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.R;

import java.util.ArrayList;

import java.io.IOException;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getAlineacion;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setAlineacion;

public class MiEquipo extends Activity{

    static ArrayList<Jugador> listado;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listado = getCurrentUser().getEquipo().getListaJugadores();
        if (listado.isEmpty()) {
            setContentView(R.layout.noteam);
        } else {
            setContentView(R.layout.activity_equipo);
            setAll(listado);
        }

        Button botonAlineacion = (Button) findViewById(R.id.button10);

        botonAlineacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaActividad;
                nuevaActividad = new Intent(MiEquipo.this, AlineacionEquipo.class);
                startActivity(nuevaActividad);
            }
        });
    }

    public void onStop() {
        // ESTO OCURRIRA CUANDO LA ACTIVIDAD DEJE DE SER VISIBLE AL USUARIO
        super.onStop();
        String user = getCurrentUser().getNombreUsuario();
        String alineacion = getAlineacion().getIDs();
        new asyncAlineacion().execute(user,alineacion);
    }

    public void setAll (ArrayList<Jugador> listac) {
        RecyclerView lista = (RecyclerView)findViewById(R.id.recycler1);
        lManager = new LinearLayoutManager(this);
        lista.setLayoutManager(lManager);
        adapter = new RecyclerAdapter(listac, this);
        lista.setAdapter(adapter);
    }

    class asyncAlineacion extends AsyncTask<String,String,String> {
        //Declaramos las variables que vamos a recoger
        String user;
        String alineacion;
        ProgressDialog pDialog;
        // PRE-EJECUCION: esto se ejecutara ANTES del background, en este caso muestra un dialogo de proceso y un mensaje que se puede editar
        protected void onPreExecute(){
            /**pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Autenticando ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
        // EJECUCIÓN: Aqui va el codigo que se realizara en background
        @Override
        protected String doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            user= params[0];
            alineacion= params[1];
            String respuesta = "ERROR1";
            //Construye una url valida para hacer la consulta (Ver UtilidadesLogin)
            String url = setAlineacion(user, alineacion);
            try {
                //Realiza la peticion al PHP, la respuesta nos da igual
                getHtml(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Devuelve como argumento el HTML o "ERROR1" (Devolvera "ERROR" si el usuario o contraseña son incorrectos)
            return respuesta;
        }
        //POST-EJECUCIÓN: Recoge lo que devuelve "doInBackground" y actua en funcion del resultado
        protected void onPostExecute (String result){
            //Si el login es correcto, "result" sera "OK" y pasara a la siguiente activity
            /**if (result.equals("OK")){
                // Avanzamos al main
                Intent nuevaActividad;
                nuevaActividad = new Intent(LoginActivity.this, Inicio.class);
                startActivity(nuevaActividad);
            } else {err_login();} //En caso contrario sacara un error por pantalla
            pDialog.hide();*/
        }
    }
}
