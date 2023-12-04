package com.example.notification
import com.gun0912.tedpermission.PermissionBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import android.Manifest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notification.databinding.ActivityMainBinding

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import android.os.Build
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationManagerCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.permission.setOnClickListener {
            requestPermission {
                notification()
            }
        }
    }

    private fun requestPermission(logic : () -> Unit){
        TedPermission.create()
            .setPermissionListener(object  : PermissionListener {
                override fun onPermissionGranted() {
                    logic()
                }
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@MainActivity, "권한을 허가해주세요", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
            .check()
                //     implementation 'io.github.ParkSangGwon:tedpermission-normal:3.3.0' (gradle > dependencies 추가)
        }

    private fun notification(){
        val builder: NotificationCompat.Builder
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Builder
            builder = NotificationCompat.Builder(this, "CHANNEL_ID")

            // Channel
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            val channel = NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", importance).apply {
                description = "Extra builder setting"

                // 부과 설정들...
                setShowBadge(true)
                setSound(uri, audioAttributes)
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100,200,100,200)
            }
            manager.createNotificationChannel(channel)

        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder.setSmallIcon(androidx.customview.R.drawable.notification_bg_low)
        builder.setContentTitle("알림제목")
        builder.setContentText("알림내용")
        builder.setWhen(System.currentTimeMillis())

        val intent = Intent(this, Notification_Receiver::class.java).apply {
            putExtra("MESSAGE", "Clicked!")
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        builder.addAction(0, "Action",pendingIntent)
        manager.notify(1,builder.build())

    }

}
