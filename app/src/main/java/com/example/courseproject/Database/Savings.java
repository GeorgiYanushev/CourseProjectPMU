package com.example.courseproject.Database;

public class Savings {
    private int id;
    private String description; //cel
    private double amount; //subrani
    private double total; // cel v pari
    private String completed;

    public Savings(int id, String description, double amount, double total, String completed) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.total = total;
        this.completed = completed;
    }

    public Savings(String description, double amount, double total, String completed) {
        this.description = description;
        this.amount = amount;
        this.total = total;
        this.completed = completed;
    }
    public Savings(){}

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
