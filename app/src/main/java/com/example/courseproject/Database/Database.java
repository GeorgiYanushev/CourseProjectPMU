package com.example.courseproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    // Име на базата
    private static final String DATABASE_NAME = "Db";
    private static final String TABLE_DEBTS = "DEBTS";
    private static final String TABLE_Transaction = "TRANSACTIONS";
    private static final String TABLE_LOG_Transaction = "LOG_TRANSACTION";
    private static final String TABLE_Savings = "SAVINGS";
    private static final String TABLE_Scheduling = "Scheduling";
    // Имена на колоните на Таблицата
    private static final String dId = "id";
    private static final String dDescription = "Description";
    private static final String dAmount = "amount";
    private static final String dDate = "date";
    private static final String dIsPaid = "isPaid";
    private static final String dLocation = "location";

    private static final String tId = "id";
    private static final String tDescription = "Description";
    private static final String tAmount = "amount";
    private static final String tDate = "date";
    private static final String tDirection = "direction";
    private static final String tTotal = "total";
    private static final String tLocation = "location";

    private static final String sId = "id";
    private static final String sDescriptions = "Description" ; //cel
    private static final String sAmount = "amount"; //subrani
    private static final String sTotal = "total"; // cel v pari
    private static final String sCompleted = "completed";

    private static final String shid = "id";
    private static final String shdescription = "Description";
    private static final String shamount = "amount";
    private static final String shdate = "startDate";
    private static final String shnextDate = "nextDate";
    private static final String shstatus = "status";
    private static final String shperiodicity = "periodicity";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Transaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG_Transaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Savings);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Scheduling);
        String CREATE_DEBTS_TABLE = "CREATE TABLE " + TABLE_DEBTS +
                "("
                + dId + " INTEGER PRIMARY KEY,"
                + dDescription + " TEXT,"
                + dAmount + " TEXT,"
                + dDate + " TEXT,"
                + dIsPaid + " TEXT,"
                + dLocation + " TEXT" +
                ")";
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_Transaction +
                "("
                + tId + " INTEGER PRIMARY KEY,"
                + tDescription + " TEXT,"
                + tAmount + " TEXT,"
                + tDate + " TEXT,"
                + tTotal + " TEXT,"
                + tDirection + " TEXT,"
                + tLocation + " TEXT" +
                ")";
        String CREATE_LOG_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_LOG_Transaction +
                "("
                + tId + " INTEGER PRIMARY KEY,"
                + tDescription + " TEXT,"
                + tAmount + " TEXT,"
                + tDate + " TEXT,"
                + tTotal + " TEXT,"
                + tDirection + " TEXT,"
                + tLocation + " TEXT" +
                ")";

        String CREATE_SAVINGS_TABLE = "CREATE TABLE " + TABLE_Savings +
                "("
                + sId + " INTEGER PRIMARY KEY,"
                + sDescriptions + " TEXT,"
                + sAmount + " TEXT,"
                + sTotal + " TEXT,"
                + sCompleted + " TEXT" +
                ")";

        String CREATE_SCHEDULING_TABLE = "CREATE TABLE " + TABLE_Scheduling +
                "("
                + shid + " INTEGER PRIMARY KEY,"
                + shdescription + " TEXT,"
                + shamount + " TEXT,"
                + shdate + " TEXT,"
                + shnextDate + " TEXT,"
                + shstatus + " TEXT,"
                + shperiodicity + " TEXT" +
                ")";

        db.execSQL(CREATE_DEBTS_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_LOG_TRANSACTION_TABLE);
        db.execSQL(CREATE_SAVINGS_TABLE);
        db.execSQL(CREATE_SCHEDULING_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Transaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG_Transaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Savings);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Scheduling);
        // Повторно създаване на базата от данни
        onCreate(db);
    }
    public void onDeleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        db.delete(TABLE_DEBTS,null,null);
        db.delete(TABLE_Transaction,null,null);
        db.delete(TABLE_LOG_Transaction,null,null);
        db.delete(TABLE_Savings,null,null);
        db.delete(TABLE_Scheduling,null,null);
        //db.close();
    }
   public void addDebt(Debt contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dDescription, contact.getDescription()); // Име на Потребител
        values.put(dAmount, contact.getAmount()); // Парола на
        values.put(dDate, contact.getDate()); // Парола на
        values.put(dIsPaid, contact.getIsPaid()); // Парола на
        values.put(dLocation, contact.getLocation()); // Парола на
        //Потребител
        // Добавяне на Ред
        db.insert(TABLE_DEBTS, null, values);
        //db.close();
    }
   public void addTransaction(Transaction contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tDescription, contact.getDescription()); // Име на Потребител
        values.put(tAmount, contact.getAmount()); // Парола на
        values.put(tDate, contact.getDate()); // Парола на
        values.put(tTotal, contact.getTotal()); // Парола на
        values.put(tDirection, contact.getDirection()); // Парола на
        values.put(tLocation, contact.getLocation()); // Парола на
        //Потребител
        // Добавяне на Ред
        db.insert(TABLE_Transaction, null, values);
        //db.close();
    }
    public void addLogTransaction(Transaction contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tDescription, contact.getDescription()); // Име на Потребител
        values.put(tAmount, contact.getAmount()); // Парола на
        values.put(tDate, contact.getDate()); // Парола на
        values.put(tTotal, contact.getTotal()); // Парола на
        values.put(tDirection, contact.getDirection()); // Парола на
        values.put(tLocation, contact.getLocation()); // Парола на
        //Потребител
        // Добавяне на Ред
        db.insert(TABLE_LOG_Transaction, null, values);
        //db.close();
    }
    public void addSchedule(Scheduling contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(shdescription, contact.getDescription()); // Име на Потребител
        values.put(shamount, contact.getAmount()); // Парола на
        values.put(shdate, contact.getDate()); // Парола на
        values.put(shnextDate, contact.getNextDate()); // Парола на
        values.put(shstatus, contact.getstatus()); // Парола на
        values.put(shperiodicity, contact.getPeriodicity()); // Парола на
        //Потребител
        // Добавяне на Ред
        db.insert(TABLE_Scheduling, null, values);
        //db.close();
    }
   public void addSavings(Savings contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(sDescriptions, contact.getDescription());
        values.put(sAmount, contact.getAmount());
        values.put(sTotal, contact.getTotal());
        values.put(sCompleted, contact.getCompleted());
        // Добавяне на Ред
        db.insert(TABLE_Savings, null, values);
        //db.close();
    }
    public int getDebtsCount() {
        String countQuery = "SELECT * FROM " + TABLE_DEBTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int res = cursor.getCount();
        cursor.close();
        // Връщане на броя
        return res;
    }
    public Double getLastTotal() {
        String countQuery = "SELECT total FROM " + TABLE_Transaction + " order by id desc limit 1;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        Double res = null;
        if (result.moveToFirst()) {
            do {
                res = result.getDouble(0);
            } while (result.moveToNext());
        }
        result.close();
        if(res==null)res=0.0;
        return res;
    }
    public ArrayList<Transaction> getTransactions(){
        String countQuery = "SELECT * FROM " + TABLE_Transaction + " where description <> 'A1B2C3D4!@#' order by id asc;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Transaction> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Transaction curr = new Transaction();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setTotal(result.getDouble(4));
                curr.setDirection(result.getString(5));
                curr.setLocation(result.getString(6));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public ArrayList<Scheduling> getScheduling(){
        String countQuery = "SELECT * FROM " + TABLE_Scheduling + " WHERE description<>'A1B2C3D4!@#';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Scheduling> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Scheduling curr = new Scheduling();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setNextDate(result.getString(4));
                curr.setstatus(result.getString(5));
                curr.setPeriodicity(result.getString(6));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public ArrayList<Transaction> getThisWeekTransactions(){
        String countQuery = "SELECT * FROM " + TABLE_Transaction + " WHERE date >= DATE('now','-7 day');";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Transaction> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Transaction curr = new Transaction();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setTotal(result.getDouble(4));
                curr.setDirection(result.getString(5));
                curr.setLocation(result.getString(6));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public Transaction lastTransaction(){
        String countQuery = "SELECT * FROM " + TABLE_LOG_Transaction + " order by id desc;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        if (result.moveToFirst()) {
                Transaction curr = new Transaction();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setTotal(result.getDouble(4));
                curr.setDirection(result.getString(5));
                curr.setLocation(result.getString(6));
                return curr;
        }
        result.close();
        return null;
    }
    public String[] getTransactionsDescriptions(){
        String countQuery = "SELECT * FROM " + TABLE_Transaction + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        List<String> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                String curr;
                curr=result.getString(1);
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res.toArray(new String[0]);
    }

    public ArrayList<Debt> getDebts(){
        String countQuery = "SELECT * FROM " + TABLE_DEBTS + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Debt> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Debt curr = new Debt();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setIsPaid(result.getString(4));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public ArrayList<Debt> getUnclearedDebts(){
        String countQuery = "SELECT * FROM " + TABLE_DEBTS + " WHERE isPaid='No' ORDER BY ID asc;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Debt> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Debt curr = new Debt();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setIsPaid(result.getString(4));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }

    public ArrayList<Transaction> getHistoryTransactions(){
        String countQuery = "SELECT * FROM " + TABLE_LOG_Transaction + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Transaction> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Transaction curr = new Transaction();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setTotal(result.getDouble(4));
                curr.setDirection(result.getString(5));
                curr.setLocation(result.getString(6));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public ArrayList<Savings> getSavings(){
        String countQuery = "SELECT * FROM " + TABLE_Savings + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Savings> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Savings curr = new Savings();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setTotal(result.getDouble(3));
                curr.setCompleted(result.getString(4));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public ArrayList<Savings> getUnCompleteSavings(){
        String countQuery = "SELECT * FROM " + TABLE_Savings + " WHERE completed='No' ORDER BY id asc;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        ArrayList<Savings> res = new ArrayList<>();
        if (result.moveToFirst()) {
            do {
                Savings curr = new Savings();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setTotal(result.getDouble(3));
                curr.setCompleted(result.getString(4));
                res.add(curr);
            } while (result.moveToNext());
        }
        result.close();
        return res;
    }
    public Scheduling GetPrivateSchedule(){
        String countQuery = "SELECT * FROM " + TABLE_Scheduling + " WHERE description='A1B2C3D4!@#' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(countQuery, null);
        if (result.moveToFirst()) {
                Scheduling curr = new Scheduling();
                curr.setId(result.getInt(0));
                curr.setDescription(result.getString(1));
                curr.setAmount(result.getDouble(2));
                curr.setDate(result.getString(3));
                curr.setNextDate(result.getString(4));
                curr.setstatus(result.getString(5));
                curr.setPeriodicity(result.getString(6));
                return curr;
            }
        result.close();
        return null;
    }
    public void updateTransaction(String desc, double amount, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update " + TABLE_Transaction + " set description = '" + desc + "', amount = " + amount + " where id = " + id);
    }
    public void updateDebt(String paid, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update " + TABLE_DEBTS + " set isPaid = '" + paid + "'" + " where id = " + id);
    }
    public void updateSaving(double total, double amount, String desc,  int id, String comp){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update " + TABLE_Savings + " set description = '" + desc + "', amount = " + amount + ", total = " + total + ", completed = '" + comp + "'" + " where id = " + id);
    }
    public void updateScheduling(double amount, String desc,  int id, String nextDate, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update " + TABLE_Scheduling + " set description = '" + desc + "', amount = " + amount + ", nextDate = '" + nextDate + "', status = '" + status + "'" + " where id = " + id);
    }
    public void deleteFromTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_Transaction + " where id = " + id);
    }
    public void deleteSchedule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_Scheduling + " where id = " + id);
    }
}
