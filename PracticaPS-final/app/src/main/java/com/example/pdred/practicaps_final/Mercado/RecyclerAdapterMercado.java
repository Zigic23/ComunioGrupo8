package com.example.pdred.practicaps_final.Mercado;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.pdred.practicaps_final.Clases.Jugador.getImagen;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPresupuesto;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPujar;

/**
 * Created by Zigic on 16/11/2015.
 */
public class RecyclerAdapterMercado extends RecyclerView.Adapter<RecyclerAdapterMercado.PlayerHolder> {


    private List<Jugador> jugadores;

    private Context mContext;

    @Override
    public int getItemCount() {
        return jugadores.size();
    }

    public RecyclerAdapterMercado(ArrayList<Jugador> jug, Context context) {
        this.jugadores = jug;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(PlayerHolder contactViewHolder, int i) {
        final Jugador ju = jugadores.get(i);

        contactViewHolder.texto_nombre.setText(ju.getNombreJugador());
        contactViewHolder.texto_posicion.setText("Posicion: " + (ju.getPosicion()));
        contactViewHolder.texto_precio.setText("Precio: " + String.valueOf(ju.getPrecio()));
        try {
            Bitmap imagen = new asyncImagen().execute("http://st.comuniazo.com/img/players/"+ju.getImagenId()+".png").get();
            contactViewHolder.imagen_entrada.setImageBitmap(imagen);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (ju.getOwner().equals("System")) {
            contactViewHolder.icono.setVisibility(View.INVISIBLE);
        } else {
            contactViewHolder.icono.setImageResource(R.drawable.subasta);
            contactViewHolder.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast1 = Toast.makeText(mContext.getApplicationContext(), "El jugador esta subastado", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            });
        }


        if (ju.getOwner().equals(getCurrentUser().getNombreUsuario())) {
            contactViewHolder.botonPujar.setVisibility(View.INVISIBLE);
        } else {
            if (ju.getPrecio() > getCurrentUser().getDinero()){
                contactViewHolder.botonPujar.setVisibility(View.INVISIBLE);
            }else{
                contactViewHolder.botonPujar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Jugador jugador = ju;
                        AlertDialog d = ((Mercado) mContext).createFicharDialogo(jugador);
                        d.show();
                    }
                });
            }
        }
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.entrada, viewGroup, false);

        return new PlayerHolder(itemView);
    }


    public class PlayerHolder extends RecyclerView.ViewHolder {


        protected TextView texto_posicion;
        protected TextView texto_nombre;
        protected TextView texto_precio;
        protected ImageView imagen_entrada;
        protected ImageButton icono;
        protected ImageButton botonPujar;

        public PlayerHolder(View v) {
            super(v);
            texto_posicion = (TextView) v.findViewById(R.id.textView2);
            texto_nombre = (TextView) v.findViewById(R.id.textView_superior);
            texto_precio = (TextView) v.findViewById(R.id.textView_inferior);
            imagen_entrada = (ImageView) v.findViewById(R.id.imageView_imagen);
            icono = (ImageButton) v.findViewById(R.id.imageButtonSubasta);
            botonPujar = (ImageButton) v.findViewById(R.id.imageButton3);
        }
    }

    class asyncImagen extends AsyncTask<String,String,Bitmap> {
        //Declaramos las variables que vamos a recoger
        String url;
        Bitmap imagen;
        // PRE-EJECUCION: esto se ejecutara ANTES del background, en este caso muestra un dialogo de proceso y un mensaje que se puede editar
        protected void onPreExecute(){
        }
        // EJECUCIÓN: Aqui va el codigo que se realizara en background
        @Override
        protected Bitmap doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            url= params[0];
            String respuesta = "ERROR1";
            //Construye una url valida para hacer la consulta (Ver UtilidadesLogin)
            imagen = DownloadImage(url);
            //Devuelve como argumento el HTML o "ERROR1" (Devolvera "ERROR" si el usuario o contraseña son incorrectos)
            return imagen;
        }
        //POST-EJECUCIÓN: Recoge lo que devuelve "doInBackground" y actua en funcion del resultado
        protected void onPostExecute (Bitmap result){
        }
    }
    private Bitmap DownloadImage(String imageHttpAddress){
        URL imageUrl;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return imagen;
    }


}
