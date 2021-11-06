package com.example.taskt.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tasks")
data class TasksTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id : Int = 0,

    @ColumnInfo(name = "TaskName")
    val taksName: String,

    @ColumnInfo(name = "TaskDescription")
    val taskDescription: String,

    @ColumnInfo(name = "TaskTime")
    var taskTime: String
)
