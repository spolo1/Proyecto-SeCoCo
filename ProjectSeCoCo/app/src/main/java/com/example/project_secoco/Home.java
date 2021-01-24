package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button btnCasos;
    private String urlCasos,urlPrevencion,urlAislamiento, urlAtencion, urlDrecreto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        urlCasos = "https://coronaviruscolombia.gov.co/";
        urlPrevencion = "https://coronaviruscolombia.gov.co/Covid19/protocolos.html";
        urlAislamiento = "https://coronaviruscolombia.gov.co/Covid19/aislamiento-saludable.html";
        urlAtencion = "https://coronaviruscolombia.gov.co/Covid19/lineas-de-atencion.html";
        urlDrecreto = "https://coronaviruscolombia.gov.co/Covid19/datos-cuarentena.html";
    }

    //Botones interacciones inferior
    public void reportarSintomas(View view){
        Intent intent = new Intent(this, Formulario.class);
        startActivity(intent);
    }
    public void contactoPersonas(View view){
        Intent intent = new Intent(this, Contactos.class);
        startActivity(intent);
    }
    public void recorrido(View view){
        Intent intent = new Intent(this, Recorridos.class);
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