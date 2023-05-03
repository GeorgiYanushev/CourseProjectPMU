package com.example.courseproject.ListAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.courseproject.R;
import com.example.courseproject.Database.Debt;

import java.util.ArrayList;

public class DebtsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Debt> debts;

    private TextView description,amount,date,isPaid;

    public DebtsListAdapter(ArrayList<Debt> debts, Context context){
        this.debts=debts;
        this.context=context;

    }

    @Override
    public int getCount() {
        return debts.size();
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
        isPaid=convertView.findViewById(R.id.custom_status);
        description=convertView.findViewById(R.id.custom_description);
        amount=convertView.findViewById(R.id.custom_amount);
        date=convertView.findViewById(R.id.custom_date);
        isPaid.setText(context.getString(R.string.mdebts_status,debts.get(position).getIsPaid()));
        description.setText(context.getString(R.string.mdebts_description,debts.get(position).getDescription()));
        amount.setText(context.getString(R.string.mamount,Double.toString(debts.get(position).getAmount())));
        date.setText(context.getString(R.string.mdebts_date,debts.get(position).getDate()));
        return convertView;

    }

}
