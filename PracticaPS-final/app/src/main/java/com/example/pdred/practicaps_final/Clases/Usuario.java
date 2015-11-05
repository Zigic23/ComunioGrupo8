/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pdred.practicaps_final.Clases;

/**
 *
 * @author Michel
 */
public class Usuario {
    String nombreUsuario;
    String nombreLiga;
    int dinero;
    int puntos;
    Equipo equipo;
    
    public Usuario (String nU, String nL,int c, int p, Equipo eQ){
        nombreUsuario = nU;
        nombreLiga = nL;
        dinero = c;
        puntos = p;
        equipo = eQ;
    }
    
    public String getNombreUsuario(){return nombreUsuario;}
    
    public void setNombreUsuario(String s){nombreUsuario = s;}

    public String getNombreLiga(){
        return nombreLiga;
    }
    
    public void setNombreLiga(String s){
        nombreLiga = s;
    }
    
    public int getPuntos(){
        return puntos;
    }
    
    public void setPuntos(int p){
        puntos = p;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
}
