package com.example.courseproject.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.courseproject.Database.Database;
import com.example.courseproject.R;
import com.example.courseproject.Database.Savings;

import java.util.ArrayList;

public class SavingsListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Savings> savings;

    private TextView description, current, goal;

    public SavingsListAdapter(Context context, ArrayList<Savings> savings){
        this.context=context;
        this.savings=savings;
    }
    @Override
    public int getCount() {
        return savings.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.savings_list,parent,false);
        description=convertView.findViewById(R.id.savings_description);
        current=convertView.findViewById(R.id.savings_current);
        goal=convertView.findViewById(R.id.savings_goal);
        description.setText(context.getString(R.string.msavings_descriptions,savings.get(position).getDescription()));
        current.setText(context.getString(R.string.msavings_current,Double.toString(savings.get(position).getAmount())));
        goal.setText(context.getString(R.string.msavings_goal,Double.toString(savings.get(position).getTotal())));
        return convertView;
    }
}
