package com.example.taskt.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//step 6: create database abstract class
/*
Contains the database holder and serves as the main access point
for the underlying connection to your app’s persisted, relational data.
 */

/*1) this abstract class should extend RoomDatabase
and annotated with @Database (lists the entities contained in the database (it take them in array) version and exportSchem)*/

@Database(entities= [TasksTable::class], version = 1, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {
    //2 create variable and fun that return instance of this class to be used at run time ant to get access to Dao
    companion object{
        var instance:TasksDatabase?=null

        //this method will take context and return instance of this class
        fun getInstance(context: Context):TasksDatabase {

            //this if statement will be used (true) only after database was built using databaseBuilder
            //as we gonna get the instance of database form instance variable
            //because we have saved a reference of database build when it build in instance variable and we reuse it now
            if(instance!=null){
                return instance as TasksDatabase
            }

            /*Creates a RoomDatabase.Builder for a persistent database.
            Once a database is built, you should keep a reference to it and re-use it.
            (that is why we store it in a variable to use it again)
            take 3 parameter: context, klass: abstract class which is annotated with Database and extends RoomDatabase. (this class), name of database  */

            //this code will be execute for first time (create it) only once
            instance= Room.databaseBuilder(context,TasksDatabase::class.java, "data").run {allowMainThreadQueries()}
                .fallbackToDestructiveMigration()
                .build();

            //return the instance
            return instance as TasksDatabase
        }
    }//end companion object

    //3) create fun that return Dao interface to get DAOs which access the entities
    // (the fun must be abstract since class is)
    abstract fun getTasksDao():TasksDao;
}