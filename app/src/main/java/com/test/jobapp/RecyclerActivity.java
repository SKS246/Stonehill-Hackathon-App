package com.test.jobapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RecyclerActivity extends AppCompatActivity{

    RecyclerView recview;
    WorkerAdapter adapter;

    public String UserName, Prof, Bio, Number, AskedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        Intent intent = getIntent();

        String prof = intent.getStringExtra("prof");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<WorkerModel> options =
                new FirebaseRecyclerOptions.Builder<WorkerModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Worker List").orderByChild("Prof").equalTo(prof), WorkerModel.class)
                        .build();

        adapter = new WorkerAdapter(options);
        recview.setAdapter(adapter);

        adapter.setOnItemClickListener(new WorkerAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AskedBy = options.getSnapshots().get(position).getPublisher();
                Number = options.getSnapshots().get(position).getUserNum();

                Intent intent2 = new Intent(RecyclerActivity.this, OfferActivity.class);
                intent2.putExtra("host", AskedBy);
                intent2.putExtra("number", Number);
                startActivity(intent2);
                finish();

//                UserName = options.getSnapshots().get(position).getUsername();
//                Prof = options.getSnapshots().get(position).getProf();
//                Bio = options.getSnapshots().get(position).getBio();
//                Num = options.getSnapshots().get(position).getUserNum();
//
//                Intent intent1 = new Intent(RecyclerActivity.this, Profile2Activity.class);
//                intent1.putExtra("username", UserName);
//                intent1.putExtra("prof", Prof);
//                intent1.putExtra("bio", Bio);
//                intent1.putExtra("Num", Num);
//                startActivity(intent1);
//                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}