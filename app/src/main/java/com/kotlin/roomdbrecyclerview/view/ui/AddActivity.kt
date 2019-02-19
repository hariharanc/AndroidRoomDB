package com.kotlin.roomdbrecyclerview.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.kotlin.roomdbrecyclerview.model.Student
import com.kotlin.roomdbrecyclerview.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.add_activity.*
import java.util.UUID.randomUUID

import java.util.*
import android.os.SystemClock
import com.kotlin.roomdbrecyclerview.R


class AddActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var studentViewModel: StudentViewModel
    lateinit var student: Student
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
        btnAdd.setOnClickListener(this)
        studentViewModel = StudentViewModel(this.application)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAdd -> {
                val name: String = edtName.text.toString()
                if (name.length > 0) {
                    student = Student(SystemClock.uptimeMillis().toInt(), name, "10")
                    studentViewModel.insertStudentData(student)
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                    val resultIntent = Intent()
                    var bundle = Bundle()
                    bundle.putParcelable("studentObj",student)
                    resultIntent.putExtra("myBundle",bundle)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else
                    Toast.makeText(this, "Enter Student Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

}