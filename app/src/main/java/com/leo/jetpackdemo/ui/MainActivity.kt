package com.leo.jetpackdemo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ActivityMainBinding
import com.leo.jetpackdemo.room.student.StudentViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mBinding.roomBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RoomActivity::class.java)
            startActivity(intent)
        }
    }
}