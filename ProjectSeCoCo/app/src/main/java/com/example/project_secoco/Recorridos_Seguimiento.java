package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Recorridos_Seguimiento extends AppCompatActivity {

    private ImageButton logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos__seguimiento);
        logout = (ImageButton) findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Recorridos_Seguimiento.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Recorridos_Seguimiento.this, MainActivity.class));
            }
        });
    }

    //Botones barra inferior
    public void returnHome(View view){
        startActivity(new Intent(this,Home_Seguimiento.class));
    }
    public void contacseguimin(View view){
        startActivity(new Intent(this,Contactos_Seguimiento.class));
    }
}