package com.example.pdred.practicaps_final.Mercado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Main_Menu.Inicio;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.pdred.practicaps_final.Clases.Equipo.parsearJugadores;
import static com.example.pdred.practicaps_final.Clases.Jugador.getImagen;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setMercadoComunidad;

public class Mercado extends AppCompatActivity {
    // Actividad Mercado

    // ListView donde se añadiran los jugadores
    ListView listView44;
    // Jugadores que apareceran en el mercado
    ArrayList<Jugador> enVenta;
    // String con los datos de los jugadores (Para parsear)
    ArrayList<String> jugadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recoge la comunidad del usuario
        String comunidad = getCurrentUser().getNombreLiga();
        try {
            // Pide a la base de datos el mercado de esa comunidad (BACKGROUND)
            createAsyncTask(comunidad);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // En el BACKGROUND se genera el los String con los datos de los jugadores
        // Parseamos dichos String al tipo Jugador
        enVenta = parsearJugadores(jugadores);
        if (enVenta.isEmpty()) {
            // Si no hay jugadores, cambia el fondo de la actividad
            setContentView(R.layout.noplayers);
        } else {
            // Si encuentra jugadores, os añade al ListView
            setContentView(R.layout.activity_mercado);
            listView44  = (ListView) findViewById(R.id.listViewMercado);
            setAll(enVenta);
        }

    }


    public void createAsyncTask (String url) throws ExecutionException, InterruptedException {
        String str_result= new asyncmerc().execute(url).get();
    }

    class asyncmerc extends AsyncTask<String,String,String> {

        String comunity;
        ProgressDialog pDialog;

        protected void onPreExecute(){
            pDialog = new ProgressDialog(Mercado.this);
            pDialog.setMessage("Cargando jugadores...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            comunity = params[0];
            String respuesta = "ERROR1";
            //Construye una url valida para hacer la consulta
            String url = setMercadoComunidad(comunity);
            try {
                //Realiza la peticion al PHP, si es correcta devolvera el contenido del HTML (Jugadores
                jugadores = getAllLines(url);
                respuesta = "conseguidas las lineas";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return respuesta;
        }

        protected void onPostExecute (String result){
            pDialog.hide();
        }

    }

    public void setAll (ArrayList<Jugador> listac) {
        // La ListView recibe los jugadores
        listView44.setAdapter(new Lista_adaptador_Mercado(this, R.layout.entrada, listac) {
            @Override
            // Para cada jugador crear una nueva entrada
            public void onEntrada(Object entrada, View view) {
                // Consigue una ID local de la imagen a partir de la ID del Jugador
                int idImagen = getImagen(((Jugador) entrada).getImagenId());
                // Muestra el nombre del jugador
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_superior);
                texto_nombre.setText(((Jugador) entrada).getNombreJugador());
                // Muestra la posicion del jugador
                TextView texto_posicion = (TextView) view.findViewById(R.id.textView2);
                texto_posicion.setText("Posicion: " + ((Jugador) entrada).getPosicion());
                // Muestra el precio del jugador
                TextView texto_precio = (TextView) view.findViewById(R.id.textView_inferior);
                texto_precio.setText("Precio: " + String.valueOf(((Jugador) entrada).getPrecio()));
                // Muestra la imagen del jugador
                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                imagen_entrada.setImageResource(idImagen);
            }
        });


    }
}
