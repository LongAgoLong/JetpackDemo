package com.leo.jetpackdemo.room.student

import android.content.Context
import androidx.lifecycle.ViewModel
import com.leo.jetpackdemo.room.SchoolDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StudentViewModel : ViewModel() {

    /**
     * 结合room生成可以监听变化的Flow
     * @param context Context
     * @return Flow<List<Student>>
     */
    fun allStudentsObservable(context: Context): Flow<List<Student>> {
        return SchoolDatabase.getInstance(context).studentDao().queryAllObservable()
    }

    /**
     * 自行emit触发的flow，只会执行一次
     * @param context Context
     * @return Flow<List<Student>>
     */
    fun allStudents(context: Context): Flow<List<Student>> = flow {
        val all = SchoolDatabase.getInstance(context).studentDao().queryAll()
        emit(all)
    }
}