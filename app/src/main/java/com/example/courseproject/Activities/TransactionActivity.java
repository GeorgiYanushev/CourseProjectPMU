package com.example.courseproject.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.courseproject.databinding.TransactionsScreenBinding;
import com.example.courseproject.Database.Database;
import com.example.courseproject.Database.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionActivity extends Activity implements LocationListener {
    TransactionsScreenBinding binding;
    LocationManager locationManager;
    Transaction dummy= new Transaction();
    List<Address> addresses = new ArrayList<>();
    String address;
    LocalDateTime now;
    final Database database = new Database(this);
    private double lastTotal;
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        binding= TransactionsScreenBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstancesState);
        getLocation();
        setContentView(binding.getRoot());
        if (ContextCompat.checkSelfPermission(TransactionActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TransactionActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        binding.transactionsReturn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i= new Intent(TransactionActivity.this, HomeScreen.class);
                        startActivity(i);
                    }
                }
        );
        binding.transactionsIncome.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if(database.getLastTotal()<=0)lastTotal=0;
                        else lastTotal= database.getLastTotal();
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        now=LocalDateTime.now();
                        dummy.setDescription(binding.transactionsDescription.getText().toString());
                        dummy.setAmount(Double.parseDouble(binding.transactionsCost.getText().toString()));
                        dummy.setTotal(lastTotal+ dummy.getAmount());
                        dummy.setDirection("In");
                        dummy.setDate(dtf.format(now));
                        dummy.setLocation("N/A");
                        database.addTransaction(dummy);
                        Toast.makeText(TransactionActivity.this,"Транцакцията беше изпълнена",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        binding.transactionsExpense.setOnClickListener(
                new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (database.getLastTotal() - Double.parseDouble(binding.transactionsCost.getText().toString()) < 0)
                            insufficientFunds(TransactionActivity.this);
                        else {
                            lastTotal = database.getLastTotal();
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            now = LocalDateTime.now();
                            dummy.setDescription(binding.transactionsDescription.getText().toString());
                            dummy.setAmount(Double.parseDouble(binding.transactionsCost.getText().toString()));
                            dummy.setTotal(lastTotal - dummy.getAmount());
                            dummy.setDirection("Out");
                            dummy.setDate(dtf.format(now));
                            dummy.setLocation(address);
                            database.addTransaction(dummy);
                            Toast.makeText(TransactionActivity.this,"Транцакцията беше изпълнена",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void insufficientFunds(Context context){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle("Недостатъчни средства");
        alert.setMessage("Не разполагате с нужните средства, да не би да сте взели пари назаем");
        alert.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i= new Intent(context,DebtsActivity.class);
                startActivity(i);
            }
        });
        alert.setNegativeButton("Не", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"Моля проверете за грешно въведена цена", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(TransactionActivity.this, Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address = addresses.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(TransactionActivity.this,"Грешка при взимане на локация",Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,5,TransactionActivity.this);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(TransactionActivity.this,"Грешка при взимане на локация",Toast.LENGTH_SHORT).show();
        }
    }

}
