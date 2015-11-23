package com.example.pdred.practicaps_final.Clases;

import com.example.pdred.practicaps_final.R;

/**
 * Created by Michel on 16/11/2015.
 */
public class Alineacion {
    public Jugador Portero;
    public Jugador Defensa1;
    public Jugador Defensa2;
    public Jugador Defensa3;
    public Jugador Defensa4;
    public Jugador Medio1;
    public Jugador Medio2;
    public Jugador Medio3;
    public Jugador Delantero1;
    public Jugador Delantero2;
    public Jugador Delantero3;
    // Varlor por defecto para que siempre haya un jugador en la alineacion
    static public Jugador defecto = new Jugador(R.drawable.avatar,"(Nadie)","NO", 0,1, "");

    public Alineacion(){
        // Al crear una alineacion, genera todas sus posiciones por defecto
        Portero = defecto;
        Defensa1 = defecto;
        Defensa2 = defecto;
        Defensa3 = defecto;
        Defensa4 = defecto;
        Medio1 = defecto;
        Medio2 = defecto;
        Medio3 = defecto;
        Delantero1 = defecto;
        Delantero2 = defecto;
        Delantero3 = defecto;
    }


    // Añadir jugador recibe un jugador del equipo del usuario e intenta introducirlo en la alineacion
    public boolean addJugador(Jugador jug){
        String nombrerDefecto = defecto.getNombreJugador();
        String pos = jug.getPosicion();
        boolean added = false;
        // Filtra por su posicion
        // Recorre cada posicion en el 11 buscando un sitio libre (ocupado por un jugador por defecto)
        // Si lo encuentra, lo sustituye y sale del bucle (added a TRUE)
        // En caso contrario, no hace nada (added a FALSE)
        switch(pos){
            case "POR": if (Portero.getNombreJugador().equals(nombrerDefecto)){Portero = jug; added = true; break;} break;

            case "DEF": if (Defensa1.getNombreJugador().equals(nombrerDefecto)){Defensa1 = jug;added = true; break;}
                        else {if (Defensa2.getNombreJugador().equals(nombrerDefecto)){Defensa2 = jug;added = true; break;}
                            else if (Defensa3.getNombreJugador().equals(nombrerDefecto)){Defensa3 = jug;added = true; break;}
                                else if (Defensa4.getNombreJugador().equals(nombrerDefecto)){Defensa4 = jug;added = true; break;}}break;

            case "MED": if (Medio1.getNombreJugador().equals(nombrerDefecto)){Medio1 = jug;added = true; break;}
                        else {if (Medio2.getNombreJugador().equals(nombrerDefecto)){Medio2 = jug;added = true; break;}
                            else if (Medio3.getNombreJugador().equals(nombrerDefecto)){Medio3 = jug;added = true; break;}}break;

            case "DEL": if (Delantero1.getNombreJugador().equals(nombrerDefecto)){Delantero1 = jug;added = true; break;}
                        else {if (Delantero2.getNombreJugador().equals(nombrerDefecto)){Delantero2 = jug;added = true; break;}
                            else if (Delantero3.getNombreJugador().equals(nombrerDefecto)){Delantero3 = jug;added = true; break;}}break;
        }
        return added;
    }

    public boolean removeJugador(Jugador jug){
        String pos = jug.getPosicion();
        String nom = jug.getNombreJugador();
        boolean added = false;
        switch(pos){
            case "POR": if (Portero.getNombreJugador().equals(nom)){Portero = defecto; added = true;} break;

            case "DEF": if (Defensa1.getNombreJugador().equals(nom)){Defensa1 = defecto;added = true;}
            else {if (Defensa2.getNombreJugador().equals(nom)){Defensa2 = defecto;added = true;}
            else if (Defensa3.getNombreJugador().equals(nom)){Defensa3 = defecto;added = true;}
            else if (Defensa4.getNombreJugador().equals(nom)){Defensa4 = defecto;added = true;}}break;

            case "MED": if (Medio1.getNombreJugador().equals(nom)){Medio1 = defecto;added = true;}
            else {if (Medio2.getNombreJugador().equals(nom)){Medio2 = defecto;added = true;}
            else if (Medio3.getNombreJugador().equals(nom)){Medio3 = defecto;added = true;}}break;

            case "DEL": if (Delantero1.getNombreJugador().equals(nom)){Delantero1 = defecto;added = true;}
            else {if (Delantero2.getNombreJugador().equals(nom)){Delantero2 = defecto;added = true;}
            else if (Delantero3.getNombreJugador().equals(nom)){Delantero3 = defecto;added = true;}}break;
        }
        return added;
    }

    public String getIDs (){
        String p= String.valueOf(Portero.getImagenId());
        String d1= String.valueOf(Defensa1.getImagenId());
        String d2= String.valueOf(Defensa2.getImagenId());
        String d3= String.valueOf(Defensa3.getImagenId());
        String d4= String.valueOf(Defensa4.getImagenId());
        String m1= String.valueOf(Medio1.getImagenId());
        String m2= String.valueOf(Medio1.getImagenId());
        String m3= String.valueOf(Medio1.getImagenId());
        String a1= String.valueOf(Delantero1.getImagenId());
        String a2= String.valueOf(Delantero2.getImagenId());
        String a3= String.valueOf(Delantero3.getImagenId());

        String ids = p+","+d1+","+d2+","+d3+","+d4+","+m1+","+m2+","+m3+","+a1+","+a2+","+a3;

        return ids;
    }
}
