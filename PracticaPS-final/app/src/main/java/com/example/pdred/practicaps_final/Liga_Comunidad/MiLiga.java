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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Usuario> listado = getUsuariosLiga();
        setContentView(R.layout.activity_mi_liga);
        setAll(listado);
    }

    public void setAll(ArrayList<Usuario> listac) {
        ListView lista = (ListView) findViewById(R.id.listView2);
        lista.setAdapter(new Lista_adaptador_Liga(this, R.layout.entrada_liga, listac) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView texto_nombre = (TextView) view.findViewById(R.id.textView_NombreUsuario);
                texto_nombre.setText(((Usuario) entrada).getNombreUsuario());

                TextView texto_puntos = (TextView) view.findViewById(R.id.textView_Puntos);
                texto_puntos.setText("Puntos: "+String.valueOf(((Usuario) entrada).getPuntos()));

                //TextView texto_equipo = (TextView) view.findViewById(R.id.textView_Equipo);
                //texto_equipo.setText(((Usuario) entrada).getEquipo().getNombreEquipo());

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen2);
                imagen_entrada.setImageResource(R.drawable.sym_def_app_icon);
            }
        });
    }
}
