/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pdred.practicaps_final.Clases;

import com.example.pdred.practicaps_final.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Michel
 */
public class Equipo {
    String nombreEquipo;
    int valorSuma;
    int numJugadores;
    ArrayList<Jugador> listaJugadores;
    
    public Equipo (String n, int v, int nj, ArrayList<Jugador> l){
        nombreEquipo = n;
        valorSuma = v;
        numJugadores = nj;
        listaJugadores = l;
    }

    public String getNombreEquipo(){
        return nombreEquipo;
    }
    
    public void setNombreEquipo(String n){
        nombreEquipo = n;
    }
    
    public int getValorSuma(){
        return valorSuma;
    }
    
    public void setValorSuma(int v){
        valorSuma = v;
    }
    
    public int getNumJugadores(){
        return numJugadores;
    }
    
    public void setNumJugadores(int n){
        numJugadores = n;
    }
    
    public ArrayList<Jugador> getListaJugadores(){
        return listaJugadores;
    }
    
    public void setListaJugadores(ArrayList<Jugador> l){
        listaJugadores = l;
    }

    public void actualizarValorSuma(){
        int suma = 0;
        Iterator<Jugador> iteradorJugadores = listaJugadores.iterator();
        while(iteradorJugadores.hasNext()){
            Jugador jugador = iteradorJugadores.next();
            suma = suma + jugador.getPrecio();
            setValorSuma(suma);
        }
    }


    // METODOS DE PARSER DE JUGADORES

    public static ArrayList<Jugador> parsearJugadores(ArrayList<String> jugadores){
        ArrayList<Jugador> nuevaPlantilla = new ArrayList<>();
        for (String jugador : jugadores) {
            nuevaPlantilla.add(parsearJugador(jugador));
        }
        return nuevaPlantilla;
    }

    public static Jugador parsearJugador(String jugador){
        String[] jugadorItems = jugador.split(",");
        int id = Integer.parseInt(jugadorItems[0].replace(" ",""));
        String nombreJugador = jugadorItems[1];
        String posicion = jugadorItems[2];
        int precio = Integer.parseInt(jugadorItems[3].replace(" ",""));
        int puntos = Integer.parseInt(jugadorItems[4].replace(" ",""));

        Jugador player = new Jugador(0,nombreJugador,posicion,precio,puntos);
        player.setImagenId(id);
        return player;
    }
}
