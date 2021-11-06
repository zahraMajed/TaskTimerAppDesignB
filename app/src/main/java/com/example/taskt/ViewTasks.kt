package com.example.taskt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskt.RoomDB.TasksDatabase
import kotlinx.android.synthetic.main.activity_view_tasks.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

 class ViewTasks : AppCompatActivity() {
    //our views
    lateinit var btnSummaryTask:Button
    lateinit var tvWelcomeView:TextView
    //our database
    lateinit var tasksDB: TasksDatabase
    //our variables
    var isBtnSumarryClick=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tasks)

        btnSummaryTask=findViewById(R.id.btnSummaryTask)
        tvWelcomeView=findViewById(R.id.tvWelcomeView)

        tasksDB= TasksDatabase.getInstance(this)

        getTasks()

        btnSummaryTask.setOnClickListener() {
            isBtnSumarryClick=true
            getTasks()
        }// end listener

    }//end onCreate()

    fun getTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                if (!tasksDB.getTasksDao().getAll().isNullOrEmpty()) {
                   rv_main.adapter=viewRecyclerAdapter(this@ViewTasks,tasksDB.getTasksDao().getAll())
                    rv_main.layoutManager=LinearLayoutManager(this@ViewTasks)
                }////write else part
            }
        }//end CoroutineScope
    }//end getTasks()


    ////////////////////////Adapter method//////////////////////////

     fun updateTime(id:Int,time:String){
         CoroutineScope(Dispatchers.IO).launch {
             tasksDB.getTasksDao().updateTaskTime(id, time)
         }
     }//end updateTime()


}//end class



