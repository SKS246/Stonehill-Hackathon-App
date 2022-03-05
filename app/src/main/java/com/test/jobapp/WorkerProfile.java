package com.test.jobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class WorkerProfile extends AppCompatActivity {

    private TextView name, number, bio, prof;
    private CircleImageView display;
    private Button cancel, copy, sendReq;

    String nametxt, numbertxt, biotxt, proftxt, displayurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        Intent intent = getIntent();
        nametxt = intent.getStringExtra("username");
        numbertxt = intent.getStringExtra("Num");
        biotxt = intent.getStringExtra("bio");
        proftxt = intent.getStringExtra("prof");
        displayurl = intent.getStringExtra("url");


        name = findViewById(R.id.nameplace);
        number = findViewById(R.id.numplace);
        bio = findViewById(R.id.bioplace);
        prof = findViewById(R.id.profplace);

        display = findViewById(R.id.imagedp);

        cancel= findViewById(R.id.cancel);
        copy = findViewById(R.id.copy);
        sendReq = findViewById(R.id.sendReq);

        name.setText(nametxt);
        number.setText(numbertxt);
        bio.setText(biotxt);
        prof.setText(proftxt);
        Glide.with(display.getContext()).load(displayurl).into(display);

        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(WorkerProfile.this, OfferActivity.class);
                intent2.putExtra("number", numbertxt);
                intent2.putExtra("host", intent.getStringExtra("host"));
                startActivity(intent2);
                finish();
            }
        });
    }
}