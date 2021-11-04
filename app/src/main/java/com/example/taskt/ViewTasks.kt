package com.example.taskt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
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
    var running = false
    var pauseOffset: Long = 0
    var taskTime=""
    var num = 1

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

    fun updateTime(id:Int,time:String){
        CoroutineScope(Dispatchers.IO).launch {
            tasksDB.getTasksDao().updateTaskTime(id, time)
        }
    }//end updateTime()

    ////////////////////////Adapter method//////////////////////////

    fun Timer(chronometer: Chronometer) {
        chronometer.setFormat("Time: %s")
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                if (SystemClock.elapsedRealtime() - chronometer.base >= 10000) {
                    chronometer.base = SystemClock.elapsedRealtime()
                }//end startTimer
            }
    }//end Timer

    fun pasuse(chronometer: Chronometer, TaskId:Int):Boolean {
        var ishideLL2RV=false
        if (num==2){
            if (running){
                chronometer.stop();
                running = false;
                num--
                ishideLL2RV=true
                taskTime=chronometer.text.toString()
                updateTime(TaskId, taskTime)
            }//end if
        }
        return ishideLL2RV
    }//end pasuse

    fun start(chronometer: Chronometer, taskName:String){
        var runningTask = ""
        if (num == 1) {
            if (!running) {
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                chronometer.start();
                running = true;
                num++
                runningTask = taskName
            }
        }else {
            Toast.makeText(applicationContext, "Please finish/stop $runningTask first", Toast.LENGTH_SHORT).show()
        }
    }//end start()

}//end class