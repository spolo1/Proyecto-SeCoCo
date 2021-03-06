package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    private RadioButton man, women;
    private EditText txtname, txtapellido, txtage, txtidenti, txtnumber, txtmedic, txtplace, txtlocal, txtmail, pass, txtperfil;
    private Button regis;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private RadioButton rman,rwom;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtname = (EditText) findViewById(R.id.Names);
        txtapellido = (EditText) findViewById(R.id.Apellidos);
        txtage = (EditText) findViewById(R.id.Edad);
        txtidenti = (EditText) findViewById(R.id.documento);
        txtnumber = (EditText) findViewById(R.id.numTel);
        txtmedic = (EditText) findViewById(R.id.Medico);
        txtplace = (EditText) findViewById(R.id.Casa);
        txtlocal = (EditText) findViewById(R.id.Localidad);
        txtmail = (EditText) findViewById(R.id.Correo);
        pass = (EditText) findViewById(R.id.contase);
        regis = (Button) findViewById(R.id.registro);
        mAuth = FirebaseAuth.getInstance();
        txtperfil = (EditText) findViewById(R.id.tipousuario);
        progressDialog = new ProgressDialog(this);
        rman = (RadioButton) findViewById(R.id.Hombre);
        rwom = (RadioButton) findViewById(R.id.Mujer);


    }



    //Bóton regreso
    public void returnStart(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void registrarUsuarios () {
        String name = txtname.getText().toString().trim();
        String lastName = txtapellido.getText().toString().trim();
        String age = txtage.getText().toString().trim();
        String cedula = txtidenti.getText().toString().trim();
        String celular = txtnumber.getText().toString().trim();
        String medicina = txtmedic.getText().toString().trim();
        String casa = txtplace.getText().toString().trim();
        String Localidad = txtlocal.getText().toString().trim();
        String crreo = txtmail.getText().toString().trim();
        String contrasena = pass.getText().toString();
        String perfil = txtperfil.getText().toString().trim();

        Intent intent = new Intent(this, MainActivity.class);

        if(TextUtils.isEmpty(crreo) && TextUtils.isEmpty(contrasena)) {
            Toast.makeText(this,"Rellene todos los campos para crear su usuario", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Creando su Usuario por favor espere...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(crreo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String cad = "";
                    if(rman.isChecked()){
                        cad = "Masculino";
                    }
                    if(rwom.isChecked()){
                        cad = "Femenino";
                    }
                    Toast.makeText(Registro.this, "Su usuario ha sido creado con exito", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    //Cargar usuario en base de datos
                    Map<String,Object> user = new HashMap();
                    user.put("Nombres",name);
                    user.put("Apellidos",lastName);
                    user.put("Edad",age);
                    user.put("Documento de Identificación", cedula);
                    user.put("Celular", celular);
                    user.put("Entidad prestadora de Salud", medicina);
                    user.put("Dirección", casa);
                    user.put("Localidad",Localidad);
                    user.put("Correo electrónico", crreo);
                    user.put("Contraseña",contrasena);
                    user.put("Tipo de perfil",perfil);
                    user.put("Genero",cad);
                    db.collection("users")
                            .add(user)
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
                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(Registro.this, "El usuario ya se encuentra registrado en la aplicación", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Registro.this, "Los campos no estan correctamente diligenciados para crear el usuario", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }



    @Override
    public void onClick(View v) {
        registrarUsuarios();
    }
}