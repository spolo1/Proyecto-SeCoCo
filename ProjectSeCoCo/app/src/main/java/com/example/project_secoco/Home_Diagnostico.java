package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home_Diagnostico extends AppCompatActivity {

    private ImageButton logout;
    private String urlCasos,urlPrevencion,urlAislamiento, urlAtencion, urlDrecreto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__diagnostico);
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
                Toast.makeText(Home_Diagnostico.this, "Cerrrando sesión",Toast.LENGTH_LONG).show();
                startActivity(new Intent(Home_Diagnostico.this, MainActivity.class));
            }
        });
    }
    //Acciones botones inferiores

    public void result(View view){
        startActivity(new Intent(this, Resultados.class));
    }

    public void cita(View view){
        startActivity(new  Intent(this, Citar_Toma_de_Muestras.class));
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