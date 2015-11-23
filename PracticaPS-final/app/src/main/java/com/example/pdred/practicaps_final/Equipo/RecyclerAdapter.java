package com.example.pdred.practicaps_final.Equipo;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getAlineacion;

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
    public void onBindViewHolder(PlayerHolder contactViewHolder, int i) {
        final Jugador ju = jugadores.get(i);
        contactViewHolder.vName.setText(ju.getNombreJugador());
        contactViewHolder.vPos.setText(ju.getPosicion());
        contactViewHolder.vImagen.setImageResource(ju.getImagenId());
        contactViewHolder.vBotonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ju).getJuega() == 0) {
                    boolean introducido = getAlineacion().addJugador((ju ));
                    (ju).setJuega(1);
                    if (introducido) {
                        Toast toast1 = Toast.makeText((mContext).getApplicationContext(), ( ju).getNombreJugador() + " Convocado", Toast.LENGTH_SHORT);
                        toast1.show();
                    } else {
                        Toast toast2 = Toast.makeText((mContext).getApplicationContext(), "Retire un jugador para insertar", Toast.LENGTH_SHORT);
                        toast2.show();
                    }
                } else {
                    Toast toast3 = Toast.makeText((mContext).getApplicationContext(), "El jugador ya estaba convocado", Toast.LENGTH_SHORT);
                    toast3.show();
                }
            }
        });


        contactViewHolder.vBotonDel.setOnClickListener(new View.OnClickListener() {
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
        public PlayerHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vPos = (TextView) v.findViewById(R.id.txtPos);
            vImagen = (ImageView) v.findViewById(R.id.imagenJugador1);
            vBotonAdd = (ImageButton) v.findViewById(R.id.add);
            vBotonDel = (ImageButton) v.findViewById(R.id.delete);
        }
    }
}
