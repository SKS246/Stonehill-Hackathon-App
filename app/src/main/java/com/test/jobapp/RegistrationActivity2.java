package com.test.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity2 extends AppCompatActivity {

    private EditText fullname, email, password;
    private Button register, switchbutt;
    private TextView question;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ProgressDialog loader;
    private String onlineUserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);

        register = findViewById(R.id.regButton);
        switchbutt = findViewById(R.id.emplButton);
        question = findViewById(R.id.LoginQ);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity2.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        switchbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity2.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullname.getText().toString();
                String Email = email.getText().toString();
                String PassText = password.getText().toString();

                if (TextUtils.isEmpty(fullName)){
                    fullname.setError("Name is necessary");
                }
                if (TextUtils.isEmpty(Email)){
                    email.setError("Email is necessary");
                }
                if (TextUtils.isEmpty(PassText)){
                    password.setError("Password is necessary");
                }

                else{
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(Email, PassText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegistrationActivity2.this, "Registration Failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegistrationActivity2.this, "Registration Success", Toast.LENGTH_SHORT).show();

                                onlineUserID = mAuth.getCurrentUser().getUid();
                                reference = FirebaseDatabase.getInstance().getReference().child("users").child(onlineUserID);
                                Map hashMap = new HashMap();
                                hashMap.put("username", Email);
                                hashMap.put("fullname", fullName);
                                hashMap.put("id", onlineUserID);
                                hashMap.put("type", "employer");
                                reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegistrationActivity2.this, "Details set Successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(RegistrationActivity2.this, "Data failed to upload" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                        loader.dismiss();
                                    }
                                });

                                Intent intent = new Intent(RegistrationActivity2.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }
}