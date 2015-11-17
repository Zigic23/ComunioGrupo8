package com.example.pdred.practicaps_final.Equipo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.pdred.practicaps_final.R;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getAlineacion;


public class AlineacionEquipo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alineacion);

        TextView guardameta = (TextView) findViewById(R.id.textViewPortero);
        guardameta.setText(getAlineacion().Portero.getNombreJugador());

        TextView defensa1 = (TextView) findViewById(R.id.textViewDefensa1);
        defensa1.setText(getAlineacion().Defensa1.getNombreJugador());
        TextView defensa2 = (TextView) findViewById(R.id.textViewDefensa2);
        defensa2.setText(getAlineacion().Defensa2.getNombreJugador());
        TextView defensa3 = (TextView) findViewById(R.id.textViewDefensa3);
        defensa3.setText(getAlineacion().Defensa3.getNombreJugador());
        TextView defensa4 = (TextView) findViewById(R.id.textViewDefensa4);
        defensa4.setText(getAlineacion().Defensa4.getNombreJugador());

        TextView medio1 = (TextView) findViewById(R.id.textViewMedio1);
        medio1.setText(getAlineacion().Medio1.getNombreJugador());
        TextView medio2 = (TextView) findViewById(R.id.textViewMedio2);
        medio2.setText(getAlineacion().Medio2.getNombreJugador());
        TextView medio3 = (TextView) findViewById(R.id.textViewMedio3);
        medio3.setText(getAlineacion().Medio3.getNombreJugador());

        TextView delantero1 = (TextView) findViewById(R.id.textViewDelantero1);
        delantero1.setText(getAlineacion().Delantero1.getNombreJugador());
        TextView delantero2 = (TextView) findViewById(R.id.textViewDelantero2);
        delantero2.setText(getAlineacion().Delantero2.getNombreJugador());
        TextView delantero3 = (TextView) findViewById(R.id.textViewDelantero3);
        delantero3.setText(getAlineacion().Delantero3.getNombreJugador());
    }




}
