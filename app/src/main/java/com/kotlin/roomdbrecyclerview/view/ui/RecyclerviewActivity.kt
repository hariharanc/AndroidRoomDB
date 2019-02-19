package com.kotlin.roomdbrecyclerview.view.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.roomdbrecyclerview.R

import com.kotlin.roomdbrecyclerview.model.Student
import com.kotlin.roomdbrecyclerview.view.adapter.OnItemClickListener
import com.kotlin.roomdbrecyclerview.view.adapter.RecyclerAdapter
import com.kotlin.roomdbrecyclerview.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.activity_recycler.*


class RecyclerviewActivity : AppCompatActivity(), OnItemClickListener {
    var selPos: Int = 0
    val studentLiveData: MutableList<Student> = ArrayList()
    lateinit var studentAdapter: RecyclerAdapter
    lateinit var studentViewModel: StudentViewModel
    private val observer = Observer<List<Student>> { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        studentAdapter = RecyclerAdapter(studentLiveData, this, this)
        recyclerView.adapter = studentAdapter
        notifyObserver()
    }

    override fun onResume() {
        super.onResume()
        notifyObserver()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun notifyObserver() {
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        studentViewModel.getStudentList().observeForever {
            if (studentLiveData.isNotEmpty()) {
                studentLiveData.clear()
                studentLiveData.addAll(it)
            } else
                studentLiveData.addAll(it)
            studentAdapter.notifyDataSetChanged()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                if (requestCode == 100) {
                    val bundle = data?.getBundleExtra("myBundle")
                    val student = bundle?.getParcelable<Student>("studentObj") as Student
                    studentViewModel.insertStudentData(student)
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_add ->
                startActivityForResult(Intent(this, AddActivity::class.java), 100)

            R.id.action_del -> confirmDialogue()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(pos: Int) {
        selPos = pos
        showdialogueOption()
    }

    private fun showdialogueOption() {
        val listItems = arrayOf("Update", "Delete")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Item")
        builder.setItems(listItems) { dialog, which ->
            when (which) {
                0 -> updateDialogue()
                1 -> {
                    var studentUpdate = studentLiveData[selPos]
                    studentViewModel.deleteStudentData(studentUpdate.name)
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateDialogue() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(R.layout.add_activity, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.edtName)
        val btnAdd = dialogLayout.findViewById<Button>(R.id.btnAdd)
        val txtTitle = dialogLayout.findViewById<TextView>(R.id.txtTitle)
        txtTitle.visibility = View.GONE
        btnAdd.visibility = View.GONE
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            val name: String = editText.text.toString()
            if (name?.length > 0) {
                var studentUpdate = studentLiveData[selPos]
                studentUpdate.name = editText.text.toString()
                studentViewModel.updateStudentData(studentUpdate)
            } else
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun confirmDialogue() {
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("Are You sure Want Delete?")
        builder.setPositiveButton("OK") { dialogInterface, i ->
            studentViewModel.deleteTable()
            if (dialog!!.isShowing)
                dialog.cancel()

        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialog.cancel()
        }
        dialog = builder.create()
        dialog.show()
    }
}




