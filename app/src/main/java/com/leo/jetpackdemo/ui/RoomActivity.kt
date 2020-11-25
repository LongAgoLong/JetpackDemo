package com.leo.jetpackdemo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ActivityRoomBinding
import com.leo.jetpackdemo.room.SchoolDatabase
import com.leo.jetpackdemo.room.student.Student
import com.leo.jetpackdemo.room.student.StudentViewModel

class RoomActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRoomBinding
    private lateinit var mModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayHomeAsUpEnabled(true)

        initView()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        mBinding.addBtn.setOnClickListener {
            val name = getRandomChar().toString() + getRandomChar().toString()
            val s =
                Student(name, (1..16).random(), (1..10).random(), (8..14).random(), (0..1).random())
            SchoolDatabase.getInstance(this).studentDao().insert(s)
        }
        mBinding.deleteBtn.setOnClickListener {
            SchoolDatabase.getInstance(this).studentDao().deleteAll()
            updateScreen(SchoolDatabase.getInstance(this).studentDao().students())
        }
        mBinding.queryBtn.setOnClickListener {
            updateScreen(SchoolDatabase.getInstance(this).studentDao().students())
        }
    }

    private fun initData() {
        mModel = ViewModelProvider(this).get(StudentViewModel::class.java)
        mModel.liveDataStudent.observe(this, Observer {
            updateScreen(it)
        })
    }

    private fun updateScreen(list: List<Student>) {
        mBinding.resultTv.text = "查询结果:\n"
        list.forEach {
            mBinding.resultTv.append("$it\n")
        }
    }

    private fun getRandomChar(): Char {
        return (0x4e00 + (Math.random() * (0x9fa5 - 0x4e00 + 1)).toInt()).toChar()
    }
}