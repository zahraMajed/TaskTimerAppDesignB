package com.example.taskt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.taskt.RoomDB.TasksDatabase

class MainActivity : AppCompatActivity() {
    lateinit var btnViewTaskMain:Button
    lateinit var btnInsertMain:Button
    lateinit var tasksDB: TasksDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnViewTaskMain=findViewById(R.id.btnViewTaskMain)
        btnInsertMain=findViewById(R.id.btnInsertMain)
        tasksDB= TasksDatabase.getInstance(this)

        btnInsertMain.setOnClickListener(){
            intent= Intent(this, AddTask::class.java)
            startActivity(intent)
            //finish()
        }

        btnViewTaskMain.setOnClickListener(){
            //firt check if task table is empty and do an action
            if (!tasksDB.getTasksDao().getAll().isNullOrEmpty()){
                intent= Intent(this, ViewTasks::class.java)
                startActivity(intent)
                //finish()
            }else{
                Toast.makeText(applicationContext, "There is no task yet, add task first! ", Toast.LENGTH_SHORT).show()
            }
        }//end listener

    }//end onCreate()

}//end class