package com.example.pdred.practicaps_final.Perfil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pdred.practicaps_final.R;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;

public class MiPerfil extends AppCompatActivity {
    // Esta actividad muestra los datos del usuario actual
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        TextView nombreUsuario = (TextView) findViewById(R.id.textViewUser);
        nombreUsuario.setText("   Usuario: " + getCurrentUser().getNombreUsuario());

        TextView nombreEquipo = (TextView) findViewById(R.id.textViewTeam);
        nombreEquipo.setText("   Equipo: " + getCurrentUser().getEquipo().getNombreEquipo());

        TextView nombreLiga = (TextView) findViewById(R.id.textViewLiga);
        nombreLiga.setText("   Liga: "  + getCurrentUser().getNombreLiga());

        TextView puntos = (TextView) findViewById(R.id.textViewPoints);
        puntos.setText("   "+String.valueOf(getCurrentUser().getPuntos())+" puntos");

        TextView presupuesto = (TextView) findViewById(R.id.textViewPresupuesto);
        presupuesto.setText("   Presupuesto: "+String.valueOf(getCurrentUser().getDinero())+ " €");

    }

}
