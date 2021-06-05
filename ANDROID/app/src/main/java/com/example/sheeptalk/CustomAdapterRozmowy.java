package com.example.sheeptalk;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;


public class CustomAdapterRozmowy extends RecyclerView.Adapter<CustomAdapterRozmowy.CustomAdapterViewHolder>{
    private Singleton singleton;
    private Klient klient;

    Context context;
    public CustomAdapterRozmowy(Context context){
        this.singleton=Singleton.getInstance();
        this.klient = singleton.klient;
        this.context=context;
    }

    @Override
    public CustomAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_conversations,parent,false);
        return new CustomAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterViewHolder holder, final int position){
        holder.button.setText("");

    }

    @Override
    public int getItemCount(){
        return klient.convNumber();
    }

    public static class CustomAdapterViewHolder extends RecyclerView.ViewHolder{
        public Button button;
        public CustomAdapterViewHolder(@NonNull View itemView){
            super(itemView);
            button = itemView.findViewById(R.id.rozm);
        }
    }






}
