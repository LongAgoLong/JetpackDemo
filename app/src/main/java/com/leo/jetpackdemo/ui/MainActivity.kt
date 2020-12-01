package com.leo.jetpackdemo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ActivityMainBinding
import com.leo.jetpackdemo.paging.net.ApiProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        mBinding.eventHandler = EventHandlerListener(WeakReference(this))
    }

    inner class EventHandlerListener constructor(private val actWeak: WeakReference<MainActivity>) {

        fun onButtonClicked(view: View) {
            val act = actWeak.get() ?: return
            when (view.id) {
                R.id.roomBtn -> {
                    val intent = Intent(act, RoomActivity::class.java)
                    act.startActivity(intent)
                }
                R.id.dataBindingBtn -> {
                    val intent = Intent(act, TwoWayBindingActivity::class.java)
                    act.startActivity(intent)
                }
                R.id.pagingBtn -> {
                    val intent = Intent(act, PagingActivity::class.java)
                    act.startActivity(intent)
                }
            }
        }
    }
}