package com.kotlin.roomdbrecyclerview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kotlin.roomdbrecyclerview.model.Student
import com.kotlin.roomdbrecyclerview.repository.StudentRepository


class StudentViewModel(app: Application) : AndroidViewModel (app) {
        lateinit var studentRepository: StudentRepository;

    init {
        studentRepository = StudentRepository(app)
        //  studentList=studentRepository.studentList;

    }

    fun insertStudentData(student: Student) {
        studentRepository.insertStudent(student)
    }

    fun getStudentList(): LiveData<List<Student>> {
        return studentRepository.getStudentList()
    }

    fun updateStudentData(student: Student) {
        studentRepository.updateRecord(student)
    }

    fun deleteStudentData(student: String) {
        studentRepository.deleteRecord(student)
    }

    fun deleteTable() {
        studentRepository.deleteTable()
    }


}







