package com.example.project_secoco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Recorridos_Decisiones extends AppCompatActivity {

    private ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos__decisiones);
        logout = (ImageButton) findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Recorridos_Decisiones.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Recorridos_Decisiones.this, MainActivity.class));
            }
        });
    }

    public void returnHome(View view){
        startActivity(new Intent(this,Home_Decisiones.class));
    }
}