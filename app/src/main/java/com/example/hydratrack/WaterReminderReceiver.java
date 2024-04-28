package com.example.hydratrack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;



public class WaterReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "water_reminder_channel";
    public static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create notification channel (required for Android Oreo and above)
        createNotificationChannel(context);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Hydration Reminder")
                .setContentText("Remember to drink water!")
                .setSmallIcon(R.drawable.notifs_icon)
                .setColor(ContextCompat.getColor(context, R.color.blue))
                .setAutoCancel(true);

        // Set custom sound for the notification
        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.water_droplet);
        builder.setSound(soundUri);

        // Create intent to launch MainActivity
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Water Reminder Channel";
            String description = "Channel for water reminder notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);

            // Set custom sound for the notification channel
            Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.water_droplet);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(soundUri, attributes);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendNotification(Context context) {
        Intent intent = new Intent(context, WaterReminderReceiver.class);
        context.sendBroadcast(intent);
    }
}
