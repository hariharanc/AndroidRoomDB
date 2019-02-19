package com.kotlin.roomdbrecyclerview.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.kotlin.roomdbrecyclerview.dao.StudentDao
import com.kotlin.roomdbrecyclerview.db.StudentDatabase
import com.kotlin.roomdbrecyclerview.model.Student

class StudentRepository(context: Context) {
    lateinit var studentDAO: StudentDao

    init {
        val studentDB = StudentDatabase.getAppDataBase(context)
        studentDAO = studentDB!!.studentDeo()
    }

    fun getStudentList(): LiveData<List<Student>> {
        return studentDAO.getStudent()
    }

    fun insertStudent(student: Student) {
        AsyncTask.execute {
            with(studentDAO) {
                studentDAO.insertStudent(student)
            }
        }
    }

    fun updateRecord(student: Student) {
        AsyncTask.execute {
            studentDAO.updateStudent(student)
        }
    }

    fun deleteRecord(name: String): Unit {
        AsyncTask.execute {
            studentDAO.deleteUserByName(name)
        }
    }

    fun deleteTable(): Unit {
        AsyncTask.execute {
            studentDAO.deleteTable()
        }
    }
}



