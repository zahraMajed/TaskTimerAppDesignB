package com.example.taskt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.taskt.RoomDB.TasksDatabase
import com.example.taskt.RoomDB.TasksTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//here users should be able to add tasks as many as they need, then they should be able to go bask
class AddTask : AppCompatActivity() {

    lateinit var edTaskInsert:EditText
    lateinit var edTaskDesInsert:EditText
    lateinit var btnSaveInsert:Button
    lateinit var tasksDB: TasksDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        edTaskInsert=findViewById(R.id.edTaskInsert)
        edTaskDesInsert=findViewById(R.id.edTaskDesInsert)
        btnSaveInsert=findViewById(R.id.btnSaveInsert)

       tasksDB= TasksDatabase.getInstance(this)

        btnSaveInsert.setOnClickListener(){
            if (edTaskInsert.text.isNotEmpty() && edTaskDesInsert.text.isNotEmpty()){
                var taskName=edTaskInsert.text.toString(); var taskDes=edTaskDesInsert.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                   tasksDB.getTasksDao().insertTask(TasksTable(0,taskName,taskDes,"", false))
                }//end CoroutineScope
                Toast.makeText(applicationContext, "Task saved successfully!", Toast.LENGTH_SHORT).show()
                edTaskInsert.text.clear(); edTaskDesInsert.text.clear()
            }else
                Toast.makeText(applicationContext, "Please fill all entries!", Toast.LENGTH_SHORT).show()
        }//end btnSaveInsert
    }//end onCreate()

}//end class