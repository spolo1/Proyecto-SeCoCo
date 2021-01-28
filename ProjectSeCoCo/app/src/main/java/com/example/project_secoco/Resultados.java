package com.example.project_secoco;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
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

public class Resultados extends AppCompatActivity{

    private Button btncarga;
    private EditText entidad,txtcor;
    private ImageButton logout;
    private TextInputLayout mess;
    private TextView mDisplayDate;
    private RadioButton rpos,rneg;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String date;
    private static String emailAccount = "spolo48@gmail.com";
    private static String emailPassword = "Eldios20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        mDisplayDate = (TextView) findViewById(R.id.selectDate2);
        btncarga = (Button) findViewById(R.id.subir);
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

        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Resultados.this,
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
                date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);
            }
        };
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

    public void cargarDatos (View view){
        String email = txtcor.getText().toString().trim();
        String asunto = "Resultados prueba Covid";
        String centro = entidad.getText().toString().trim();
        String mes = mess.getEditText().getText().toString().trim();
        String from = emailAccount;

        String cad = "";
        if(rpos.isChecked()){
            cad = "Positivo";
        }
        if(rneg.isChecked()){
            cad = "Negativo";
        }

        String et = entidad.getText().toString().trim();
        Map<String, Object> respuesta = new HashMap<>();
        String fecha = mDisplayDate.getText().toString().trim();
        respuesta.put("Entidad", et);
        respuesta.put("Resultado",cad);
        respuesta.put("Fecha: ", fecha);
        respuesta.put("Correo",email);


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
        String concat =mes + "\n" +
                "\n" +
                "Fecha de toma del examen: " + fecha + "\n" +
                "\n" +
                "Entidad donde se realizo la prueba: " + centro + "\n" +
                "\n" +
                "Su resultado es: " + cad;
        /*
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
        intent.putExtra(Intent.EXTRA_SUBJECT,asunto);
        intent.putExtra(Intent.EXTRA_TEXT,concat);
        intent.setType("message/rfc822");
        startActivity(intent);*/
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

    }
}