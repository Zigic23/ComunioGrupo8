package com.example.pdred.practicaps_final.Equipo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.pdred.practicaps_final.Clases.Jugador.getImagen;
import static com.example.pdred.practicaps_final.Equipo.AlineacionEquipo.refrescarView;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getAlineacion;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPresupuesto;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setVender;

/**
 * Created by Zigic on 16/11/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PlayerHolder>  {


    private List<Jugador> jugadores;
    private Context mContext;

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public RecyclerAdapter(ArrayList<Jugador> jug, Context context){
        this.jugadores = jug;
        this.mContext=context;
    }

    @Override
    public void onBindViewHolder(final PlayerHolder contactViewHolder, int i) {
        final Jugador ju = jugadores.get(i);
        contactViewHolder.vName.setText(ju.getNombreJugador());
        contactViewHolder.vPos.setText(ju.getPosicion());
        contactViewHolder.vImagen.setImageResource(getImagen(ju.getImagenId()));
        if (ju.getJuega() == 1) {
            contactViewHolder.vBotonAdd.setImageResource(R.drawable.down);
        }
        contactViewHolder.vBotonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ju).getJuega() == 0) {
                    boolean introducido = getAlineacion().addJugador((ju));
                    (ju).setJuega(1);
                    if (introducido) {
                        contactViewHolder.vBotonAdd.setImageResource(R.drawable.down);
                        Toast toast1 = Toast.makeText((mContext).getApplicationContext(), (ju).getNombreJugador() + " Convocado", Toast.LENGTH_SHORT);
                        toast1.show();
                    } else {
                        Toast toast2 = Toast.makeText((mContext).getApplicationContext(), "Retire un jugador para insertar", Toast.LENGTH_SHORT);
                        toast2.show();
                    }
                    refrescarView();
                } else {
                    boolean eliminado = getAlineacion().removeJugador((ju));
                    if (eliminado) {
                        contactViewHolder.vBotonAdd.setImageResource(R.drawable.up);
                        Toast toast1 = Toast.makeText((mContext).getApplicationContext(), (ju).getNombreJugador() + " Desconvocado", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                    (ju).setJuega(0);
                    refrescarView();
                    //Toast toast3 = Toast.makeText((mContext).getApplicationContext(), "El jugador ya estaba convocado", Toast.LENGTH_SHORT);
                    //toast3.show();
                }
            }
        });


        /**contactViewHolder.vBotonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean eliminado = getAlineacion().removeJugador((ju));
                (ju).setJuega(0);
                if (eliminado) {
                    Toast toast1 = Toast.makeText((mContext).getApplicationContext(), (ju).getNombreJugador() + " Desconvocado", Toast.LENGTH_SHORT);
                    toast1.show();
                } else {
                    Toast toast1 = Toast.makeText((mContext).getApplicationContext(), "No estaba convocado", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                (ju).setJuega(0);
                refrescarView();
            }
        });*/

        contactViewHolder.botonVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Jugador jugador = (ju);
                int precioVenta = (int) (jugador.getPrecio() *0.7);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(mContext);
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
                        getAlineacion().removeJugador((ju));
                        Toast toast1 = Toast.makeText((mContext).getApplicationContext(), jugador.getNombreJugador() +" vendido", Toast.LENGTH_SHORT);
                        toast1.show();
                        refrescarView();
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

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.entradacardview, viewGroup, false);

        return new PlayerHolder(itemView);
    }



    public class PlayerHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected TextView vPos;
        protected ImageView vImagen;
        protected ImageButton vBotonAdd;
        protected ImageButton vBotonDel;
        protected ImageButton botonVender;
        public PlayerHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vPos = (TextView) v.findViewById(R.id.txtPos);
            vImagen = (ImageView) v.findViewById(R.id.imagenJugador1);
            vBotonAdd = (ImageButton) v.findViewById(R.id.add);
            botonVender = (ImageButton) v.findViewById(R.id.imageButton4);

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
            refrescarView();
        }
    }
}
