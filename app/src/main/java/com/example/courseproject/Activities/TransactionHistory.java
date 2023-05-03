package com.example.courseproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.courseproject.R;
import com.example.courseproject.ListAdapters.TransactionListAdapter;
import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Transaction;
import com.example.courseproject.databinding.TransactionHistoryBinding;

import java.util.ArrayList;

public class TransactionHistory extends Activity {
    private ArrayList<Transaction> transactions= new ArrayList<Transaction>();
    final Database database = new Database(this);
    TransactionHistoryBinding binding;

    TransactionListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=TransactionHistoryBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.transactionsHistoryReturn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(TransactionHistory.this, HomeScreen.class);
                        startActivity(i);
                    }
                }
        );
        binding.transactionsHistoryWeek.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transactions = database.getThisWeekTransactions();
                        adapter =new TransactionListAdapter(transactions,TransactionHistory.this);
                        binding.listview.setAdapter(adapter);
                    }
                }
        );
        binding.transactionsHistoryMonth.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transactions = database.getTransactions();
                        adapter =new TransactionListAdapter(transactions,TransactionHistory.this);
                        binding.listview.setAdapter(adapter);
                    }
                }
        );
        binding.transactionsHistoryYear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transactions = database.getHistoryTransactions();
                        adapter =new TransactionListAdapter(transactions,TransactionHistory.this);
                        binding.listview.setAdapter(adapter);
                    }
                }
        );
        adapter =new TransactionListAdapter(transactions,this);
        binding.listview.setAdapter(adapter);
    }
}
