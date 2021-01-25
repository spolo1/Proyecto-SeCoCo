package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Resultados extends AppCompatActivity implements View.OnClickListener {

    private Button btnFecha,btncarga;
    private EditText fecha,entidad;
    private int day,month,year;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        btnFecha = (Button) findViewById(R.id.datepicker);
        btncarga = (Button) findViewById(R.id.subir);
        fecha = (EditText) findViewById(R.id.fecha);
        entidad = (EditText) findViewById(R.id.entidadexam);
        radioGroup = findViewById(R.id.radioGroup);

        btnFecha.setOnClickListener(this);
        btncarga.setOnClickListener(this);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v == btnFecha){
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                }
            },day,month,year);
            datePickerDialog.show();
        }
    }



}