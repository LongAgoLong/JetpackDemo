package com.leo.jetpackdemo.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.leo.jetpackdemo.R
import com.leo.jetpackdemo.databinding.ActivityRoomBinding
import com.leo.jetpackdemo.room.SchoolDatabase
import com.leo.jetpackdemo.room.student.Student
import com.leo.jetpackdemo.room.student.StudentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

class RoomActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRoomBinding
    private val mModel: StudentViewModel by lazy {
        ViewModelProvider(this).get(StudentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayHomeAsUpEnabled(true)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        mBinding.eventHandler = EventHandlerListener(WeakReference(this))

        lifecycleScope.launchWhenCreated {
            mModel.allStudentsObservable(this@RoomActivity).flowOn(Dispatchers.IO).collect {
                withContext(Dispatchers.Main) {
                    updateScreen(it)
                }
            }
//            mModel.allStudents(this@RoomActivity).flowOn(Dispatchers.IO).collect {
//                withContext(Dispatchers.Main) {
//                    updateScreen(it)
//                }
//            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateScreen(list: List<Student>) {
        mBinding.resultTv.text = "查询结果:\n"
        list.forEach {
            mBinding.resultTv.append("$it\n")
        }
    }

    inner class EventHandlerListener constructor(private val actWeak: WeakReference<RoomActivity>) {

        fun onButtonClicked(view: View) {
            val act = actWeak.get() ?: return
            when (view.id) {
                R.id.addBtn -> {
                    val name = getRandomChar().toString() + getRandomChar().toString()
                    val s =
                        Student(
                            name,
                            (1..16).random(),
                            (1..10).random(),
                            (8..14).random(),
                            (0..1).random()
                        )
                    SchoolDatabase.getInstance(act).studentDao().insert(s)
                }
                R.id.deleteBtn -> {
                    SchoolDatabase.getInstance(act).studentDao().deleteAll()
                }
                R.id.queryBtn -> {
                    updateScreen(SchoolDatabase.getInstance(act).studentDao().queryAll())
                }
            }
        }
    }

    private fun getRandomChar(): Char {
        return (0x4e00 + (Math.random() * (0x9fa5 - 0x4e00 + 1)).toInt()).toChar()
    }
}