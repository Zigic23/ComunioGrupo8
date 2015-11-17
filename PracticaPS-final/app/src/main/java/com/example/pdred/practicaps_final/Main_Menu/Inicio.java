package com.example.pdred.practicaps_final.Main_Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pdred.practicaps_final.Equipo.MiEquipo;
import com.example.pdred.practicaps_final.Liga_Comunidad.MiLiga;
import com.example.pdred.practicaps_final.Login.LoginActivity;
import com.example.pdred.practicaps_final.Perfil.MiPerfil;
import com.example.pdred.practicaps_final.R;
import com.example.pdred.practicaps_final.Mercado.Mercado;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;


public class Inicio extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        TextView nombre = (TextView) findViewById(R.id.textView10);
        nombre.setText(getCurrentUser().getNombreUsuario() + " "+ String.valueOf(getCurrentUser().getPuntos())+" puntos");
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_perfil) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Inicio.this, MiPerfil.class);
            startActivity(nuevaActividad);
        } else if (id == R.id.mi_equipo) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Inicio.this, MiEquipo.class);
            startActivity(nuevaActividad);

        } else if (id == R.id.mi_liga) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Inicio.this,MiLiga.class);
            startActivity(nuevaActividad);
        } else if (id == R.id.mi_mercado) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Inicio.this, Mercado.class);
            startActivity(nuevaActividad);
        } else if (id == R.id.cerrar_sesion) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(Inicio.this, LoginActivity.class);
            startActivity(nuevaActividad);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
