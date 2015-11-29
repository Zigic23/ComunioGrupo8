/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pdred.practicaps_final.Clases;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getLine;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setInfoUsuario;

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

    public String getNombreLiga(){
        return nombreLiga;
    }
    
    public int getPuntos(){
        return puntos;
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

    public static ArrayList<Usuario> cargarUsuarios(ArrayList<String> usuariosS)throws IOException{
        ArrayList<Usuario> usuarios = new ArrayList<>();
        for (String usuario : usuariosS) {
            if (!usuario.equals("System"))
            {usuarios.add(cargarUsuario(usuario));}
        }
        return usuarios;
    }

    public static Usuario cargarUsuario (String user)throws IOException {
        // Genera una URL válida para consultar los datos de un usuario
        String url = setInfoUsuario(user);
        // Carga la información del usuario haciendo llamadas a la base de datos
        String nEquipo = getLine(url, 0);
        String puntos = getLine(url, 1);
        String presupuesto = getLine(url, 2);
        String comunidad = getLine(url, 3);
        // Parsea los tipos incompatibles
        int presupuestoP = Integer.parseInt(presupuesto);
        int puntosP = Integer.parseInt(puntos);
        //Creamos un nuevo usuario y lo devolvemos
        Usuario newUser = new Usuario(user,comunidad,presupuestoP,puntosP,null);
        return newUser;
    }
}
