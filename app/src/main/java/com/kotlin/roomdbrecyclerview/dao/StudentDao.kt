package com.kotlin.roomdbrecyclerview.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kotlin.roomdbrecyclerview.model.Student

@Dao
 interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("DELETE FROM studentEntry WHERE studName == :name")
    fun deleteUserByName(name: String)

    @Query("SELECT * FROM studentEntry WHERE studName == :name")
    fun getStudentByName(name: String): LiveData<List<Student>>

    @Query("SELECT * FROM studentEntry")
    fun getStudent():LiveData<List<Student>>

    @Query("DELETE FROM studentEntry")
    fun deleteTable()

}