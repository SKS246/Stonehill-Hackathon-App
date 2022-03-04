package com.test.jobapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class JobProfActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinner;
    private EditText BioBox, NameBox;
    private ImageView imageView;
    private Button cancel, post;
    private TextView EmpQ;

    private String askedByName = "";
    private DatabaseReference askedByRef;
    private ProgressDialog loader;
    private String myUrl = "";
    StorageTask uploadTask;
    StorageReference storageReference;
    private Uri imageUrl;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jobprof);

        toolbar = findViewById(R.id.askQToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post a Job Brief");

        spinner = findViewById(R.id.spinner);
        BioBox = findViewById(R.id.BioText);
        NameBox = findViewById(R.id.NameText);
        imageView = findViewById(R.id.QuesionImg);
        cancel = findViewById(R.id.cancel);
        post = findViewById(R.id.post);
        EmpQ = findViewById(R.id.EmpQ);

        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();

        askedByRef = FirebaseDatabase.getInstance().getReference("users").child(onlineUserId);
        askedByRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                askedByName = snapshot.child("fullname").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        storageReference = FirebaseStorage.getInstance().getReference("questions");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.topics));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().equals("Select Topic")){
                    Toast.makeText(JobProfActivity.this, "Please Select a Valid Topic", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/");
                startActivityForResult(intent, 1);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performValidations();
            }
        });

        EmpQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobProfActivity.this, Home2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    String getBText(){
        return BioBox.getText().toString().trim();
    }

    String getNText(){
        return NameBox.getText().toString().trim();
    }

    String getJob(){
        return spinner.getSelectedItem().toString();
    }

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Worker List");

    private void performValidations() {
        if (getNText().isEmpty()) {
            NameBox.setError("Name Required");
        } else if (getJob().equals("Select Profession")) {
            Toast.makeText(this, "Please Select a Valid Profession", Toast.LENGTH_SHORT).show();
        } else if (!getNText().isEmpty() && !getJob().equals("Select Profession") && imageUrl == null) {
            uploadQwoImage();
        } else if (!getNText().isEmpty() && !getJob().equals("Select Profession") && imageUrl != null) {
            uploadQwithImage();
        }
    }

    private void startLoader(){
        loader.setMessage("Posting Your Job Brief!");
        loader.setCanceledOnTouchOutside(false);
        loader.show();
    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadQwithImage() {
        startLoader();
        final StorageReference fileRef;
        fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUrl));
        uploadTask = fileRef.putFile(imageUrl);
        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if(!task.isComplete()){
                    throw task.getException();
                }
                return fileRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Uri downloadUri = (Uri) task.getResult();
                    myUrl = downloadUri.toString();
                    String postid = ref.push().getKey();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("User id", postid);
                    hashMap.put("User name", getNText());
                    hashMap.put("Bio", getBText());
                    hashMap.put("Publisher", onlineUserId);
                    hashMap.put("Prof", getJob());
                    hashMap.put("Asked By", askedByName);
                    hashMap.put("questionImage", myUrl);

                    ref.child(postid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(JobProfActivity.this, "Job Brief Posted", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                                startActivity(new Intent(JobProfActivity.this, HomeActivity.class));
                                finish();
                            }

                            else{
                                Toast.makeText(JobProfActivity.this, "Job Post failed" + task.getException(), Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JobProfActivity.this, "Job Post failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadQwoImage() {
        startLoader();
        String postid = ref.push().getKey();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("User id", postid);
        hashMap.put("User name", getNText());
        hashMap.put("Bio", getBText());
        hashMap.put("Publisher", onlineUserId);
        hashMap.put("Prof", getJob());
        hashMap.put("Asked By", askedByName);

        ref.child(postid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(JobProfActivity.this, "Job Brief Posted", Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                    startActivity(new Intent(JobProfActivity.this, HomeActivity.class));
                    finish();
                }

                else{
                    Toast.makeText(JobProfActivity.this, "Job Post failed" + task.getException(), Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data !=null){
            imageUrl = data.getData();
            imageView.setImageURI(imageUrl);
        }
    }
}