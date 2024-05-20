package com.example.water_app;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class User {
    public int dailyWaterGoal;
    public Map<String, Map<String, WaterRecord>> waterConsumption;

    public User() {
        // Default constructor required for Firebase
    }

    public User(int dailyWaterGoal) {
        this.dailyWaterGoal = dailyWaterGoal;
        this.waterConsumption = new HashMap<>();
    }

    public int getConsumedWaterForCurrentDate() {
        String currentDate = getCurrentDate();
        Map<String, WaterRecord> consumptionForCurrentDate = waterConsumption.get(currentDate);
        if (consumptionForCurrentDate == null) {
            return 0;
        }
        int totalConsumedWater = 0;
        for (WaterRecord record : consumptionForCurrentDate.values()) {
            totalConsumedWater += record.amount;
        }
        return totalConsumedWater;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static class WaterRecord {
        public int amount;
        public String containerId;
        public String timestamp;

        public WaterRecord() {
            // Default constructor required for Firebase
        }

        public WaterRecord(int amount, String containerId, String timestamp) {
            this.amount = amount;
            this.containerId = containerId;
            this.timestamp = timestamp;
        }
    }
}
