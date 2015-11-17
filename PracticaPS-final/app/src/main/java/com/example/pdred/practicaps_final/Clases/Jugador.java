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
    
    public void setPrecio (int p){
        precio = p;
    }

    public static int getImagen(int n) {
        int id;
        switch(n){
            case 6: id = R.drawable.jugador6; break;
            case 10: id = R.drawable.jugador10; break;
            case 17: id = R.drawable.jugador17; break;
            case 18: id = R.drawable.jugador18; break;
            case 19: id = R.drawable.jugador19; break;
            case 22: id = R.drawable.jugador22; break;
            case 23: id = R.drawable.jugador23; break;
            case 24: id = R.drawable.jugador24; break;
            case 25: id = R.drawable.jugador25; break;
            case 26: id = R.drawable.jugador26; break;
            case 27: id = R.drawable.jugador27; break;
            case 28: id = R.drawable.jugador28; break;
            case 29: id = R.drawable.jugador29; break;
            case 30: id = R.drawable.jugador30; break;
            case 31: id = R.drawable.jugador31; break;
            case 32: id = R.drawable.jugador32; break;
            case 33: id = R.drawable.jugador33; break;
            case 34: id = R.drawable.jugador34; break;
            case 35: id = R.drawable.jugador35; break;
            case 36: id = R.drawable.jugador36; break;
            case 37: id = R.drawable.jugador37; break;
            case 38: id = R.drawable.jugador38; break;
            case 39: id = R.drawable.jugador39; break;
            case 40: id = R.drawable.jugador40; break;
            case 41: id = R.drawable.jugador41; break;
            case 42: id = R.drawable.jugador42; break;
            case 43: id = R.drawable.jugador43; break;
            case 44: id = R.drawable.jugador44; break;
            case 45: id = R.drawable.jugador45; break;
            case 46: id = R.drawable.jugador46; break;
            case 47: id = R.drawable.jugador47; break;
            case 48: id = R.drawable.jugador48; break;
            case 49: id = R.drawable.jugador49; break;
            case 50: id = R.drawable.jugador50; break;
            case 51: id = R.drawable.jugador51; break;
            case 52: id = R.drawable.jugador52; break;
            case 53: id = R.drawable.jugador53; break;
            case 54: id = R.drawable.jugador54; break;
            case 55: id = R.drawable.jugador55; break;
            case 56: id = R.drawable.jugador56; break;
            case 57: id = R.drawable.jugador57; break;
            case 58: id = R.drawable.jugador58; break;
            case 59: id = R.drawable.jugador59; break;
            case 60: id = R.drawable.jugador60; break;
            case 61: id = R.drawable.jugador61; break;
            case 62: id = R.drawable.jugador62; break;
            case 63: id = R.drawable.jugador63; break;
            case 64: id = R.drawable.jugador64; break;
            case 65: id = R.drawable.jugador65; break;
            case 66: id = R.drawable.jugador66; break;
            case 67: id = R.drawable.jugador67; break;
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
}
