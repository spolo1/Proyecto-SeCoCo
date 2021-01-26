package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Resultados extends AppCompatActivity implements View.OnClickListener {

    private Button btnFecha,btncarga;
    private EditText fecha,entidad,txtcor;
    private int day,month,year;
    private ImageButton logout;
    private TextInputLayout mess;
    private RadioButton rpos,rneg;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        btnFecha = (Button) findViewById(R.id.datepicker);
        btncarga = (Button) findViewById(R.id.subir);
        fecha = (EditText) findViewById(R.id.fecha);
        entidad = (EditText) findViewById(R.id.entidadexam);
        logout = (ImageButton) findViewById(R.id.Logout);
        txtcor = (EditText) findViewById(R.id.send_result);
        mess = (TextInputLayout) findViewById(R.id.mesage);
        rpos = (RadioButton) findViewById(R.id.radioPosit);
        rneg = (RadioButton) findViewById(R.id.radioNeg);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Resultados.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Resultados.this, MainActivity.class));
            }
        });

        btnFecha.setOnClickListener(this);
    }



    //Botones interacciones inferior

    public void result(View view){
        startActivity(new Intent(this, Resultados.class));
    }
    public void cita(View view){
        startActivity(new Intent(this,Citar_Toma_de_Muestras.class));
    }
    public void returnHome(View view){
        startActivity(new Intent(this, Home_Diagnostico.class));
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
    public void cargarDatos (View view){
        String cad = "";
        if(rpos.isChecked()){
            cad = "Positivo";
        }
        if(rneg.isChecked()){
            cad = "Negativo";
        }
        String et = entidad.getText().toString().trim();
        String date = fecha.getText().toString().trim();
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Entidad", et);
        respuesta.put("Fecha",date);
        respuesta.put("Resultado",cad);


        db.collection("resultados")
                .add(respuesta)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentRef) {
                        Log.d("resultados", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("resultados", "Error writing document", e);
                    }
                });
        Toast.makeText(this,"Respuesta guardada", Toast.LENGTH_LONG).show();
        startActivity(new Intent(Resultados.this, Home_Diagnostico.class));

        String email = txtcor.getText().toString().trim();
        String asunto = "Resultados prueba Covid";
        String fec = fecha.getText().toString().trim();
        String centro = entidad.getText().toString().trim();
        String mes = mess.getEditText().getText().toString().trim();
        String concat =mes + "\n" +
                "\n" +
                "Fecha de toma del examen: " +  fec + "\n" +
                "\n" +
                "Entidad donde se realizo la prueba: " + centro + "\n" +
                "\n" +
                "Su resultado es: " + cad;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT,asunto);
        intent.putExtra(Intent.EXTRA_TEXT,concat);
        intent.setType("message/rfc822");
        startActivity(intent);

    }







}