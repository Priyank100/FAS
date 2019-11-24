package com.priyank.fas.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.priyank.fas.Activity.SecondActivity;
import com.priyank.fas.Constant.SwipeToDeleteCallback;
import com.priyank.fas.Model.FestListModel;
import com.priyank.fas.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {
    public Context context;
    public ArrayList<FestListModel> list;

    public MyListAdapter(Context context, ArrayList<FestListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_layout, viewGroup, false);
        return new MyListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.textTitleLetter.setText(String.valueOf(list.get(position).getFestName().charAt(0)));
        holder.textTitle.setText(list.get(position).getFestName());

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");

        try {
            String str = outputFormat.format(inputFormat.parse(list.get(position).getFestDate()));
            holder.textDate.setText(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("Id", list.get(position).getFestId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitleLetter, textTitle, textDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitleLetter = itemView.findViewById(R.id.text_title_letter);
            textTitle = itemView.findViewById(R.id.text_title);
            textDate = itemView.findViewById(R.id.text_date);
        }
    }
}
