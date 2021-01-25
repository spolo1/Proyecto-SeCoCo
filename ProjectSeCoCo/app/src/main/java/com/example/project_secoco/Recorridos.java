package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Recorridos extends AppCompatActivity {

    private ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos);
        logout = (ImageButton) findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Recorridos.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Recorridos.this, MainActivity.class));
            }
        });
    }

    public void returnHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, Formulario.class);
        startActivity(intent);
    }
    public void result(View view){
        Intent intent = new Intent(this, Resultados.class);
        startActivity(intent);
    }
}