package com.example.project_secoco;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Citar_Toma_de_Muestras extends AppCompatActivity {

    private TextView mDisplayDate, mDisplayHour;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ImageButton logout;
    private EditText txtcorreo;
    private TextInputLayout mess;
    int thour, tminute;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String emailAccount = "spolo48@gmail.com";
    private static String emailPassword = "Eldios20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citar__toma_de__muestras);
        mDisplayDate = (TextView) findViewById(R.id.selectDate);
        mDisplayHour = (TextView) findViewById(R.id.selec_hour);
        logout = (ImageButton) findViewById(R.id.Logout);
        mess = (TextInputLayout) findViewById(R.id.message);
        txtcorreo = (EditText) findViewById(R.id.correo);

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Citar_Toma_de_Muestras.this, "Cerrrando sesión",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Citar_Toma_de_Muestras.this, MainActivity.class));
            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Citar_Toma_de_Muestras.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        mDisplayHour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Citar_Toma_de_Muestras.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                thour = hourOfDay;
                                tminute = minute;
                                Calendar ca = Calendar.getInstance();
                                ca.set(0,0,0,thour,tminute);
                                mDisplayHour.setText(DateFormat.format("hh:mm aa",ca));
                            }
                        }, 12,0,false
                );
                timePickerDialog.updateTime(thour,tminute);
                timePickerDialog.show();
            }
        });
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

    public void sendMessage(View view){


        String email = txtcorreo.getText().toString().trim();
        String asunto = "Agendamiento cita toma de prueba Covid";
        String fecha = mDisplayDate.getText().toString().trim();
        String hour = mDisplayHour.getText().toString().trim();
        String mes = mess.getEditText().getText().toString().trim();
        String from = emailAccount;
        String concat = mes + "\n" +
                "\n" +
                "Fecha de la cita: " + fecha + "\n" +
                "\n" +
                "Hora de la cita: " + hour;

        /*Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT,asunto);
        intent.putExtra(Intent.EXTRA_TEXT,concat);
        intent.setType("message/rfc822");
        startActivity(intent);*/
        Map<String, Object> citas_prueba = new HashMap<>();
        citas_prueba.put("Resultado",hour);
        citas_prueba.put("Fecha: ", fecha);
        citas_prueba.put("Correo",email);
        db.collection("citas_prueba")
                .add(citas_prueba)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentRef) {
                        Log.d("citas prueba", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("citas prueba", "Error writing document", e);
                    }
                });
        Toast.makeText(this,"Respuesta guardada", Toast.LENGTH_LONG).show();
        final ProgressDialog pd = ProgressDialog.show(this,
                "Sending", "Enviando Correo", true, false);
        new Thread((Runnable) () -> {
            try {
                GMailSender sender = new GMailSender(emailAccount, emailPassword);
                sender.sendMail(asunto,concat,from,email);
            }
            catch (Exception e){
                Log.e("sendMail",e.getMessage(),e);
            }
            pd.dismiss();
        }).start();
        startActivity(new Intent (this, Home_Diagnostico.class));
    }

}