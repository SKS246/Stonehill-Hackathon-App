package com.test.jobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class OfferActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText AddBox, NameBox, NumBox, MsgBox;
    private Button cancel, send;

    private ProgressDialog loader;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId = "", offerFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Intent intent = getIntent();

        offerFor = intent.getStringExtra("host");

        toolbar = findViewById(R.id.askQToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post a Job Offer");

        AddBox = findViewById(R.id.AddText);
        NameBox = findViewById(R.id.NameTextBar);
        NumBox = findViewById(R.id.NumTextBar);
        MsgBox = findViewById(R.id.MessageText);
        cancel = findViewById(R.id.cancel);
        send = findViewById(R.id.post);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performValidations();
            }
        });

    }

    String getNText(){
        return NameBox.getText().toString().trim();
    }

    String getMText(){
        return MsgBox.getText().toString().trim();
    }

    String getNumText(){
        return NumBox.getText().toString().trim();
    }

    String getAddText(){
        return AddBox.getText().toString().trim();
    }


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Offer List");

    private void performValidations() {
        if (getNText().isEmpty()) {
            NameBox.setError("Name Required");
        } else if (getNumText().isEmpty()) {
            NumBox.setError("Number Required");
        } else if (!getNText().isEmpty() && !getNumText().isEmpty()) {
            uploadQwoImage();
        }
    }

    private void startLoader(){
        loader.setMessage("Posting Your Job Offer!");
        loader.setCanceledOnTouchOutside(false);
        loader.show();
    }

    private void uploadQwoImage() {
        startLoader();
        String postid = ref.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("UserNum", getNumText());
        hashMap.put("Username", getNText());
        hashMap.put("Add", getAddText());
        hashMap.put("Publisher", onlineUserId);
        hashMap.put("Msg", getMText());
        hashMap.put("AskedFor", offerFor);

        ref.child(postid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OfferActivity.this, "Job Offer Posted", Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                    startActivity(new Intent(OfferActivity.this, Home2Activity.class));
                    finish();
                }

                else{
                    Toast.makeText(OfferActivity.this, "Job Offer failed" + task.getException(), Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                }
            }
        });
    }
}