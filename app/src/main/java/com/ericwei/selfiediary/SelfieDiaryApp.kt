package com.ericwei.selfiediary

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.ericwei.selfiediary.notification.AppBroadcastReceiver
import java.util.*

class SelfieDiaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val receiver = ComponentName(applicationContext, AppBroadcastReceiver::class.java)

        applicationContext.packageManager.setComponentEnabledSetting(
            receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )

        val alarmActive = Intent(applicationContext, AppBroadcastReceiver::class.java).let { intent ->
            intent.action = "com.ericwei.selfiediary.action.SHOW_NOTIFICATION"
            PendingIntent.getBroadcast(applicationContext, 0, intent, PendingIntent.FLAG_NO_CREATE)
        }
        if (alarmActive == null) {
            registerNotificationAlarm(applicationContext)
        }
    }

    companion object {
        fun registerNotificationAlarm(context: Context) {
            val showNotificationIntent = Intent(context, AppBroadcastReceiver::class.java).let { intent ->
                intent.action = "com.ericwei.selfiediary.action.SHOW_NOTIFICATION"
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            var alarmMgr = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 16)
                set(Calendar.MINUTE, 30)
            }

            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                showNotificationIntent
            )
//            alarmMgr?.setExact(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 5 * 1000,
//                showNotificationIntent
//            )
        }
    }
}