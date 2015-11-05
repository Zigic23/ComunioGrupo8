package com.example.pdred.practicaps_final.Equipo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.R;

import java.util.ArrayList;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;

public class EquipoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Jugador> listado = getCurrentUser().getEquipo().getListaJugadores();
        if (listado.isEmpty()) {
            setContentView(R.layout.noteam);
        } else {
            setContentView(R.layout.activity_equipo);
            setAll(listado);
        }
    }

    public void setAll (ArrayList<Jugador> listac) {
        ListView lista = (ListView) findViewById(R.id.listView1);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, listac) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_superior);
                texto_nombre.setText(((Jugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.textView2);
                texto_posicion.setText(((Jugador) entrada).getPosicion());

                TextView texto_precio= (TextView) view.findViewById(R.id.textView_inferior);
                texto_precio.setText("Precio: " + String.valueOf(((Jugador) entrada).getPrecio()));

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                imagen_entrada.setImageResource(((Jugador) entrada).getImagenId());
            }
        });
    }
}
