package com.example.taskt.RoomDB

import androidx.room.*

@Dao
interface TasksDao {
    @Query("SELECT * FROM Tasks")
    fun getAll(): List<TasksTable>

    @Insert
    fun insertTask(task:TasksTable)

    @Update
    fun updateTask(taks: TasksTable)

    @Query("UPDATE Tasks SET TaskTime=:taskTime  WHERE ID= :taksId")
    fun updateTaskTime(taksId:Int,taskTime:String)

    @Query("UPDATE Tasks SET isDone=:isDone  WHERE ID= :taksId")
    fun updateIsDone(taksId:Int,isDone:Boolean)

    @Delete
    fun delTask(task: TasksTable)
}
