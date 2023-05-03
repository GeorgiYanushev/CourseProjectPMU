package com.example.courseproject.ListAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.courseproject.R;
import com.example.courseproject.Database.Transaction;

import java.util.ArrayList;

public class TransactionListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Transaction> transactions;

    private TextView description,amount,date,direction,location;

    public TransactionListAdapter(ArrayList<Transaction> transactions, Context context){
                this.transactions=transactions;
                this.context=context;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.transactions_list,parent,false);
        direction=convertView.findViewById(R.id.direction);
        description=convertView.findViewById(R.id.description);
        amount=convertView.findViewById(R.id.amount);
        date=convertView.findViewById(R.id.date);
        location = convertView.findViewById(R.id.location);
        direction.setText(context.getString(R.string.mdirection,transactions.get(position).getDirection()));
        description.setText(context.getString(R.string.mdescription,transactions.get(position).getDescription()));
        amount.setText(context.getString(R.string.mamount,Double.toString(transactions.get(position).getAmount())));
        date.setText(context.getString(R.string.mdate,transactions.get(position).getDate()));
        location.setText(context.getString(R.string.mlocation,transactions.get(position).getLocation()));
        return convertView;

    }


}
