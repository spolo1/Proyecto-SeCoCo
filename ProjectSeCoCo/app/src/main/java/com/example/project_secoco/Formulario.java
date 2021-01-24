package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Formulario extends AppCompatActivity implements View.OnClickListener{

    private Button btn;
    private CheckBox s1,s2,s3,s4,s5,s6,s7,s8,sNull;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        s1 = (CheckBox) findViewById(R.id.checkBox);
        s2 = (CheckBox) findViewById(R.id.checkBox2);
        s3 = (CheckBox) findViewById(R.id.checkBox3);
        s4 = (CheckBox) findViewById(R.id.checkBox4);
        s5 = (CheckBox) findViewById(R.id.checkBox5);
        s6 = (CheckBox) findViewById(R.id.checkBox6);
        s7 = (CheckBox) findViewById(R.id.checkBox7);
        s8 = (CheckBox) findViewById(R.id.checkBox8);
        sNull = (CheckBox) findViewById(R.id.checkBox9);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void returnHome(View view){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void cargarSintomas(){
        String x1 = s1.getText().toString();
        String x2 = s2.getText().toString();
        String x3 = s3.getText().toString();
        String x4 = s4.getText().toString();
        String x5 = s5.getText().toString();
        String x6 = s6.getText().toString();
        String x7 = s7.getText().toString();
        String x8 = s8.getText().toString();
        String xnull = sNull.getText().toString();
        Boolean c1=false,c2=false,c3=false,c4=false,c5=false,c6=false,c7=false,c8=false,c9=false;
        if(TextUtils.isEmpty(xnull)){
            Toast.makeText(this,"Rellene todos los campos", Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Cargando sintomas");
        progressDialog.show();

        if(s1.isChecked()){
            c1= true;
        }
        if(s2.isChecked()){
            c2 = true;
        }
        if(s3.isChecked()){
            c3 = true;
        }
        if (s4.isChecked()){
            c4 = true;
        }
        if(s5.isChecked()){
            c5 = true;
        }
        if(s6.isChecked()){
            c6 = true;
        }
        if(s7.isChecked()){
            c7 = true;
        }
        if(s8.isChecked()){
            c8 = true;
        }

        Map<String,Object> Sintomas = new HashMap();
        if(sNull.isChecked()){
            Sintomas.put("Fiebre","False");
            Sintomas.put("Dolor de garganta","False");
            Sintomas.put("Congestión nasal","False");
            Sintomas.put("Tos", "False");
            Sintomas.put("Dificultad Respirar", "False");
            Sintomas.put("Fatiga", "False");
            Sintomas.put("Dolor de músculos","False");
            Sintomas.put("Escalofrio","False");
            Sintomas.put("Ninguna de las anteriores", c9);
            startActivity(new Intent(this, Home.class));
        }else{
            Sintomas.put("Fiebre",c1);
            Sintomas.put("Dolor de garganta",c2);
            Sintomas.put("Congestión nasal",c3);
            Sintomas.put("Tos", c4);
            Sintomas.put("Dificultad Respirar", c5);
            Sintomas.put("Fatiga", c6);
            Sintomas.put("Dolor de músculos", c7);
            Sintomas.put("Escalofrio",c8);
            Sintomas.put("Ninguna de las anteriores", "Flase");
            startActivity(new Intent(this, Home.class));
        }



        db.collection("Sintomas")
                .add(Sintomas)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    @Override
    public void onClick(View v) {
        cargarSintomas();
    }
}