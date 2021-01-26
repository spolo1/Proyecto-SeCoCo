package com.example.project_secoco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private EditText txtUser, txtPassword;
    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private static final int SIGN_IN_CODE =777;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Intent intentDiagnostico, intenPersonas, intentSeguimiento, intentDesicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.contrasena);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signIn);

        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });
}

    private void Ingresar(){
        String correo = txtUser.getText().toString().trim();
        String passwd = txtPassword.getText().toString().trim();

        intenPersonas = new Intent(this, Home_personas.class);
        intentDiagnostico = new Intent(this,Home_Diagnostico.class);
        intentSeguimiento = new Intent(this,Home_Seguimiento.class);
        intentDesicion = new Intent(this,Home_Decisiones.class);

        if(TextUtils.isEmpty(correo)){
            makeText(this, "Ingrese un correo", LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(passwd)){
            makeText(this, "Ingrese una contraseña", LENGTH_LONG).show();
        }
        mAuth.signInWithEmailAndPassword(correo, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.setMessage("Iniciando sesión");
                            progressDialog.show();
                            makeText(MainActivity.this, "Bienvenido a SeCoCo", LENGTH_LONG).show();

                        //inicia las interfaces segun ROL
                            db.collection("users").whereEqualTo("Correo electrónico",correo)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    if(document.getData().get("Tipo de perfil").equals("Diagnostico")){
                                                        Home_Diagnostico.usuarioUtilizandoApp(correo);
                                                        startActivity(intentDiagnostico);
                                                    }else if(document.getData().get("Tipo de perfil").equals("Persona")){
                                                        Home_personas.usuarioUtilizandoApp(correo);
                                                        startActivity(intenPersonas);
                                                    }else if(document.getData().get("Tipo de perfil").equals("Seguimiento")){
                                                        Home_Seguimiento.usuarioUtilizandoApp(correo);
                                                        startActivity(intentSeguimiento);
                                                    }else if(document.getData().get("Tipo de perfil").equals("Desicion")){
                                                        Home_Decisiones.usuarioUtilizandoApp(correo);
                                                        startActivity(intentDesicion);
                                                    }
                                                }
                                            } else {
                                                Log.w("Contactos", "Error getting documents.", task.getException());
                                            }
                                        }
                                    });

                            Log.d("Contactos", "no entre al db");
                        } else {
                            makeText(MainActivity.this, "Datos erroneos", LENGTH_LONG).show();

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            Intent intent = new Intent(this, Formulario.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            makeText(this, "No se pudo iniciar sesión", LENGTH_LONG).show();
        }
    }
}