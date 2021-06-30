package com.example.plantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.MyViewHolder> {


    private ArrayList<Plant> plantArrayList;

    public PlantAdapter(ArrayList<Plant> plantArrayList) {
        this.plantArrayList = plantArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.MyViewHolder holder, int position) {
        Plant plant = plantArrayList.get(position);
        holder.plantName.setText(plant.getPlantName());
        Glide.with(holder.itemView).load(plant.getPlantPic()).into(holder.plantPicture);
    }

    @Override
    public int getItemCount() {
        return plantArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView plantName;
        ImageView plantPicture;
        public MyViewHolder (@NonNull View itemView){
            super(itemView);

            plantName = itemView.findViewById(R.id.plant_name);
            plantPicture = itemView.findViewById(R.id.plant_image);
        }
    }
}
