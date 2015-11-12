package com.example.pdred.practicaps_final;

import com.example.pdred.practicaps_final.Clases.Equipo;
import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Clases.Usuario;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.pdred.practicaps_final.Clases.Equipo.parsearJugadores;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.setFuncionEquipo;
import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.setInfoUsuario;
import static com.example.pdred.practicaps_final.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.MetodosIO.getLine;

/**
 * Created by Michel on 30/10/2015.
 */
public class UsuarioEstatico {
    static Usuario currentUser;
    static String currentComunidad;
    static boolean comunidadNueva;

    public static boolean isComunidadNueva() {
        return comunidadNueva;
    }

    public static void setComunidadNueva(boolean comunidadNueva) {
        UsuarioEstatico.comunidadNueva = comunidadNueva;
    }

    public static String getCurrentComunidad() {
        return currentComunidad;
    }

    public static void setCurrentComunidad(String currentComunidad) {
        UsuarioEstatico.currentComunidad = currentComunidad;
    }


    public static Usuario getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(Usuario currentUser) {
        UsuarioEstatico.currentUser = currentUser;
    }

    public static void iniciarSesion(String user) throws IOException {
        // Genera una URL válida para consultar los daos de un usuario
        String url = setInfoUsuario(user);
        // Carga la información del usuario haciendo llamadas a la base de datos
        String nEquipo = getLine(url, 0);
        String puntos = getLine(url, 1);
        String presupuesto = getLine(url, 2);
        String comunidad = getLine(url, 3);
        // Obtiene los jugadores del equipo del usuario
        Equipo equipo = setEquipoPruebas(); //PRUEBAS
        String url2 = setFuncionEquipo(user);
        ArrayList<String> jugadores = getAllLines(url2);
            if (! jugadores.isEmpty()){equipo = new Equipo(nEquipo,0,jugadores.size(),parsearJugadores(jugadores));}
        // Parsea los tipos incompatibles
        int presupuestoP = Integer.parseInt(presupuesto);
        int puntosP = Integer.parseInt(puntos);
        // Crea un nuevo usuario en la aplicación a partir de esta información
        Usuario newUser = new Usuario(user,comunidad,presupuestoP,puntosP,equipo);
        setCurrentUser(newUser);
    }

    //A partir de aquí es unicamente para pruebas

    public static Equipo setEquipoPruebas(){
        Equipo newTeam = new Equipo("NewTeam",0,0,setJugadoresPruebas());
        newTeam.actualizarValorSuma();
        return newTeam;
    }

    public static ArrayList<Jugador> setJugadoresPruebas(){
        Jugador jugador1 = new Jugador(R.drawable.neymar,"Neymar", "Delantero", 6000000,0);
        Jugador jugador2 = new Jugador(R.drawable.nolito,"Nolito", "Delantero", 2000000,0);
        Jugador jugador3 = new Jugador(R.drawable.cristiano,"Cristiano Ronaldo", "Delantero", 8000000,0);
        Jugador jugador4 = new Jugador(R.drawable.reyes,"Reyes", "Medio", 2000000,0);
        Jugador jugador5 = new Jugador(R.drawable.koke,"Koke", "Medio", 4000000,0);
        Jugador jugador6 = new Jugador(R.drawable.tonikross,"Toni Kroos", "Medio", 5000000,0);
        Jugador jugador7 = new Jugador(R.drawable.iconcono,"Arbeloa", "Defensa", 999999999,0);
        Jugador jugador8 = new Jugador(R.drawable.pique,"Pique", "Defensa", 3000000,0);
        Jugador jugador9 = new Jugador(R.drawable.demarcos,"De Marcos", "Defensa", 2000000,0);
        Jugador jugador10 = new Jugador(R.drawable.rami,"Rami", "Defensa", 1000000,0);
        Jugador jugador11 = new Jugador(R.drawable.keylornavas,"Keylor Navas", "Portero", 2000000,0);

        ArrayList<Jugador> nuevaPlantilla = new ArrayList<>();
        nuevaPlantilla.add(jugador1);
        nuevaPlantilla.add(jugador2);
        nuevaPlantilla.add(jugador3);
        nuevaPlantilla.add(jugador4);
        nuevaPlantilla.add(jugador5);
        nuevaPlantilla.add(jugador6);
        nuevaPlantilla.add(jugador7);
        nuevaPlantilla.add(jugador8);
        nuevaPlantilla.add(jugador9);
        nuevaPlantilla.add(jugador10);
        nuevaPlantilla.add(jugador11);

        return nuevaPlantilla;
    }
}
