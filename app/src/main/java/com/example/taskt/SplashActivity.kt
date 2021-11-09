package com.example.taskt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.taskt.RoomDB.TasksDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var tasksDB: TasksDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        tasksDB= TasksDatabase.getInstance(this)
        handler= Handler()
        handler.postDelayed({
            intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 6600)
        clearDB()
    }

    fun clearDB(){
        CoroutineScope(Dispatchers.IO).launch {
            tasksDB.getTasksDao().clearTaskTable()
        }
    }//end updateTime()

}