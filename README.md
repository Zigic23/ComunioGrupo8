# ComunioGrupo8

API en PHP
------------------------

Para ver el equipo de un usuario
Lo que va ente <***> lo debes de cambiar para funcionalidad

#### Equipo
```
"http://comunio.garcy.es/?funcion=equipo&usuario=<Usuario>"
```
#### validarComunidad
Nota: comunidades sin ¡ Espacios ! gracias :))
```
"http://comunio.garcy.es/?funcion=validarComunidad&comunidad=<Comun>"
```
#### RegsitroC (La comunidad será creada, la aplicación comprueba todo.)
```
"http://comunio.garcy.es/?funcion=registroC&comunidad=<Comunidad>&usuario=<Usuario>&contraseña=<Contrasena>&equipo=<Equipo>"
```
#### registroPorComunidad (La comunidad ya esta creada)
```
"http://comunio.garcy.es/?funcion= registroPorComunidad&comunidad=<Comunidad>&usuario=<Usuario>&contraseña=<Contrasena>&equipo=<Equipo>"
```
#### Registro (Con numero de la comunidad.)
```
"http://comunio.garcy.es/?funcion=registro&comunidad=<NumeroComunidad>&usuario=<Usuario>&contraseña=<Cont>&equipo=<Equipo>"
```
#### RegistroAmigo (Con el amigo)
```
"http://comunio.garcy.es/?funcion=registroAmigo&amigo=<Amigo>&usuario=<Usuario>&contraseña=<Contraseña>&equipo=<Equipo>"
```
#### Mercado
```
http://comunio.garcy.es/?funcion=mercado&comunidad=<Nombre Comunidad>
```
#### jugadoresComunidad
```
"comunio.garcy.es/?funcion=jugadoresComunidad&comunidad=<NombreComunidad>"
```
#### JugadoresLibre (!!! Nota si la comunidad esta vacía o no hay nada te da todos los jugadores.....)
```
"comunio.garcy.es/?funcion=jugadoresLibres&comunidad=<Comunidad>"
```
#### Fichar
```
http://comunio.garcy.es/?funcion=fichar&usuario=<Usuario>&equipo=<,,;>
```
#### ActualizarOnce
```
http://comunio.garcy.es/?funcion=actualizarOnce&usuario=<Usuario>&equipo=<,,*>
```
#### Presupuesto
```
http://comunio.garcy.es/?funcion=presupuesto&usuario=<Usuario>&presupuesto=<Nuevo Presupuesto>
```
#### Vender
```
http://comunio.garcy.es/?funcion=vender&usuario=<Uusario>&jugador=<IDJugador>
```
#### Pujar
```
http://comunio.garcy.es/?funcion=pujar&usuario=<Usuario>&jugador=<IdJugador>&precio=<NuevoPrecio>
```
#### reventarMercado
```
http://comunio.garcy.es/?funcion=reventarMercado
```
