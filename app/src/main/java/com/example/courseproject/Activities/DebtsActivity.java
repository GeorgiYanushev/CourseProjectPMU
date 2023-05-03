package com.example.courseproject.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.courseproject.ListAdapters.DebtsListAdapter;
import com.example.courseproject.Notifications.MyNotificationPublisher;
import com.example.courseproject.R;
import com.example.courseproject.databinding.DebtsScreenBinding;
import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Debt;
import com.example.courseproject.Database.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DebtsActivity extends Activity {
    DebtsScreenBinding binding;
    final Database database = new Database(this);
    private ArrayList<Debt> debts;
    DebtsListAdapter adapter;
    Debt dummy= new Debt();
    public static String NOTIFICATION_CHANNEL_ID="1001";
    public static String default_notification_id="default";
    Transaction transactionDummy= new Transaction();

    LocalDateTime now;
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        binding= DebtsScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstancesState);
        setContentView(binding.getRoot());
        debts=database.getUnclearedDebts();
        binding.debtsReturn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(DebtsActivity.this, HomeScreen.class);
                        startActivity(i);
                    }
                }
        );
        if (ActivityCompat.checkSelfPermission(DebtsActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DebtsActivity.this,new String[]{
                    Manifest.permission.POST_NOTIFICATIONS
            },100);

            return;
        }
        adapter=new DebtsListAdapter(debts,DebtsActivity.this);
        binding.debtsListview.setAdapter(adapter);
        binding.debtsCreation.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        now= LocalDateTime.now();
                        dummy.setDescription(binding.debtDescription.getText().toString());
                        dummy.setAmount(Double.parseDouble(binding.debtAmount.getText().toString()));
                        dummy.setIsPaid("No");
                        dummy.setDate(dtf.format(now));
                        database.addDebt(dummy);
                        debts=database.getUnclearedDebts();
                        adapter=new DebtsListAdapter(debts,DebtsActivity.this);
                        binding.debtsListview.setAdapter(adapter);
                        scheduleNotification(getNotification(getString(R.string.unpaid_debt)),1000*24*3600);
                    }
                }
        );
        binding.debtsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onDebtsClick(DebtsActivity.this,position);
            }
        });
    }

    private void onDebtsClick(Context context, int position){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Връщане на дълг");
        alert.setMessage("Искате ли да върнете дълга");
        alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Debt test= debts.get(position);
                if(database.getLastTotal()<test.getAmount())
                    Toast.makeText(context,"Недостатъчно капитал", Toast.LENGTH_SHORT).show();
                   else {
                    Double lastTotal = database.getLastTotal();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    now = LocalDateTime.now();
                    transactionDummy.setDescription(test.getDescription());
                    transactionDummy.setAmount(test.getAmount());
                    transactionDummy.setTotal(lastTotal - test.getAmount());
                    transactionDummy.setDirection("Out");
                    transactionDummy.setDate(dtf.format(now));
                    transactionDummy.setLocation("N/A");
                    database.addTransaction(transactionDummy);
                    database.updateDebt("Yes", test.getId());
                    debts=database.getUnclearedDebts();
                    adapter=new DebtsListAdapter(debts,DebtsActivity.this);
                    binding.debtsListview.setAdapter(adapter);

                }
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

    private void scheduleNotification(Notification notification, int delay){
        Intent notificationIntent = new Intent(DebtsActivity.this,MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION,1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION,notification);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(DebtsActivity.this,0,notificationIntent,PendingIntent.FLAG_IMMUTABLE);
        long futureMillis= SystemClock.elapsedRealtime()+delay;
        AlarmManager alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager!=null;
        alarmManager.set(alarmManager.ELAPSED_REALTIME_WAKEUP,futureMillis,pendingIntent);
    }

    private Notification getNotification(String content){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,default_notification_id);

        builder.setContentTitle("SCHEDULE NOTIFICATION");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);

        return builder.build();

    }
}
