package com.example.water_app;

public class WaterConsumption {
    private int containerId;
    private int amount;
    private String date;

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WaterConsumption(int amount, String name, String currentDate){}
}
