package com.example.zigic.test3;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EquipoActivity extends Activity {

    String nomUsuario = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<MostrarJugador> listado;
        listado = getPlayers();
        if (listado.isEmpty()) {
            setContentView(R.layout.noteam);
        } else {
            setContentView(R.layout.activity_equipo);
            setAll(listado);
        }


    }

    public void setAll (ArrayList<MostrarJugador> listac) {
        ListView lista = (ListView) findViewById(R.id.listView1);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, listac) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_superior);
                texto_nombre.setText(((MostrarJugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.textView2);
                texto_posicion.setText(((MostrarJugador) entrada).getPosicion());

                TextView texto_precio= (TextView) view.findViewById(R.id.textView_inferior);
                texto_precio.setText("Precio: " + String.valueOf(((MostrarJugador) entrada).getPrecio()));

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                imagen_entrada.setImageResource(((MostrarJugador) entrada).getImagenId());
            }
        });
    }

    public ArrayList<MostrarJugador> getPlayers(){
        ArrayList <MostrarJugador> auxp = new ArrayList<>();
        auxp.add(new MostrarJugador(R.drawable.messi, "Messi", "delantero", 10000));
        auxp.add(new MostrarJugador(R.drawable.cristiano_ronaldo, "Cristiano", "delantero", 100000));
        for (int i = 1; i<14; i++) {
            auxp.add(new MostrarJugador(R.drawable.imagenlogin1, "ladsfqwer", "jfaidsjfw", 193935));
        }
        //acceder a base de datos
        return auxp;

    }
}
