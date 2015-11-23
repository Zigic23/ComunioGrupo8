package com.example.pdred.practicaps_final.Liga_Comunidad;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pdred.practicaps_final.Clases.Usuario;
import com.example.pdred.practicaps_final.R;

import java.util.ArrayList;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getUsuariosLiga;


public class MiLiga extends AppCompatActivity {
    // Esta actividad muestra los usuarios de la liga a la que pertenece el jugador
    // Deberia tener su propio BACKGROUND (igual que Mercado)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recoge los usuarios de la liga
        ArrayList<Usuario> listado = getUsuariosLiga();
        setContentView(R.layout.activity_mi_liga);
        //Los introduce a la lista
        setAll(listado);
    }

    public void setAll(ArrayList<Usuario> listac) {
        ListView lista = (ListView) findViewById(R.id.listView2);
        lista.setAdapter(new Lista_adaptador_Liga(this, R.layout.entrada_liga, listac) {
            @Override
            // Para cada usuario, genera los siguientes datos en su entrada
            public void onEntrada(Object entrada, View view) {
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_NombreUsuario);
                texto_nombre.setText(((Usuario) entrada).getNombreUsuario());

                TextView texto_puntos = (TextView) view.findViewById(R.id.textView_Puntos);
                texto_puntos.setText("Puntos: "+String.valueOf(((Usuario) entrada).getPuntos()));

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen2);
                imagen_entrada.setImageResource(R.drawable.sym_def_app_icon);
            }
        });
    }
}
