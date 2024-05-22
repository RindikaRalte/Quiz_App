package com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.test_model;

import org.w3c.dom.Text;

import java.util.List;

public class test_adapter extends RecyclerView.Adapter<test_adapter.ViewHolder> {



    private final Context context;

    private List<test_model> listData;

    public test_adapter(Context context, List<test_model> listData) {
        this.context = context;
        this.listData = listData;
    }


    @NonNull
    @Override
    public test_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull test_adapter.ViewHolder holder, int position) {
        test_model model = listData.get(position);

        holder.name.setText(model.getUsername());
        holder.amount.setText(model.getPoints());
        holder.position.setText(String.valueOf(position + 1));
        holder.cte.setText(model.getCat());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView position;

        TextView cte;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            name = itemView.findViewById(R.id.name);
            amount = itemView.findViewById(R.id.amount);
            position = itemView.findViewById(R.id.position);
            cte = itemView.findViewById(R.id.cte);


        }
    }

}
