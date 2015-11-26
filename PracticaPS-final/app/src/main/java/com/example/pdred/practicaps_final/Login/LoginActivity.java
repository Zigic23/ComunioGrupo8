package com.example.pdred.practicaps_final.Login;
    import android.app.Activity;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.content.Intent;
    import android.view.KeyEvent;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
    import android.app.ProgressDialog;

    import com.example.pdred.practicaps_final.Main_Menu.Inicio;
    import com.example.pdred.practicaps_final.R;
    import com.example.pdred.practicaps_final.Registro.registroComunidad;

    import java.io.IOException;

    import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.checklogindata;
    import static com.example.pdred.practicaps_final.Login.UtilidadesLogin.getHtml;
    import static com.example.pdred.practicaps_final.UsuarioEstatico.iniciarSesion;
    import static com.example.pdred.practicaps_final.Utilidades.UtilidadesURL.setUsuarioContrasena;


public class LoginActivity extends Activity {
    public static boolean isRecursionEnable = false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button botonLogin = (Button) findViewById(R.id.button);
        Button botonRegistro = (Button) findViewById(R.id.button2);

        // Recoge el boton del Layout de registro y le proporciona funcionalidad
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nuevaActividad;
                nuevaActividad = new Intent(LoginActivity.this, registroComunidad.class);
                startActivity(nuevaActividad);
            }
        });

        // Recoge el boton del Layout de login y le proporciona funcionalidad
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // recoge los datos introducidos por el usuario
                String usuario = ((EditText) findViewById(R.id.editText)).getText().toString();
                String password = ((EditText) findViewById(R.id.editText2)).getText().toString();
                // Crea un background, pasandole los datos que introdujo el usuario
                if (checklogindata(usuario,password)){ // Solo si estos datos son correctos
                new asynclogin().execute(usuario, password);} else err_login();
            }
        });
    }
    public void err_login(){ //Muestra un mensaje de error
        Toast toast1 = Toast.makeText(getApplicationContext(),"Error. Nombre de usuario o contraseña no valido",Toast.LENGTH_SHORT);
        toast1.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent nuevaActividad;
            nuevaActividad = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(nuevaActividad);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class asynclogin extends AsyncTask <String,String,String>{
        //Declaramos las variables que vamos a recoger
        String user, pass;
        ProgressDialog pDialog;
        // PRE-EJECUCION: esto se ejecutara ANTES del background, en este caso muestra un dialogo de proceso y un mensaje que se puede editar
        protected void onPreExecute(){
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Autenticando ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        // EJECUCIÓN: Aqui va el codigo que se realizara en background
        @Override
        protected String doInBackground(String... params) {
            //Recoge los argumentos que le hemos pasado en la clase principal
            user= params[0];
            pass = params[1];
            String respuesta = "ERROR1";
            //Construye una url valida para hacer la consulta (Ver UtilidadesLogin)
            String url = setUsuarioContrasena(user, pass);
            try {
                //Realiza la peticion al PHP, si es correcta devolvera el contenido del HTML
                respuesta = getHtml(url);
                // Creamos una sesion, creando un usuario estatico a partir del nombre de usuario y cargando su informacion
                if (!respuesta.equals("ERROR")){iniciarSesion(user);}
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Devuelve como argumento el HTML o "ERROR1" (Devolvera "ERROR" si el usuario o contraseña son incorrectos)
            return respuesta;
            }
        //POST-EJECUCIÓN: Recoge lo que devuelve "doInBackground" y actua en funcion del resultado
        protected void onPostExecute (String result){
            //Si el login es correcto, "result" sera "OK" y pasara a la siguiente activity
            if (result.equals("OK")){
                // Avanzamos al main
                Intent nuevaActividad;
                nuevaActividad = new Intent(LoginActivity.this, Inicio.class);
                startActivity(nuevaActividad);
            } else {err_login();} //En caso contrario sacara un error por pantalla
            pDialog.hide();
        }
    }
}
