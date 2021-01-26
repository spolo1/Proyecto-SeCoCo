package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Contactos_Personas extends AppCompatActivity {

    private ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_personas);
        logout = (ImageButton) findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Contactos_Personas.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Contactos_Personas.this, MainActivity.class));
            }
        });
    }



    //Botones interacciones inferior
    public void reportarSintomas(View view){
        Intent intent = new Intent(this, Formulario.class);
        startActivity(intent);
    }
    public void recorrido(View view){
        Intent intent = new Intent(this, Recorridos_Personas.class);
        startActivity(intent);
    }
    public void returnHome(View view){
        Intent intent = new Intent(this, Home_personas.class);
        startActivity(intent);
    }

}