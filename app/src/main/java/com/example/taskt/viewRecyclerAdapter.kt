package com.example.taskt

import android.app.ActivityManager
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskt.RoomDB.TasksTable
import kotlinx.android.synthetic.main.item_view.view.*

class viewRecyclerAdapter (val activity: ViewTasks, val TaskList:List<TasksTable>): RecyclerView.Adapter<viewRecyclerAdapter.itemViewHolder>() {
    class itemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        return itemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))

    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {

        val TaskObj=TaskList[position]
        val TaskId=TaskList[position].id
        val TaskName=TaskList[position].taksName
        val TaskDes=TaskList[position].taskDescription
        val TaskTime=TaskList[position].taskTime

        var running = false
        var pauseOffset: Long = 0
        var taskTime=""

        holder.itemView.apply {
            tvTaskNameRV.text=TaskName

            FABtimer.setOnClickListener(){
                LL1RV.visibility=View.GONE
                LL2RV.visibility=View.VISIBLE
                tvTaskNameTimer.text=TaskName
                tvTaskDescriptionTimer.text=TaskDes
                activity.Timer(chronometer)
            }//end listener

            startbutton.setOnClickListener {
                activity.start(chronometer,TaskName)
            }


            pausebutton.setOnClickListener {
                if (activity.pasuse(chronometer,TaskId))
                    LL1RV.visibility=View.VISIBLE
                LL2RV.visibility=View.GONE
            }

            LL2RV.setOnClickListener(){
                LL1RV.visibility=View.VISIBLE
                LL2RV.visibility=View.GONE
            }

            if (activity.isBtnSumarryClick){
                activity.btnSummaryTask.visibility=View.INVISIBLE
                activity.tvWelcomeView.text="Tasks Summary"
                if (TaskTime.isNotEmpty()){
                    tvTaskTimeRVD.visibility=View.VISIBLE
                    FABtimer.visibility=View.GONE
                    tvTaskTimeRVD.text=TaskTime
                }else{
                    FABtimer.visibility=View.VISIBLE
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
}//end class