package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home_personas extends AppCompatActivity {

    private static String correoUser;
    private Button btnCasos;
    private ImageButton logout;
    private String urlCasos,urlPrevencion,urlAislamiento, urlAtencion, urlDrecreto;

    public static void usuarioUtilizandoApp(String correo) {
        correoUser=correo;
        Log.d("desde personas: ","llego el correo "+correo);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_personas);
        urlCasos = "https://coronaviruscolombia.gov.co/";
        urlPrevencion = "https://coronaviruscolombia.gov.co/Covid19/protocolos.html";
        urlAislamiento = "https://coronaviruscolombia.gov.co/Covid19/aislamiento-saludable.html";
        urlAtencion = "https://coronaviruscolombia.gov.co/Covid19/lineas-de-atencion.html";
        urlDrecreto = "https://coronaviruscolombia.gov.co/Covid19/datos-cuarentena.html";
        logout = (ImageButton) findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Home_personas.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Home_personas.this, MainActivity.class));
            }
        });
    }

    //Botones interacciones inferior
    public void reportarSintomas(View view){
        Intent intent = new Intent(this, Formulario.class);
        startActivity(intent);
    }
    public void contactoPersonas(View view){
        Intent intent = new Intent(this, Contactos_Personas.class);
        startActivity(intent);
    }
    public void recorrido(View view){
        Intent intent = new Intent(this, Recorridos_Personas.class);
        startActivity(intent);
    }
    public void result(View view){
        Intent intent = new Intent(this, Resultados.class);
        startActivity(intent);
    }

    //NOTICIAS COVID
    public void urlCasos(View view){
        Uri uri = Uri.parse(urlCasos);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void urlPrevent(View view){
        Uri uri = Uri.parse(urlPrevencion);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void aisla(View view){
        Uri uri = Uri.parse(urlAislamiento);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void atencion(View view){
        Uri uri = Uri.parse(urlAtencion);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void decretos(View view){
        Uri uri = Uri.parse(urlDrecreto);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}