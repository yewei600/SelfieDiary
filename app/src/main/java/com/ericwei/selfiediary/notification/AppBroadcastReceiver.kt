package com.ericwei.selfiediary.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.SelfieDiaryApp
import com.ericwei.selfiediary.ui.MainActivity

class AppBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent!!.action) {
            "android.intent.action.BOOT_COMPLETED" -> {
                SelfieDiaryApp.registerNotificationAlarm(context!!)
                Toast.makeText(context, "BOOT completed from SelfieDiary app!!!", Toast.LENGTH_SHORT).show()
            }
            "com.ericwei.selfiediary.action.SHOW_NOTIFICATION" -> {

                val CHANNEL_ID = "Daily Selfie Reminder"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "SelfieDiary"
                    val descriptionText = "A channel which shows a notification to remind user to take daily selfie"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                        description = descriptionText
                    }

                    val notificationManager =
                        context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }

                val intent = Intent(context!!, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    putExtra("fromNotification", true)
                }
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

                val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setContentTitle("Selfie Diary")
                    .setContentText("Time to take selfie!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(context)) {
                    notify(123, builder.build())
                }

            }
        }
    }

}