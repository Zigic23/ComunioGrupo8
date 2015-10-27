package com.example.pdred.practicaps_final;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Zigic on 24/10/2015.
 */
public class EquipoActivity extends Activity {

    String nomUsuario;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo);
        nomUsuario = "";
        ArrayList <MostrarJugador> listado;
        listado = getPlayers();
        ListView lista = (ListView) findViewById(R.id.listView1);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, listado) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_superior);
                texto_nombre.setText(((MostrarJugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.textView2);
                texto_posicion.setText(((MostrarJugador) entrada).getPosicion());

                TextView texto_precio= (TextView) view.findViewById(R.id.textView_inferior);
                texto_precio.setText(((MostrarJugador) entrada).getPrecio());

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                imagen_entrada.setImageResource(((MostrarJugador) entrada).getImagenId());
            }
        });

    }

    public ArrayList<MostrarJugador> getPlayers(){
        ArrayList <MostrarJugador> auxp = new ArrayList<>();
        auxp.add(new MostrarJugador(1, "Messi", "delantero", 10000));
        auxp.add(new MostrarJugador(2, "Cristiano", "delantero",100000));
        //acceder a base de datos
        return auxp;

    }
}