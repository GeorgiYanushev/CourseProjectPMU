package com.example.courseproject.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Savings;
import com.example.courseproject.Database.Transaction;
import com.example.courseproject.ListAdapters.SavingsListAdapter;
import com.example.courseproject.R;
import com.example.courseproject.databinding.SavingsScreenBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SavingsActivity extends Activity {
    ArrayList<Savings> savings;
    SavingsListAdapter adapter;
    final Database database = new Database(this);
    Savings dummy= new Savings();
    SavingsScreenBinding binding;
    Transaction transactionDummy= new Transaction();
    LocalDateTime now;
    private String m_Text="";
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        binding=SavingsScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstancesState);
        setContentView(binding.getRoot());
        savings=database.getUnCompleteSavings();
        adapter= new SavingsListAdapter(this,savings);
        binding.savingsListview.setAdapter(adapter);
        binding.savingsReturn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(SavingsActivity.this, HomeScreen.class);
                        startActivity(i);
                    }
                }
        );
        binding.savingsCreation.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        dummy.setDescription(binding.savingsDescription.getText().toString());
                        dummy.setTotal(Double.parseDouble(binding.savingsGoal.getText().toString()));
                        dummy.setCompleted("No");
                        database.addSavings(dummy);
                        savings=database.getUnCompleteSavings();
                        adapter=new SavingsListAdapter(SavingsActivity.this,savings);
                        binding.savingsListview.setAdapter(adapter);
                    }
                }
        );
        binding.savingsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SavingsOptions(SavingsActivity.this,position);
            }
        });
    }
    private void SavingsOptions(Context context, int position){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Опции");
        alert.setMessage("Ще изтеглите пари или ще внесете пари");
        alert.setPositiveButton("Теглене", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Savings test= savings.get(position);
                if(test.getAmount()<test.getTotal())
                WindrawOption(SavingsActivity.this,position);
                else{
                    extracted(test);
                }
            }
        });
        alert.setNegativeButton("Внасяне", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddToSavings(SavingsActivity.this,position);
            }
        });
        alert.create().show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void extracted(Savings test) {
        Double lastTotal = database.getLastTotal();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        now = LocalDateTime.now();
        transactionDummy.setDescription(test.getDescription());
        transactionDummy.setAmount(test.getAmount());
        transactionDummy.setTotal(lastTotal + test.getAmount());
        transactionDummy.setDirection("Out");
        transactionDummy.setDate(dtf.format(now));
        transactionDummy.setLocation("N/A");
        database.addTransaction(transactionDummy);

        database.updateSaving(test.getTotal(), test.getAmount(), test.getDescription(), test.getId(),"Yes");
        savings=database.getUnCompleteSavings();
        adapter=new SavingsListAdapter(SavingsActivity.this,savings);
        binding.savingsListview.setAdapter(adapter);
    }

    private void WindrawOption(Context context, int position){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Внимание");
        alert.setMessage("Сигурни ли сте че искате да изтеглите парите дори ако не цялата сума е събрана");
        alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Savings test= savings.get(position);
               extracted(test);
            }
        });
        alert.setNegativeButton("Не", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
    }
    private void AddToSavings(Context context, int position)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Въведете сума");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                Savings test = savings.get(position);
                if (database.getLastTotal() < Double.parseDouble(m_Text))
                    Toast.makeText(context, "Недостатъчно капитал", Toast.LENGTH_SHORT).show();
                else {
                    Double lastTotal = database.getLastTotal();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    now = LocalDateTime.now();
                    transactionDummy.setDescription(test.getDescription());
                    transactionDummy.setAmount(test.getAmount());
                    transactionDummy.setTotal(lastTotal - test.getAmount());
                    transactionDummy.setDirection("Out");
                    transactionDummy.setDate(dtf.format(now));
                    database.addTransaction(transactionDummy);

                    database.updateSaving(test.getTotal(), Double.parseDouble(m_Text), test.getDescription(), test.getId(), "No");
                    savings = database.getUnCompleteSavings();
                    adapter = new SavingsListAdapter(SavingsActivity.this, savings);
                    binding.savingsListview.setAdapter(adapter);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
