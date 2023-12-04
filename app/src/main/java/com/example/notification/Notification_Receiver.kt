package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Notification_Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = intent?.getStringExtra("MESSAGE")
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

