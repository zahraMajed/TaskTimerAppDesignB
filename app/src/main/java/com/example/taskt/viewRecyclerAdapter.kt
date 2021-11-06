package com.example.taskt

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.taskt.RoomDB.TasksTable
import kotlinx.android.synthetic.main.item_view.view.*

class viewRecyclerAdapter (val activity: ViewTasks, val TaskList:List<TasksTable>): RecyclerView.Adapter<viewRecyclerAdapter.itemViewHolder>() {
    class itemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    var runningTask:Chronometer ?=null

    var oldTask:TasksTable?=null
    var oldStratbtn:ImageView?=null
    var oldImage2:LottieAnimationView?=null

    var running=false
    var pauseOffset: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))

    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val TaskObj=TaskList[position]

        val TaskId=TaskList[position].id
        val TaskName=TaskList[position].taksName
        val TaskDes=TaskList[position].taskDescription
        val TaskTime=TaskList[position].taskTime


        holder.itemView.apply {
            tvTaskNameRV.text=TaskName


            FABtimerr.setOnClickListener(){
                LL1RV.visibility=View.GONE
                LL2RV.visibility=View.VISIBLE
                tvTaskNameTimer.text=TaskName
                tvTaskDescriptionTimer.text=TaskDes
            }//end listener

            startbutton.setOnClickListener {
                if (runningTask==null){
                    startStopSelf(chronometer,TaskObj,startbutton,imageView2)
                }else
                {
                    if (runningTask==chronometer){
                        startStopSelf(chronometer,TaskObj,startbutton,imageView2)
                    }else{
                        startStopB(chronometer,TaskObj,startbutton,imageView2)
                    }
                }//end 1st else
            }//end startbutton

            //it should be close icon instead of LL2RV
            LL2RV.setOnClickListener(){
                LL1RV.visibility=View.VISIBLE
                LL2RV.visibility=View.GONE
            }

            if (activity.isBtnSumarryClick){
                activity.btnSummaryTask.visibility=View.INVISIBLE
                activity.tvWelcomeView.text="Tasks Summary"
                if (TaskTime.isNotEmpty()){
                    tvTaskTimeRVD.visibility=View.VISIBLE
                    FABtimerr.visibility=View.GONE
                    tvTaskTimeRVD.text=TaskTime
                }else{
                    FABtimerr.visibility=View.VISIBLE
                    tvTaskTimeRVD.visibility=View.GONE
                }

            }else
            {
                activity.btnSummaryTask.visibility=View.VISIBLE
                activity.tvWelcomeView.text="ToDo"
            }//end if

        }//end holder
    }//end onBindViewHolder()

    override fun getItemCount(): Int=TaskList.size

    fun startStopSelf(chronometer: Chronometer,taskObj: TasksTable,startbutton:ImageView, imageView2:LottieAnimationView ){
        if (!running){
            chronometer.base= SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            running=true
            runningTask=chronometer

            oldTask=taskObj
            oldStratbtn=startbutton
            oldImage2=imageView2
            //
            imageView2.playAnimation()
            //update btns
            startbutton.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
        }else{
            chronometer.stop()
            running=false
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
            startbutton.background= ContextCompat.getDrawable(activity, R.drawable.not_started)
            imageView2.pauseAnimation()
            taskObj.taskTime=chronometer.text.toString()
            activity.updateTime(taskObj.id,taskObj.taskTime)
        }
    }//startStopSelf

    fun startStopB(chronometer: Chronometer,taskObj: TasksTable,startbutton:ImageView, imageView2:LottieAnimationView ){
        //stop the running task and save its time in database
        runningTask?.stop()
        oldTask!!.taskTime=runningTask!!.text.toString()
        oldStratbtn!!.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
        oldImage2!!.pauseAnimation()
        activity.updateTime(oldTask!!.id,oldTask!!.taskTime)

        chronometer.base= SystemClock.elapsedRealtime() - 0
        chronometer.start()

        startbutton.background= ContextCompat.getDrawable(activity, R.drawable.pause_circle_filled)
        imageView2.playAnimation()

        oldTask=taskObj
        runningTask=chronometer
    }//startStopB

}//end class