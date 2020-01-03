package com.example.government;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GovAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "API_AsyncTask";

    private ArrayList<Offical> aList;
    private MainActivity mainActivity;


    GovAdapter(ArrayList<Offical> list, MainActivity mainActivity) {
        aList = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gov_view, parent, false);

        itemView.setOnClickListener(mainActivity);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);
        Offical stock = aList.get(position);

        String ti = String.format("%s (%s)",stock.getName(), stock.getParty());

        holder.role.setText(stock.getRole());
        holder.name.setText(ti);



    }

    @Override
    public int getItemCount() {
        return aList.size();
    }
}
