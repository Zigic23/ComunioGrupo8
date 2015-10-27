package com.example.pdred.practicaps_final;

/**
 * Created by Zigic on 26/10/2015.
 */
public class MostrarJugador {
    int imagenId;
    String nombreJugador;
    String posicion;
    int precio;

    public MostrarJugador (int iid, String nomjug, String pos, int prec){
        this.imagenId = iid;
        this.nombreJugador = nomjug;
        this.posicion = pos;
        this.precio = prec;
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
}
