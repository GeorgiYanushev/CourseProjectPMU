package com.example.courseproject.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.courseproject.R;
import com.example.courseproject.databinding.HomeScreenBinding;
import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Scheduling;
import com.example.courseproject.Database.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Activity {
    final Database database = new Database(this);
    HomeScreenBinding binding;
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        LocalDate now=LocalDate.now();
        binding= HomeScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstancesState);
        setContentView(binding.getRoot());
        ArrayList<Scheduling> schedulings= database.getScheduling();
        Scheduling priv= database.GetPrivateSchedule();
        if(priv==null){database.addSchedule(new Scheduling( "A1B2C3D4!@#",0, LocalDateTime.now().toString(),LocalDate.now().plusMonths(0).toString(),"W","M"));}
        if(schedulings.size() > 0) {
            for (Scheduling s : schedulings) {
                if (now.toString().equals(s.getNextDate().split("T")[0]) || now.plusDays(-1).toString().equals(s.getNextDate().split("T")[0])) {
                    database.addTransaction(new Transaction(s.getDescription(), s.getAmount(), database.getLastTotal() + s.getAmount(), now.toString(), "In", "N/A"));
                    switch (s.getPeriodicity()) {
                        case "D":
                            database.updateScheduling(s.getAmount(), s.getDescription(), s.getId(), now.plusDays(1).toString(), "W");
                            break;
                        case "W":
                            database.updateScheduling(s.getAmount(), s.getDescription(), s.getId(), now.plusDays(7).toString(), "W");
                            break;
                        case "M":
                            database.updateScheduling(s.getAmount(), s.getDescription(), s.getId(), now.plusMonths(1).toString(), "W");
                            break;
                    }
                }
            }
        }
        List<Transaction> log = database.getTransactions();
        if(log.size()>0) {
            if (now.toString().equals(priv.getNextDate().split("T")[0]) || now.plusDays(-1).toString().equals(priv.getNextDate().split("T")[0])) {

                for (Transaction t : log) {
                    database.addLogTransaction(t);
                    database.deleteFromTransaction(t.getId());
                }
                Transaction lastT = database.lastTransaction();
                lastT.setDescription("A1B2C3D4!@#");
                database.addTransaction(lastT);
                database.updateScheduling(priv.getAmount(), priv.getDescription(), priv.getId(), now.plusMonths(1).toString(), "W");
            }
        }
       //database.onDeleteTable();
        binding.balance.setText(getString(R.string.mbalance,database.getLastTotal().toString()));
        binding.transactions.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(HomeScreen.this,TransactionActivity.class);
                        startActivity(i);
                    }
                }
        );
        binding.transactionsHistory.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(HomeScreen.this, TransactionHistory.class);
                        startActivity(i);
                    }
                }
        );
        binding.debts.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(HomeScreen.this,DebtsActivity.class);
                        startActivity(i);
                    }
                }
        );
        binding.savings.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(HomeScreen.this,SavingsActivity.class);
                        startActivity(i);
                    }
                }
        );
        binding.scheduling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeScreen.this,SchedulingActivity.class);
                startActivity(i);
            }
        });
    }
}
