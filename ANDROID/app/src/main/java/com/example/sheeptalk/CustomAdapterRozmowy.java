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
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;

import java.util.List;


public class CustomAdapterRozmowy extends RecyclerView.Adapter<CustomAdapterRozmowy.CustomAdapterViewHolder>{
    private Singleton singleton;
    private Klient klient;
    private int index;
    private List<Object> lista;

    Context context;
    public CustomAdapterRozmowy(List<Object> lista, Context context){
        this.singleton=Singleton.getInstance();
        this.klient = singleton.klient;
        this.context=context;
        this.lista=lista;
    }

    @Override
    public CustomAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_conversations,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_maybe,parent,false);
        return new CustomAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterViewHolder holder, final int position){
        holder.button.setText(lista.get(position).toString());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "GUZIK " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount(){
        int conversationsNumber = klient.convNumber();
        return conversationsNumber;
    }

    public static class CustomAdapterViewHolder extends RecyclerView.ViewHolder{
        public Button button;
        public CustomAdapterViewHolder(@NonNull View itemView){
            super(itemView);
            button = itemView.findViewById(R.id.conversationButton);

        }
    }






}
