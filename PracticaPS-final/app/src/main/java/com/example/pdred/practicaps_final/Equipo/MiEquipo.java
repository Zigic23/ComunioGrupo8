package com.example.pdred.practicaps_final.Equipo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.R;


import java.util.ArrayList;

import static com.example.pdred.practicaps_final.UsuarioEstatico.getCurrentUser;

public class MiEquipo extends Activity{

    static ArrayList<Jugador> listado;
    static int[] juegan;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listado = getCurrentUser().getEquipo().getListaJugadores();
        if (listado.isEmpty()) {
            setContentView(R.layout.noteam);
        } else {
            setContentView(R.layout.activity_equipo);
            setAll(listado);
        }

        Button botonAlineacion = (Button) findViewById(R.id.button10);

        botonAlineacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaActividad;
                nuevaActividad = new Intent(MiEquipo.this, AlineacionEquipo.class);
                startActivity(nuevaActividad);
            }
        });

        Button guardarAlineacion = (Button) findViewById(R.id.button3);

        guardarAlineacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Alineacion Guardada", Toast.LENGTH_SHORT);toast1.show();
        }
        });
    }

    public void setAll (ArrayList<Jugador> listac) {
        RecyclerView lista = (RecyclerView)findViewById(R.id.listView1);
        lManager = new LinearLayoutManager(this);
        lista.setLayoutManager(lManager);
        adapter = new RecyclerAdapter(listac, this);
        lista.setAdapter(adapter);
        /*lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_jug, listac) {
            @Override
            public void onEntrada(final Object entrada, View view) {
                int idImagen = getImagen(((Jugador) entrada).getImagenId());

                TextView texto_nombre = (TextView) view.findViewById(R.id.txtName);
                texto_nombre.setText(((Jugador) entrada).getNombreJugador());

                TextView texto_posicion = (TextView) view.findViewById(R.id.txtPos);
                texto_posicion.setText(((Jugador) entrada).getPosicion());

                ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView2);
                imagen_entrada.setImageResource(idImagen);

                ImageButton botonAdd = (ImageButton) view.findViewById(R.id.imageButton);
                botonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Jugador) entrada).getJuega() == 0) {
                            boolean introducido = getAlineacion().addJugador(((Jugador) entrada));
                            ((Jugador) entrada).setJuega(1);
                            if (introducido) {
                                Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Convocado", Toast.LENGTH_SHORT);
                                toast1.show();
                            } else {
                                Toast toast2 = Toast.makeText(getApplicationContext(), "Retire un jugador para insertar", Toast.LENGTH_SHORT);
                                toast2.show();
                            }
                        } else {
                            Toast toast3 = Toast.makeText(getApplicationContext(), "El jugador ya estaba convocado", Toast.LENGTH_SHORT);
                            toast3.show();
                        }
                    }
                });


                ImageButton botonDelete = (ImageButton) view.findViewById(R.id.imageButton2);
                botonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean eliminado = getAlineacion().removeJugador(((Jugador) entrada));
                        ((Jugador) entrada).setJuega(0);
                        if(eliminado){Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Desconvocado", Toast.LENGTH_SHORT);toast1.show();}
                        else{Toast toast1 = Toast.makeText(getApplicationContext(),"No estaba convocado", Toast.LENGTH_SHORT);toast1.show();}
                        ((Jugador) entrada).setJuega(0);
                    }
                });
                */

                // SWITCH

                /**Switch mySwitch = (Switch) view.findViewById(R.id.switch1);
                //if (((Jugador) entrada).getJuega() == 1) {mySwitch.setChecked(true);}
                //attach a listener to check for changes in state
                mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked){// SI SE CHECKEA
                            boolean introducido = getAlineacion().addJugador(((Jugador) entrada));
                            ((Jugador) entrada).setJuega(1);
                            if (introducido){
                                Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Convocado", Toast.LENGTH_SHORT);toast1.show();}
                            else {Toast toast2 = Toast.makeText(getApplicationContext(),"Retire un jugador para insertar", Toast.LENGTH_SHORT);toast2.show();}
                        }else{ // SI NO SE CHECKEA
                            boolean eliminado = getAlineacion().removeJugador(((Jugador) entrada));
                            ((Jugador) entrada).setJuega(0);
                            if(eliminado){Toast toast1 = Toast.makeText(getApplicationContext(), ((Jugador) entrada).getNombreJugador() + " Desconvocado", Toast.LENGTH_SHORT);toast1.show();}
                            else{Toast toast1 = Toast.makeText(getApplicationContext(),"No estaba convocado", Toast.LENGTH_SHORT);toast1.show();}
                            ((Jugador) entrada).setJuega(0);
                        }

                    }
                });

            }
        }); */
    }
}
