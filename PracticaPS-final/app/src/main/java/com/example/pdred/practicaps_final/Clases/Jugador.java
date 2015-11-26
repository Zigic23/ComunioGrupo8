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
    String owner;
    int juega;

    public Jugador (int iid, String nomjug, String pos, int prec, int j, String o){
        imagenId = iid;
        nombreJugador = nomjug;
        posicion = pos;
        precio = prec;
        owner = o;
        juega = j;
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

    public static int getImagen(int n) {
        int id;
        switch(n){
            default: id = R.drawable.avatar;
        }
        return id;
    }

    public void setJuega(int juega) {
        this.juega = juega;
    }

    public int getJuega() {
        return juega;
    }

    public String getOwner() {
        return owner;
    }
}
