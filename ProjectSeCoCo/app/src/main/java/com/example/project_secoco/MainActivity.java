package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText txtUser, txtPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.contrasena);
        mAuth = FirebaseAuth.getInstance();
    }

    private void Ingresar(){
        String correo = txtUser.getText().toString().trim();
        String passwd = txtPassword.getText().toString().trim();
        Intent intent = new Intent(this, Formulario.class);
        if(TextUtils.isEmpty(correo)){
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(passwd)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_LONG).show();
        }
        mAuth.signInWithEmailAndPassword(correo, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Bienvenido a SeCoCo", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Datos erroneos", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void register(View view){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Ingresar();
    }
}