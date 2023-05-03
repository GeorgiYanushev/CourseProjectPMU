package com.example.courseproject.ListAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.courseproject.R;
import com.example.courseproject.Database.Scheduling;

import java.util.ArrayList;

public class SchedulingListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Scheduling> schedulings;

    private TextView description,amount,date,status;

    public SchedulingListAdapter(ArrayList<Scheduling> schedulings, Context context){
        this.schedulings=schedulings;
        this.context=context;

    }

    @Override
    public int getCount() {
        return schedulings.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.custom_list,parent,false);
        status=convertView.findViewById(R.id.custom_status);
        description=convertView.findViewById(R.id.custom_description);
        amount=convertView.findViewById(R.id.custom_amount);
        date=convertView.findViewById(R.id.custom_date);
        status.setText(context.getString(R.string.mperiodicity,schedulings.get(position).getPeriodicity()));
        description.setText(context.getString(R.string.mdescription,schedulings.get(position).getDescription()));
        amount.setText(context.getString(R.string.mamount,Double.toString(schedulings.get(position).getAmount())));
        date.setText(context.getString(R.string.mscheduling_date,schedulings.get(position).getDate().split("T")));
        return convertView;

    }
}
