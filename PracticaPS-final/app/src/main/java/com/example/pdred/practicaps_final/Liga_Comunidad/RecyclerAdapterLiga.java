package com.example.pdred.practicaps_final.Liga_Comunidad;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pdred.practicaps_final.Clases.Usuario;
import com.example.pdred.practicaps_final.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setPresupuesto;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setVender;

/**
 * Created by Zigic on 16/11/2015.
 */
public class RecyclerAdapterLiga extends RecyclerView.Adapter<RecyclerAdapterLiga.PlayerHolder> {


    private List<Usuario> usuarios;

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public RecyclerAdapterLiga(ArrayList<Usuario> jug) {
        this.usuarios = jug;
    }

    @Override
    public void onBindViewHolder(PlayerHolder contactViewHolder, int i) {
        final Usuario user = usuarios.get(i);
        contactViewHolder.texto_nombre.setText(user.getNombreUsuario());
        contactViewHolder.texto_puntos.setText("Puntos: " + String.valueOf(user.getPuntos()));
        contactViewHolder.imagen_entrada.setImageResource(R.drawable.sym_def_app_icon);
        if (i > 2) {
            contactViewHolder.imageRanked.setVisibility(View.INVISIBLE);
        } else {
            if (i == 1) {
                contactViewHolder.imageRanked.setImageResource(R.drawable.plata);
                contactViewHolder.imagen_entrada.setImageResource(R.drawable.jm);
            }
            if (i == 2) {
                contactViewHolder.imageRanked.setImageResource(R.drawable.bronze);
                contactViewHolder.imagen_entrada.setImageResource(R.drawable.cs);
            }
        }
        if (i == 0) {
            contactViewHolder.imagen_entrada.setImageResource(R.drawable.rb);
        }
        if (i == 3) {
            contactViewHolder.imagen_entrada.setImageResource(R.drawable.ca);
        }
        if (i == 4) {
            contactViewHolder.imagen_entrada.setImageResource(R.drawable.jk);
        }
        if (i == 5) {
            contactViewHolder.imagen_entrada.setImageResource(R.drawable.jg);
        }
        if (i == 6) {
            contactViewHolder.imagen_entrada.setImageResource(R.drawable.af);
        }

    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.entrada_liga, viewGroup, false);

        return new PlayerHolder(itemView);
    }


    public class PlayerHolder extends RecyclerView.ViewHolder {

        protected TextView texto_nombre;
        protected TextView texto_puntos;
        protected ImageView imageRanked;
        protected ImageView imagen_entrada;

        public PlayerHolder(View v) {
            super(v);
            texto_nombre = (TextView) v.findViewById(R.id.textView_NombreUsuario);
            texto_puntos = (TextView) v.findViewById(R.id.textView_Puntos);
            imagen_entrada = (ImageView) v.findViewById(R.id.imageView_imagen2);
            imageRanked = (ImageButton) v.findViewById(R.id.ranked);
        }
    }
}
