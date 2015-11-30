package com.example.pdred.practicaps_final.Mercado;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Equipo.RecyclerAdapter;
import com.example.pdred.practicaps_final.Liga_Comunidad.RecyclerAdapterLiga;
import com.example.pdred.practicaps_final.Main_Menu.Inicio;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.example.pdred.practicaps_final.Clases.Equipo.parsearJugadores;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setMercadoComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPresupuesto;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPujar;

public class Mercado extends AppCompatActivity {
    // Actividad Mercado

    // ListView donde se añadiran los jugadores
    RecyclerView listView44;
    private RecyclerView.Adapter adapter;
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
            listView44  = (RecyclerView) findViewById(R.id.listViewMercado);
            setAll(enVenta);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Mercado.this, Inicio.class);
            startActivity(nuevaActividad);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

        TextView titulo = (TextView) findViewById(R.id.editTextMercado);
        titulo.setText("Las pujas acaban el: "+calcNextMonday());
        Date ahora = new Date();
        SimpleDateFormat formateador2 = new SimpleDateFormat("yyyy");
        TextView anyo = (TextView) findViewById(R.id.anyo);
        anyo.setText(formateador2.format(ahora));
        SimpleDateFormat formateador = new SimpleDateFormat("EEE, MMM d");
        TextView fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(formateador.format(ahora));
        final LinearLayoutManager lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView44.setLayoutManager(lManager);
        adapter = new RecyclerAdapterMercado(listac, this);
        listView44.setAdapter(adapter);
    }

    class asyncPujar extends AsyncTask<String,String,String> {
        //Declaramos las variables que vamos a recoger
        // Para la puja
        String userComprador;
        String presupuestoCompradorNuevo;
        String idjugador;
        String nuevoPrecio;
        // Ajuste de presupuesto antiguo usuario
        String userAnterior;
        String devolucion;
        // PRE-EJECUCION: esto se ejecutara ANTES del background, en este caso muestra un dialogo de proceso y un mensaje que se puede editar
        protected void onPreExecute(){
        }
        // EJECUCIÓN: Aqui va el codigo que se realizara en background
        @Override
        protected String doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            userComprador= params[0];
            presupuestoCompradorNuevo = params[1];
            idjugador= params[2];
            nuevoPrecio= params[3];
            String respuesta = "ERROR1";
            //Para realizar la puja
            String urlPujar = setPujar(userComprador, idjugador,nuevoPrecio);
            //Para descontar al usuario el importe de la puja
            String urlDescontar = setPresupuesto(userComprador, presupuestoCompradorNuevo);
            try {
                // Realizamos la puja
                getHtml(urlPujar);
                // Descontamos el valor de la puja al usuario
                getHtml(urlDescontar);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Devuelve como argumento el HTML o "ERROR1" (Devolvera "ERROR" si el usuario o contraseña son incorrectos)
            return respuesta;
        }
        //POST-EJECUCIÓN: Recoge lo que devuelve "doInBackground" y actua en funcion del resultado
        protected void onPostExecute (String result){
            Intent nuevaActividad;
            nuevaActividad = new Intent(Mercado.this, Mercado.class);
            startActivity(nuevaActividad);
            Toast toast1 = Toast.makeText(Mercado.this,"Puja realizada con éxito", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }

    public AlertDialog createFicharDialogo(final Jugador jugador) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.fichar, null);

        builder.setView(v);
        final int precioActual = jugador.getPrecio();
        String precioActualStr = String.valueOf(precioActual+100);
        final EditText puja = (EditText) v.findViewById(R.id.editTextPuja);
        puja.setText(precioActualStr);
        final int id = jugador.getImagenId();
        Button pujar = (Button) v.findViewById(R.id.buttonPujar);
        Button cancelar = (Button) v.findViewById(R.id.buttonCancel);

        pujar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pujaActualAux = puja.getText().toString();
                        int pujaActual = Integer.parseInt(pujaActualAux);
                        if (pujaActual > getCurrentUser().getDinero()){
                            Toast toast1 = Toast.makeText((builder.getContext()).getApplicationContext(),"No tiene suficiente presupuesto", Toast.LENGTH_SHORT);
                            toast1.show();
                        }else{
                            if (pujaActual < precioActual){
                                Toast toast1 = Toast.makeText((builder.getContext()).getApplicationContext(),"Puja insuficiente", Toast.LENGTH_SHORT);
                                toast1.show();
                            }else{
                                // Valores async
                                String userComprador = getCurrentUser().getNombreUsuario();
                                String presupuestoCompradorNuevo = String.valueOf(getCurrentUser().getDinero()-pujaActual);
                                String idjugador = String.valueOf(id);
                                String nuevoPrecio = String.valueOf(pujaActual);
                                new asyncPujar().execute(userComprador,presupuestoCompradorNuevo,idjugador,nuevoPrecio);
                                // Actualizamos en local el dinero actual
                                getCurrentUser().setDinero(getCurrentUser().getDinero()-pujaActual);
                            }
                        }
                    }
                }
        );

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nuevaActividad;
                        nuevaActividad = new Intent(Mercado.this, Mercado.class);
                        startActivity(nuevaActividad);
                    }
                }

        );
        return builder.create();
    }

    private String calcNextMonday() {
        Calendar today = Calendar.getInstance();
        int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
        int daysUntilNextFriday = Calendar.MONDAY - dayOfWeek;
        if(daysUntilNextFriday < 0){
            daysUntilNextFriday = daysUntilNextFriday + 7;
        }
        Calendar nextFriday = (Calendar)today.clone();
        nextFriday.add(Calendar.DAY_OF_WEEK, daysUntilNextFriday);
        if(nextFriday.get(Calendar.WEEK_OF_YEAR) % 2 == 0){
            nextFriday.add(Calendar.DAY_OF_WEEK, 7);
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(nextFriday.getTime());
    }


}
