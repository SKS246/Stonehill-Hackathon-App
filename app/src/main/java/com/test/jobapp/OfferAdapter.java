package com.test.jobapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OfferAdapter extends FirebaseRecyclerAdapter<OffersModel, OfferAdapter.myViewHolder> {

    public OfferAdapter(@NonNull FirebaseRecyclerOptions<OffersModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull OffersModel model) {
        holder.Username.setText(model.getUsername());
        holder.Num.setText(model.getUserNum());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow2, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView Username, Num;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = (TextView) itemView.findViewById(R.id.nametext);
            Num = (TextView) itemView.findViewById(R.id.proftext);
        }
    }
}
