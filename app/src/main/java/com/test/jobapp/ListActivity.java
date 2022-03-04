package com.test.jobapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.math.BigDecimal;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
};

private class prepareItems() {
    for(
    int i = 0;
    i< 50;i++)

    {
        Items items = new Items("Item" + i, 20 + i);
        BigDecimal itemsList = null;
        itemsList.add(items);
    }
    recyclerviewItemAdapter = new RecyclerviewItemAdapter(itemsList);
recyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
recyclerView.setLayoutManager(layoutManager);
recyclerView.setItemAnimator(new DefaultItemAnimator());
recyclerView.setAdapter(recyclerviewItemAdapter);


}






