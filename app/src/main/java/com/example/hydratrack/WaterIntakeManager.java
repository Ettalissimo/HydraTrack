package com.example.hydratrack;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class WaterIntakeManager {
    private Context mContext;
    private double waterObjective;
    private double waterIntake; // En mL

    private static final int PERMISSION_REQUEST_CODE = 1001;

    public WaterIntakeManager(Context context, double waterObjective) {
        this.mContext = context;
        this.waterObjective = waterObjective;
        this.waterIntake = 0.0;
    }

    public void recordWaterConsumption(double amount) {
        this.waterIntake += amount;
        checkWaterIntakeProgress();
    }

    private void checkWaterIntakeProgress() {
        double progress = waterIntake / waterObjective;
        if (progress >= 0.25 && progress < 0.5) {
            // Trigger notification for 25% checkpoint
            sendNotification("25% of your daily water goal achieved!");
        } else if (progress >= 0.5 && progress < 0.75) {
            // Trigger notification for 50% checkpoint
            sendNotification("50% of your daily water goal achieved!");
        } else if (progress >= 0.75 && progress < 1.0) {
            // Trigger notification for 75% checkpoint
            sendNotification("75% of your daily water goal achieved!");
        } else if (progress >= 1.0) {
            // Trigger notification for reaching daily goal
            sendNotification("Congratulations! You've reached your daily water goal!");
        }
    }

    private void sendNotification(String message) {
        // Create notification channel if running on Android Oreo or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WaterIntakeChannel";
            String description = "Channel for Water Intake notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "channel_id")
                .setSmallIcon(R.drawable.notifs_icon)
                .setContentTitle("Water Intake Tracker")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true); // Auto cancel the notification when tapped

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
