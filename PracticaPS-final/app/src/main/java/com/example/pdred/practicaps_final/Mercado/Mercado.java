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
    ListView listView44;
    ArrayList<Jugador> enVenta;
    ArrayList<String> jugadores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String comunidad = getCurrentUser().getNombreLiga();
        try {

            createAsyncTask(comunidad);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        enVenta = parsearJugadores(jugadores);
        if (enVenta.isEmpty()) {
            setContentView(R.layout.noplayers);
        } else {
            setContentView(R.layout.activity_mercado);
            listView44  = (ListView) findViewById(R.id.listViewMercado);
            setAll(enVenta);
        }

        /**listView44.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View v, int position,
        long arg3) {
        String value = (String) adapter.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),"pene" + value, Toast.LENGTH_SHORT);
        }
        });
         */
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
                //Realiza la peticion al PHP, si es correcta devolvera el contenido del HTML
                jugadores = getAllLines(url);
                respuesta = "conseguidas las lineas";
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Devuelve como argumento el HTML o "ERROR1"
            return respuesta;
        }

        protected void onPostExecute (String result){
            if (jugadores.isEmpty()) {
                Intent nuevaActividad;
                nuevaActividad = new Intent(Mercado.this, Inicio.class);
                startActivity(nuevaActividad);
                err_merc();
                //En caso contrario sacara un error por pantalla
            }
            pDialog.hide();
        }

    }

    public void err_merc(){
        Toast toast1 = Toast.makeText(getApplicationContext(),"Error. No se han cargado los datos.",Toast.LENGTH_SHORT);
        toast1.show();
    }

    public void setAll (ArrayList<Jugador> listac) {
        listView44.setAdapter(new Lista_adaptador_Mercado(this, R.layout.entrada, listac) {
            @Override
            public void onEntrada(Object entrada, View view) {

                int idImagen = getImagen(((Jugador) entrada).getImagenId());

                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_superior);
                texto_nombre.setText(((Jugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.textView2);
                texto_posicion.setText("Posicion: " + ((Jugador) entrada).getPosicion());

                TextView texto_precio = (TextView) view.findViewById(R.id.textView_inferior);
                texto_precio.setText("Precio: " + String.valueOf(((Jugador) entrada).getPrecio()));

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                imagen_entrada.setImageResource(idImagen);
            }
        });


    }
}
