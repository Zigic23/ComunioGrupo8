package com.example.pdred.practicaps_final;

import com.example.pdred.practicaps_final.Clases.Alineacion;
import com.example.pdred.practicaps_final.Clases.Equipo;
import com.example.pdred.practicaps_final.Clases.Jugador;
import com.example.pdred.practicaps_final.Clases.Usuario;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.pdred.practicaps_final.Clases.Equipo.parsearJugadores;
import static com.example.pdred.practicaps_final.Clases.Usuario.cargarUsuarios;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getAllLines;
import static com.example.pdred.practicaps_final.Utilidades.MetodosIO.getLine;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setComunidad;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setFuncionEquipo;
import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setInfoUsuario;

/**
 * Created by Michel on 30/10/2015.
 */
public class UsuarioEstatico {
    // Guarda durante la ejecuccion del programa el usuario que se le introduce en el LOGIN
    static Usuario currentUser;
    // Guarda la comunidad entre las actividades del REGISTRO
    static String currentComunidad;
    // Guarda explicitamente si la comunidad a la que te unes es nueva o no entre las actividades del REGISTRO
    static boolean comunidadNueva;
    // Guarda los usuarios de una comunidad al arracar la sesion para la actividad LIGA
    static  ArrayList<Usuario> usuariosLiga;
    // Guarda en ejecuccion la alineacion que tengas
    static Alineacion alineacion;

    // Inicializa variables estaticas en el LOGIN
    public static void iniciarSesion(String user) throws IOException {
        // Genera una URL válida para consultar los datos de un usuario
        String url = setInfoUsuario(user);
        // Carga la información del usuario haciendo llamadas a la base de datos
        String nEquipo = getLine(url, 0);
        String puntos = getLine(url, 1);
        String presupuesto = getLine(url, 2);
        String comunidad = getLine(url, 3);
        // Genra una nueva alineacion (HAY QUE ACTUALIZARLO PARA QUE SE CARGUE EN FUNCION DEL CAMPO JUEGA DE CADA JUGADOR)
        // Obtiene los jugadores del equipo del usuario (SIGUE EL EQUIPO POR DEFECTO)
        Equipo equipo = setEquipoPruebas(); //PRUEBAS
        String url2 = setFuncionEquipo(user);
        ArrayList<String> jugadores = getAllLines(url2);
        if (! jugadores.isEmpty()){equipo = new Equipo(nEquipo,0,jugadores.size(),parsearJugadores(jugadores));}
        // Parsea los tipos incompatibles
        int presupuestoP = Integer.parseInt(presupuesto);
        int puntosP = Integer.parseInt(puntos);
        // Cargar a tus compañeros de comunidad
        usuariosLiga = cargarComunidad(comunidad);
        // Crea un nuevo usuario en la aplicación a partir de esta información
        Usuario newUser = new Usuario(user,comunidad,presupuestoP,puntosP,equipo);
        setCurrentUser(newUser);
        // Genra una nueva alineacion (HAY QUE ACTUALIZARLO PARA QUE SE CARGUE EN FUNCION DEL CAMPO JUEGA DE CADA JUGADOR)
        alineacion = new Alineacion();
        // Cargamos nuestra alineacion
        for (Jugador jug : getCurrentUser().getEquipo().getListaJugadores()) {
            if (jug.getJuega() == 1) {alineacion.addJugador(jug);}
        }
    }


    public static Usuario getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(Usuario currentUser) {UsuarioEstatico.currentUser = currentUser;}



    public static String getCurrentComunidad() {
        return currentComunidad;
    }

    public static void setCurrentComunidad(String currentComunidad) {
        UsuarioEstatico.currentComunidad = currentComunidad;
    }

    public static boolean isComunidadNueva() {
        return comunidadNueva;
    }

    public static void setComunidadNueva(boolean comunidadNueva) {
        UsuarioEstatico.comunidadNueva = comunidadNueva;
    }

    public static ArrayList<Usuario> getUsuariosLiga() {
        return usuariosLiga;
    }

    public static Alineacion getAlineacion() {
        return alineacion;
    }

    // Carga los usuarios de la comunidad del usuario introducido en el LOGIN
    public static  ArrayList<Usuario> cargarComunidad(String comunidad) throws IOException {
        String url = setComunidad(comunidad);
        ArrayList<String> usuariosS = getAllLines(url);
        ArrayList<Usuario> usuarios = cargarUsuarios(usuariosS);
        //ordenar los usuarios por sus puntos (implementar este metodo en la clase usuario)
        return usuarios;
    }

    //A partir de aquí es unicamente para pruebas (ELIMINAR)

        // Genera un equipo de pruebas
        public static Equipo setEquipoPruebas(){
            Equipo newTeam = new Equipo("NewTeam",0,0,setJugadoresPruebas());
            newTeam.actualizarValorSuma();
            return newTeam;
        }

        // Genera los jugadores de pruebas
        public static ArrayList<Jugador> setJugadoresPruebas(){

            ArrayList<Jugador> nuevaPlantilla = new ArrayList<>();

            return nuevaPlantilla;
        }

}
