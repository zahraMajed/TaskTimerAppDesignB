package com.example.taskt

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.marginStart
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

    var isCheckDone=false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))

    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val TaskObj=TaskList[position]

        val TaskId=TaskList[position].id
        val TaskName=TaskList[position].taksName
        val TaskDes=TaskList[position].taskDescription
        val TaskTime=TaskList[position].taskTime
        val TisDone=TaskList[position].isDone

        holder.itemView.apply {
            tvTaskNameRV.text=TaskName

            if(TisDone){
                imageViewRV.playAnimation()
            }

            //if user want to edit its task
            FABedit.setOnClickListener(){
                LL1RV.visibility=View.GONE
                LL3RV.visibility=View.VISIBLE
                edTaskInsertRV.setText(TaskName)
                edTaskDesInsertRV.setText(TaskDes)
            }

            btnSaveInsertRV.setOnClickListener(){
                activity.updateTask(TasksTable(TaskId,edTaskInsertRV.text.toString(), edTaskDesInsertRV.text.toString(),TaskTime,TisDone))
            }

            //it should be close icon instead of LL2RV
            FABclose.setOnClickListener(){
                LL1RV.visibility=View.VISIBLE
                LL3RV.visibility=View.GONE
            }

            //if user want to start timer
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
            FABcloseLL2RV.setOnClickListener(){
                LL1RV.visibility=View.VISIBLE
                LL2RV.visibility=View.GONE
            }

            imageViewRV.setOnClickListener(){
                if (isCheckDone){
                    imageViewRV.speed= -1F
                    imageViewRV.playAnimation()
                    isCheckDone=false
                    activity.updateIsDone(TaskId,false)
                }else{
                    imageViewRV.speed= 1F
                    imageViewRV.playAnimation()
                    isCheckDone=true
                    activity.updateIsDone(TaskId,true)
                }
            }

            if (activity.isBtnSumarryClick){
                activity.btnSummaryTask.visibility=View.INVISIBLE
                activity.tvWelcomeView.text="Tasks Summary"
                if (TaskTime.isNotEmpty()){
                    tvTaskTimeRVD.visibility=View.VISIBLE
                    tvTaskTimeRVD.text=TaskTime
                    FABtimerr.visibility=View.GONE
                    FABedit.visibility=View.GONE
                    imageViewRV.visibility=View.GONE
                }else{
                    FABtimerr.visibility=View.VISIBLE
                    FABedit.visibility=View.GONE
                    imageViewRV.visibility=View.GONE
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