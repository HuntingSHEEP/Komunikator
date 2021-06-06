package com.example.sheeptalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheeptalk.logic.klient.Klient;
import com.example.sheeptalk.logic.klient.Singleton;

import java.util.List;


public class CustomAdapterWiadomosci extends RecyclerView.Adapter<CustomAdapterWiadomosci.CustomAdapterViewHolder>{
    private Singleton singleton;
    private Klient klient;
    private int index;


    Context context;



    public CustomAdapterWiadomosci(Context context){
        this.singleton=Singleton.getInstance();
        this.klient = singleton.klient;
        this.context=context;

    }

    @Override
    public CustomAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_conversations,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_maybe,parent,false);
        return new CustomAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterViewHolder holder, final int position){


    }

    @Override
    public int getItemCount(){
        return 0;
    }

    public static class CustomAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView user,timem,messege;
        public CustomAdapterViewHolder(@NonNull View itemView){
            super(itemView);
            user = itemView.findViewById(R.id.textViewAPRName);
            timem = itemView.findViewById(R.id.textView4);
            messege = itemView.findViewById(R.id.textView5);

        }
    }






}
