package com.example.pdred.practicaps_final.Equipo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Main_Menu.Inicio;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.pdred.practicaps_final.Clases.Jugador.getImagen;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getAlineacion;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setAlineacion;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPresupuesto;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setVender;

public class MiEquipo extends Activity{

    static ArrayList<Jugador> listado;

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
        final ListView lista = (ListView) findViewById(R.id.listView1);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_jug, listac) {
            @Override
            public void onEntrada(final Object entrada, View view) {
                int idImagen = getImagen(((Jugador) entrada).getImagenId());

                TextView texto_nombre = (TextView) view.findViewById(R.id.nombre);
                texto_nombre.setText(((Jugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.posicion);
                texto_posicion.setText(((Jugador) entrada).getPosicion());

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView2);
                imagen_entrada.setImageResource(idImagen);

                ImageButton botonAdd = (ImageButton) view.findViewById(R.id.imageButton);
                botonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Jugador) entrada).getJuega() == 0) {
                            boolean introducido = getAlineacion().addJugador(((Jugador) entrada));
                            ((Jugador) entrada).setJuega(1);
                            if (introducido) {
                                Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Convocado", Toast.LENGTH_SHORT);
                                toast1.show();
                            } else {
                                Toast toast2 = Toast.makeText(getApplicationContext(), "Retire un jugador para insertar", Toast.LENGTH_SHORT);
                                toast2.show();
                            }
                        } else {
                            Toast toast3 = Toast.makeText(getApplicationContext(), "El jugador ya estaba convocado", Toast.LENGTH_SHORT);
                            toast3.show();
                        }
                    }
                });

                ImageButton botonDelete = (ImageButton) view.findViewById(R.id.imageButton2);
                botonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean eliminado = getAlineacion().removeJugador(((Jugador) entrada));
                        ((Jugador) entrada).setJuega(0);
                        if (eliminado) {
                            Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Desconvocado", Toast.LENGTH_SHORT);
                            toast1.show();
                        } else {
                            Toast toast1 = Toast.makeText(getApplicationContext(), "No estaba convocado", Toast.LENGTH_SHORT);
                            toast1.show();
                        }
                        ((Jugador) entrada).setJuega(0);
                    }
                });

                ImageButton botonvender = (ImageButton) view.findViewById(R.id.imageButton4);
                botonvender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Jugador jugador = ((Jugador) entrada);
                        int precioVenta = (int) (jugador.getPrecio() *0.7);
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MiEquipo.this);
                        dialogo1.setTitle("Importante");
                        dialogo1.setMessage("¿ Desea vender a " + jugador.getNombreJugador() + " por "+precioVenta+" ?");
                        dialogo1.setCancelable(false);
                        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                String id2 = String.valueOf(jugador.getImagenId());
                                int dineroDevuelto = (int) (0.7*jugador.getPrecio());
                                int nuevoPresupuesto = getCurrentUser().getDinero() + dineroDevuelto;
                                // Hacemos la venta por Background (PARA LA BASE DE DATOS)
                                String nuevoPresupuesto2 = String.valueOf(nuevoPresupuesto);
                                new asyncVender().execute(getCurrentUser().getNombreUsuario(), id2, nuevoPresupuesto2);
                                // Hacemos la venta en la aplicacion (PARA EL USUARIO DE LA APP)
                                getCurrentUser().getEquipo().getListaJugadores().remove(jugador);
                                getCurrentUser().setDinero(nuevoPresupuesto);
                                Toast toast1 = Toast.makeText(getApplicationContext(), jugador.getNombreJugador() +" vendido", Toast.LENGTH_SHORT);
                                toast1.show();
                            }
                        });
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                            }
                        });
                        dialogo1.show();
                    }


                });

            }
        });
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

    class asyncVender extends AsyncTask<String,String,String> {
        //Declaramos las variables que vamos a recoger
        String user;
        String idjugador;
        String nuevoPresupuesto;
        ProgressDialog pDialog;
        // PRE-EJECUCION: esto se ejecutara ANTES del background, en este caso muestra un dialogo de proceso y un mensaje que se puede editar
        protected void onPreExecute(){

        }
        // EJECUCIÓN: Aqui va el codigo que se realizara en background
        @Override
        protected String doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            user= params[0];
            idjugador = params[1];
            nuevoPresupuesto = params[2];
            String respuesta = "ERROR1";
            //Construye una url valida para hacer la consulta (Ver UtilidadesLogin)
            String url = setVender(user, idjugador);
            String url2 = setPresupuesto(user,nuevoPresupuesto);
            try {
                //Realiza la peticion al PHP, la respuesta nos da igual
                getHtml(url);
                getHtml(url2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Devuelve como argumento el HTML o "ERROR1" (Devolvera "ERROR" si el usuario o contraseña son incorrectos)
            return respuesta;
        }
        //POST-EJECUCIÓN: Recoge lo que devuelve "doInBackground" y actua en funcion del resultado
        protected void onPostExecute (String result){
        }
    }
}
