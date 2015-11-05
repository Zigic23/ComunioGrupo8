package com.example.pdred.practicaps_final;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import static com.example.pdred.practicaps_final.UtilidadesLogin.getHtml;
import static com.example.pdred.practicaps_final.UtilidadesLogin.setUsuarioContraseña;

public class LoginActivity extends AppCompatActivity {
    // <uses-permission android:name="android.permission.INTERNET" />
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button botonLogin = (Button) findViewById(R.id.buttonLogIn);
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = ((EditText) findViewById(R.id.editText)).getText().toString();
                String password = ((EditText) findViewById(R.id.editText2)).getText().toString();
                String url;
                // Obtenemos la URL con la consulta
                //url = setUsuarioContraseña(usuario,password);
                // Obtenemos la respuesta HTML de la URL
                String respuesta = "ERROR";
                //try {respuesta = getHtml(url);} catch (IOException e) {e.printStackTrace();
                if (respuesta == "OK") {
                    // Enlaza con la siguiente actividad si el login es correcto
                    //Intent nuevaActividad = new Intent(LoginActivity.this,ActividadPrueba.class);
                    //startActivity(nuevaActividad);
                } else {
                    // Mensaje al no introducir datos validos
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña no validos", Toast.LENGTH_SHORT).show();
                    Intent nuevaActividad = new Intent (LoginActivity.this,EquipoActivity.class);
                    startActivity(nuevaActividad);
                }
            }
        });
    }
}