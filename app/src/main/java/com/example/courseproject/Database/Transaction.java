package com.example.courseproject.Database;

public class Transaction {
    private int id;
    private String description;
    private double amount;
    private double total;
    private String date;
    private String direction;

    private String location;


    public Transaction(int id, String description, double amount, double total, String date, String direction, String location) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.total = total;
        this.date = date;
        this.direction = direction;
        this.location = location;
    }

    public Transaction(String description, double amount, double total, String date, String direction, String location) {
        this.description = description;
        this.amount = amount;
        this.total = total;
        this.date = date;
        this.direction = direction;
        this.location = location;
    }

    public Transaction() {
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

    public double getTotal() {
        return total;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
