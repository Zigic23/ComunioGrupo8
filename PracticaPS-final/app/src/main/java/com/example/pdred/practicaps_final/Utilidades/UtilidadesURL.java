package com.example.pdred.practicaps_final.Utilidades;

/**
 * Created by Michel on 11/11/2015.
 */
public class UtilidadesURL {

    // GENERADOR DE URL's PARA EL LOGIN

    public static String setUsuarioContrasena(String usuario, String password) {
        return "http://comunio.garcy.es/?funcion=login&usuario=" + usuario + "&contrase%C3%B1a=" + password;
    }

    public static String setInfoUsuario(String user){
        return "http://comunio.garcy.es/?funcion=info&usuario=" + user;
    }

    public static String setFuncionEquipo(String user){
        return "http://comunio.garcy.es/?funcion=equipo&usuario=" + user;
    }

    // GENERADOR DE URL's PARA EL REGISTRO

    public static String setValidarUsuario(String user){
        return "http://comunio.garcy.es/?funcion=validarUsuario&usuario="+user;
    }

    public static String setExisteComunidad(String comunidad){
        return "http://comunio.garcy.es/?funcion=validarComunidad&comunidad="+comunidad;
    }

    public static String setRegistroComunidad(String comunidad, String user, String pass, String equipo){
        String comunidad2 =comunidad.replace(' ','_');
        return "http://comunio.garcy.es/?funcion=registroPorComunidad&comunidad="+comunidad2+"&usuario="+user+"&contrase%C3%B1a="+pass+"&equipo="+equipo;
    }

    public static String setRegistroNuevaComunidad(String comunidad,String user, String pass, String equipo){
        String comunidad2 =comunidad.replace(' ','_');
        return "http://comunio.garcy.es/?funcion=registroC&comunidad="+comunidad2+"+&usuario="+user+"&contrase%C3%B1a="+pass+"&equipo="+equipo;
    }

    public static String setRegistroEquipo(String comunidad){
        String comunidad2 =comunidad.replace(' ','_');
        return  "http://comunio.garcy.es/?funcion=jugadoresLibres&comunidad="+comunidad2;
    }

    // GENERADOR DE URL's PARA EL MERCADO

    public static String setMercadoComunidad(String comunidad){
        return "http://comunio.garcy.es/?funcion=mercado&comunidad=" + comunidad;
    }

    public static String setFichar(String user, String fichajes){
        return "http://comunio.garcy.es/?funcion=fichar&usuario="+user+"&equipo="+fichajes;
    }

    // GENERADOR DE URL's PARA LA COMUNIDAD

    public static String setComunidad(String comunidad){
        return "http://comunio.garcy.es/?funcion=jugadoresComunidad&comunidad="+comunidad;
    }

    // GENERADOR DE URL's PARA EL EQUIPO

    public static String setAlineacion(String user, String alineacion){
        return "http://comunio.garcy.es/?funcion=actualizarOnce&usuario="+user+"&equipo="+alineacion;
    }

    // GENERADOR DE URL's PARA FICHAJES/VENTAS

    public static String setPresupuesto(String user, String presupuesto){
        return "http://comunio.garcy.es/?funcion=presupuesto&usuario="+user+"&presupuesto="+presupuesto;
    }

    public static String setVender(String user, String id){
        return "http://comunio.garcy.es/?funcion=vender&usuario="+user+"&jugador="+id;
    }

    public static String setPujar(String user, String idjugador, String nuevoPrecio){
        return "http://comunio.garcy.es/?funcion=pujar&usuario="+user+"&jugador="+idjugador+"&precio="+nuevoPrecio;
    }
}
