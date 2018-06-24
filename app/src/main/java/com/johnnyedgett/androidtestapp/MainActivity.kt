package com.johnnyedgett.androidtestapp

import android.app.Notification
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel("com.johnnyedgett.androidtestapp.test","Johnny's Test Notification", "Example Test Channel")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        kotlinNotificationTest()
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createNotificationChannel(id: String, name: String, description: String){
        if(Build.VERSION.SDK_INT>=26) {
            val importance = NotificationManager.IMPORTANCE_LOW

            // Note! Review the types of notification channels in older Android environments
            // Channel was added in 26 and the minimum API level specified to be supported in the build is 19
            val channel = NotificationChannel(id, name, importance)

            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
            Log.i("Channel created", "A notification channel was created")
        }
        else{
            Log.i("Didn't create channel", "Build version: ${Build.VERSION.SDK_INT}")
            println("Unable to create channel, going to have issues")
        }


    }

    fun kotlinNotificationTest(){
        val notificationID = 101
        val channelId = "com.johnnyedgett.androidtestapp.test"
        if(Build.VERSION.SDK_INT>=26) {
            val notification = Notification.Builder(this, channelId)
                    .setContentTitle("Sandwich Clicked!")
                    .setContentText("I'm hungry now!")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setChannelId(channelId)
                    .build()

            notificationManager?.notify(notificationID, notification)
        }
        else
            println("Panic")
        Log.i("Function ran", "kotlinNotificationTest ran")


    }
}