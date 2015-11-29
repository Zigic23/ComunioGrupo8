package com.example.pdred.practicaps_final.Liga_Comunidad;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pdred.practicaps_final.Clases.Usuario;
import com.example.pdred.practicaps_final.R;

import java.util.ArrayList;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentComunidad;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;
import static com.example.pdred.practicaps_final.UsuarioEstatico.getUsuariosLiga;


public class MiLiga extends AppCompatActivity {
    // Esta actividad muestra los usuarios de la liga a la que pertenece el jugador
    // Deberia tener su propio BACKGROUND (igual que Mercado)

    static ArrayList<Usuario> listado;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recoge los usuarios de la liga
        listado = getUsuariosLiga();
        setContentView(R.layout.activity_mi_liga);
        //Los introduce a la lista
        setAll(listado);
    }

    public void setAll(ArrayList<Usuario> listac) {
        TextView titulo = (TextView) findViewById(R.id.editText4);
        titulo.setText(getCurrentUser().getNombreLiga());
        RecyclerView lista = (RecyclerView) findViewById(R.id.listView2);
        lManager = new LinearLayoutManager(this);
        lista.setLayoutManager(lManager);
        adapter = new RecyclerAdapterLiga(listac);
        lista.setAdapter(adapter);
    }
}
