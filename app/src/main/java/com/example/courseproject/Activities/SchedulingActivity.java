package com.example.courseproject.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.courseproject.ListAdapters.SchedulingListAdapter;
import com.example.courseproject.databinding.SchedulingScreenBinding;
import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Scheduling;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class SchedulingActivity extends Activity {

    private ArrayList<Scheduling> schedules;
    SchedulingListAdapter adapter;
    SchedulingScreenBinding binding;
    int Day,Month,Year;
    String periodicity;
    Scheduling dummy= new Scheduling();
    LocalDateTime localDate;
    Calendar calendar;
    TimeZone tz;
    ZoneId zoneId;

    final Database database = new Database(this);
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstancesState) {
        binding= SchedulingScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstancesState);
        setContentView(binding.getRoot());
        schedules=database.getScheduling();
        ArrayList<String> list= new ArrayList<>();
        list.add("Дневни");
        list.add("Седмични");
        list.add("Месечни");
        adapter= new SchedulingListAdapter(schedules,SchedulingActivity.this);
        binding.schedulingListview.setAdapter(adapter);
       // binding.transactionsPeriodicity;
        ArrayAdapter<String> periodiciyAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        binding.transactionsPeriodicity.setAdapter(periodiciyAdapter);
        binding.transactionsStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                tz= calendar.getTimeZone();
                zoneId=tz.toZoneId();
                localDate= LocalDateTime.ofInstant(calendar.toInstant(),zoneId);
                
            }
        });

    binding.transactionsIncome.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            periodicity= binding.transactionsPeriodicity.getSelectedItem().toString();
            dummy.setDescription(binding.transactionsDescription.getText().toString());
            dummy.setAmount(Double.parseDouble(binding.transactionsCost.getText().toString()));
            dummy.setDate(localDate.toString());
            dummy.setstatus("W");
            switch (periodicity){
                case "Дневни":
                    dummy.setNextDate(localDate.plusDays(1).toString());
                    dummy.setPeriodicity("D");
                    break;
                case "Седмични":
                    dummy.setNextDate(localDate.plusDays(7).toString());
                    dummy.setPeriodicity("W");
                    break;
                case "Месечни":
                    dummy.setNextDate(localDate.plusMonths(1).toString());
                    dummy.setPeriodicity("M");
                    break;
            }

            database.addSchedule(dummy);
            schedules=database.getScheduling();
            adapter= new SchedulingListAdapter(schedules,SchedulingActivity.this);
            binding.schedulingListview.setAdapter(adapter);
        }
    });
    binding.transactionsReturn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i= new Intent(SchedulingActivity.this, HomeScreen.class);
            startActivity(i);
        }
    });
    binding.schedulingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            onSchedulesClick(SchedulingActivity.this,position);
        }
    });
    }
    private void onSchedulesClick(Context context, int position){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Триене");
        alert.setMessage("Искате ли да изтриете планът");
        alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Scheduling test= schedules.get(position);
                database.deleteSchedule(test.getId());
                schedules=database.getScheduling();
                adapter= new SchedulingListAdapter(schedules,SchedulingActivity.this);
                binding.schedulingListview.setAdapter(adapter);
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
}
