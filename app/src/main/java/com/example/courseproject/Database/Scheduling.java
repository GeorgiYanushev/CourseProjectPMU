package com.example.courseproject.Database;

public class Scheduling {
    private int id;
    private String description;
    private double amount;
    private String date;
    private String nextDate;
    private String status;
    private String periodicity;

    public Scheduling(int id, String description, double amount, String date, String nextDate, String status, String periodicity) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.nextDate = nextDate;
        this.status = status;
        this.periodicity = periodicity;
    }

    public Scheduling(String description, double amount, String date, String nextDate, String status, String periodicity) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.nextDate = nextDate;
        this.status = status;
        this.periodicity = periodicity;
    }
    public Scheduling(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
}
