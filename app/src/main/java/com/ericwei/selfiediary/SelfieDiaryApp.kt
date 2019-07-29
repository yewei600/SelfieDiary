package com.ericwei.selfiediary

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ericwei.selfiediary.notification.AppBroadcastReceiver
import java.util.*

class SelfieDiaryApp : Application(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreate() {
        super.onCreate()
        val receiver = ComponentName(applicationContext, AppBroadcastReceiver::class.java)

        applicationContext.packageManager.setComponentEnabledSetting(
            receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )

        PreferenceManager.getDefaultSharedPreferences(applicationContext).registerOnSharedPreferenceChangeListener(this)

        val alarmActive = Intent(applicationContext, AppBroadcastReceiver::class.java).let { intent ->
            intent.action = "com.ericwei.selfiediary.action.SHOW_NOTIFICATION"
            PendingIntent.getBroadcast(
                applicationContext,
                PENDING_INTENT_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_NO_CREATE
            )
        }
        if (alarmActive == null) {
            Toast.makeText(applicationContext, "SelfieDiary CREATE alarm@@@@@@", Toast.LENGTH_LONG).show()
            registerNotificationAlarm(applicationContext)
        } else {
            Toast.makeText(applicationContext, "SelfieDiary alarm exists=====__=====", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSharedPreferenceChanged(preference: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.saved_notification_time) -> {
                cancelNotificationAlarm(applicationContext)
                registerNotificationAlarm(applicationContext)
            }
            getString(R.string.notification_enable) ->
                if (preference!!.getBoolean(key, false)) {
                    registerNotificationAlarm(applicationContext)
                } else {
                    cancelNotificationAlarm(applicationContext)
                }
        }
    }

    companion object {

        private val TAG = SelfieDiaryApp::class.java.simpleName

        val PENDING_INTENT_REQUEST_CODE = 333

        fun getAlarmIntent(context: Context): PendingIntent {
            return Intent(context, AppBroadcastReceiver::class.java).let { intent ->
                intent.action = "com.ericwei.selfiediary.action.SHOW_NOTIFICATION"
                PendingIntent.getBroadcast(
                    context,
                    PENDING_INTENT_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun registerNotificationAlarm(context: Context) {
            var alarmMgr = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val notifTime = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.saved_notification_time), "17:00").split(":")
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, notifTime[0].toInt())
                set(Calendar.MINUTE, notifTime[1].toInt())
            }

            alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_HALF_HOUR,
                getAlarmIntent(context)
            )
//            alarmMgr?.setExact(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + 5 * 1000,
//                showNotificationIntent
//            )

            Toast.makeText(
                context, "CREATE ALARM at " + calendar.get(Calendar.HOUR_OF_DAY) + " " +
                        calendar.get(Calendar.MINUTE), Toast.LENGTH_LONG
            ).show()
        }

        fun cancelNotificationAlarm(context: Context) {
            var alarmMgr = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmMgr.cancel(getAlarmIntent(context))
        }
    }
}