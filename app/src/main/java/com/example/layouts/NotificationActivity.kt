package com.example.layouts

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notificationId = 1
        btnShowNotif.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, getNotification())
            }
        }

        btnShowProgress.setOnClickListener {
            val builder = getNotificationProgress()
            builder.setProgress(100,12,false)
            with(NotificationManagerCompat.from(this)) {
                notify(2, builder.build())
            }

        }
        btnENotif.setOnClickListener {

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, getExpandableNotification())
            }
        }




    }

  private fun getNotification(): Notification {

        val intent = Intent(this, NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "1").apply {
                setSmallIcon(R.drawable.com_facebook_button_icon)
                setContentTitle("Test Notification")
                setContentText("Anything")

            }
        } else {
            Notification.Builder(this).apply {
                setSmallIcon(R.drawable.com_facebook_button_icon)
                setContentTitle("Test Notification")
                setContentText("Anything")
                setContentIntent(pendingIntent)
                setAutoCancel(true)
            }
        }
        return builder.build()
    }

  private fun getNotificationProgress():Notification.Builder{


        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "1").apply {
                setSmallIcon(R.drawable.com_facebook_button_icon)
                setContentTitle("Test Notification")
                setContentText("Anything")
            }
        } else {
            Notification.Builder(this).apply {
                setSmallIcon(R.drawable.com_facebook_button_icon)
                setContentTitle("Test Notification")
                setContentText("Anything")
                setPriority(Notification.PRIORITY_LOW)


            }
        }

        return builder
    }

  private fun getExpandableNotification():Notification {

      val bitmap = BitmapFactory.decodeResource(resources,R.drawable.man_image)
      val builder = Notification.Builder(this).apply {
          setSmallIcon(R.drawable.com_facebook_button_icon)
          setContentTitle("Test Notification")
          setContentText("Anything")
          setStyle(Notification.BigPictureStyle().bigPicture(bitmap))

      }

      return builder.build()
  }

 private fun getbubble(){

 }



    companion object {
        const val KEY_TEXT = "key_text_reply"
    }


}