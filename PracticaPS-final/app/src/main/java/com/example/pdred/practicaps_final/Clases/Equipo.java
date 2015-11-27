/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pdred.practicaps_final.Clases;

import com.example.pdred.practicaps_final.R;

import java.io.UnsupportedEncodingException;
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
        int id = Integer.parseInt(jugadorItems[0].replace(" ", ""));
        String nombreJ = jugadorItems[1].replace(" ", "");
        String nombreJugador = null;
        try {
            nombreJugador= new String(nombreJ.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String posicion = jugadorItems[2].replace(" ", "");
        int precio = Integer.parseInt(jugadorItems[3].replace(" ", ""));
        int juega = Integer.parseInt(jugadorItems[4].replace(" ", ""));
        String owner = jugadorItems[5].replace(" ","");

        Jugador player = new Jugador(id, nombreJugador, posicion, precio, juega, owner);
        return player;
    }

    // GENERADOR DE EQUIPO ALEATORIO

    public static ArrayList<Jugador> generarEquipoAleatorio(ArrayList<Jugador> jugadoresLibres) {
        // La funcion recibe un array de jugadores, que seran los jugadores libres del mercado
        // Devolvera un equipo de 11 jugadores o en su defecto rellenara todas las posiciones que pueda
        ArrayList<Jugador> jugadoresgenerados = new ArrayList<>();
        int n = jugadoresLibres.size();
        boolean portero = false; //MAX 1
        int def = 0; //MAX 4
        int med = 0; //MAX 4
        int del = 0; //MAX 3
        int infinito = 0;
        while (((!portero) || (med != 4) || (def != 4) || (del != 3)) && (infinito != 1000)) {
            int nAleatorio = (int) Math.floor(Math.random() * n); //GENERA UN NUMERO ALEATORIO ENTRE 1 y EL MAXIMO DE JUGADORES
            if (!jugadoresgenerados.contains(jugadoresLibres.get(nAleatorio))) { //SI NO LO CONTIENE YA, LO INCLUYE, SI NO, LO DESCARTA
                if ((!portero) && ("POR".equals(jugadoresLibres.get(nAleatorio).getPosicion()))) {
                    jugadoresgenerados.add(jugadoresLibres.get(nAleatorio));
                    portero = true;
                } //SI EL JUGADOR ALEATORIO ES PORTERO, SE AÑADE Y SE IMPIDE METER MAS PORTEROS
                if ((def < 4) && ("DEF".equals(jugadoresLibres.get(nAleatorio).getPosicion()))) {
                    jugadoresgenerados.add(jugadoresLibres.get(nAleatorio));
                    def++;
                } //SI EL JUGADOR ES DEFENSA, SE AÑADE Y SE INCREMENTA EL NUMERO DE DEFENSAS
                if ((med < 4) && ("MED".equals(jugadoresLibres.get(nAleatorio).getPosicion()))) {
                    jugadoresgenerados.add(jugadoresLibres.get(nAleatorio));
                    med++;
                } //SI EL JUGADOR ES MEDIOS, SE AÑADE Y SE INCREMENTA EL NUMERO DE MEDIOS
                if ((del < 3) && ("DEL".equals(jugadoresLibres.get(nAleatorio).getPosicion()))) {
                    jugadoresgenerados.add(jugadoresLibres.get(nAleatorio));
                    del++;
                } //SI EL JUGADOR ES DELANTEROS, SE AÑADE Y SE INCREMENTA EL NUMERO DE DELANTEROS
            }
            infinito++;
        } //TENDREMOS UN 11 TITULAR

        int suplentes = 0; //MAX 3
        int infinito2 = 0;
        while ((suplentes != 2) && (infinito2 != 1000)) { // AÑADIMOS LOS SUPLENTES, NOS VALE CUALQUIERA
            int nAleatorioSup = (int) Math.floor(Math.random() * n);
            Jugador j = jugadoresLibres.get(nAleatorioSup);
            if (!jugadoresgenerados.contains(j)) { //SI NO LO CONTIENE YA, LO INCLUYE, SI NO, LO DESCARTA
                jugadoresgenerados.add(j);
                suplentes++;
            }
            infinito2++;
        }
        return jugadoresgenerados;
    }

    public static String getIDs(ArrayList<Jugador> equipoGenerado){
        String fichajes = "";
        Iterator<Jugador> iteradorJugadores = equipoGenerado.iterator();
        while(iteradorJugadores.hasNext()){
            Jugador jugador = iteradorJugadores.next();
            if (fichajes.equals("")){ fichajes = String.valueOf(jugador.getImagenId());
            }
            else{ fichajes = fichajes + ","+ jugador.getImagenId();}
        }
        return fichajes;
    }
}
