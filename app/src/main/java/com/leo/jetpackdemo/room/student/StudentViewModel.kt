package com.leo.jetpackdemo.room.student

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.leo.jetpackdemo.room.SchoolDatabase

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    var liveDataStudent: LiveData<List<Student>> =
        SchoolDatabase.getInstance(application).studentDao().queryAllObservable()
}