package com.example.courseproject.Database;

public class Debt {
    private int id;
    private String description;
    private double amount;
    private String date;
    private String isPaid;

    private String location;

    public Debt(int id, String description, int amount, String date, String isPaid, String location) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
        this.location = location;
    }

    public Debt(String description, int amount, String date, String isPaid, String location) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
        this.location = location;
    }
    public Debt(){}

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

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
