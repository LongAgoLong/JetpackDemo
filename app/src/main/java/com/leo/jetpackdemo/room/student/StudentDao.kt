package com.leo.jetpackdemo.room.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.leo.jetpackdemo.room.BaseDao

@Dao
interface StudentDao : BaseDao<Student> {
    @Query("DELETE FROM student")
    fun deleteAll()

    @Query("SELECT * FROM student")
    fun queryAll(): List<Student>

    @Query("SELECT * FROM student")
    fun queryAllObservable(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE id = :id")
    fun queryById(id: String): List<Student>

    @Query("SELECT * FROM student WHERE age > :age")
    fun queryAgeGreaterThan(age: String): List<Student>
}