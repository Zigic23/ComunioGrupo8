/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pdred.practicaps_final.Clases;

import com.example.pdred.practicaps_final.R;

/**
 *
 * @author Michel
 */
public class Jugador {
    int imagenId;
    String nombreJugador;
    String posicion;
    int precio;
    int puntos;

    public Jugador (int iid, String nomjug, String pos, int prec,int puntos){
        imagenId = iid;
        nombreJugador = nomjug;
        posicion = pos;
        precio = prec;
        puntos = puntos;
    }

    public int getImagenId (){
        return imagenId;
    }

    public String getNombreJugador(){
        return nombreJugador;
    }

    public String getPosicion (){
        return posicion;
    }

    public int getPrecio () {
        return precio;
    }
    
    public void setPrecio (int p){
        precio = p;
    }

    public void setImagenId(int n) {
        int id = R.drawable.cristiano;
        switch(n){
            case 6: id = R.drawable.jugador6; break;
            case 10: id = R.drawable.jugador10; break;
            case 17: id = R.drawable.jugador17; break;
            case 18: id = R.drawable.jugador18; break;
            case 19: id = R.drawable.jugador19; break;
            case 67: id = R.drawable.jugador67; break;
            default: id = R.drawable.avatar;
        }
        this.imagenId = id;
    }

}
