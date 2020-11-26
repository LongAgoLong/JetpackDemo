package com.leo.jetpackdemo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ActivityTwoWayBindingBinding
import com.leo.jetpackdemo.databinding.TwoWayBindingViewModel

class TwoWayBindingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityTwoWayBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayHomeAsUpEnabled(true)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_two_way_binding)
        mBinding.model = TwoWayBindingViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}