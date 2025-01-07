//package com.example.comp2000cs;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//public class MyFirebaseMessagingService {
//    public static void handleIncomingNotification(Context context, String title, String body) {
//        Log.d("NotificationHandler", "Preparing to show notification...");
//
//        String channelId = "admin_notifications";
//
//        // Create a NotificationChannel (for Android 8.0+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    "Admin Notifications",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            if (manager != null) {
//                manager.createNotificationChannel(channel);
//                Log.d("NotificationHandler", "Notification channel created.");
//            }
//        }
//
//        // Create an Intent to open the Admin dashboard or relevant activity
//        Intent intent = new Intent(context, AdminNameRequest.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        // Build the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
//                .setSmallIcon(R.drawable.notifsbell2) // Replace with your app's icon
//                .setContentTitle(title)
//                .setContentText(body)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent);
//
//        Log.d("NotificationHandler", "Notification built, displaying now...");
//
//        // Display the notification
//        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
//        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//            Log.w("NotificationHandler", "Missing POST_NOTIFICATIONS permission.");
//            return;
//        }
//        manager.notify(1, builder.build());
//
//        Log.d("NotificationHandler", "Notification displayed.");
//    }
//}
