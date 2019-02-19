package com.kotlin.roomdbrecyclerview.db

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.roomdbrecyclerview.dao.StudentDao
import com.kotlin.roomdbrecyclerview.model.Student


@Database(entities = [Student::class], version = 1)

abstract class StudentDatabase : RoomDatabase() {
        abstract fun studentDeo(): StudentDao


        companion object {
            var INSTANCE: StudentDatabase? = null

            fun getAppDataBase(context: Context): StudentDatabase? {
                if (INSTANCE == null){
                    synchronized(StudentDatabase::class){
                        INSTANCE = Room.databaseBuilder(context.applicationContext, StudentDatabase::class.java, "myDB").build()
                    }
                }
                return INSTANCE
            }

            fun destroyDataBase(){
                INSTANCE = null
            }
        }
}