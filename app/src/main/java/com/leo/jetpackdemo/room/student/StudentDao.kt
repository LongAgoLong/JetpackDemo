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
    fun students(): List<Student>

    @Query("SELECT * FROM student")
    fun studentsObservable(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE id = :id")
    fun studentById(id: String): List<Student>

    @Query("SELECT * FROM student WHERE age > :age")
    fun studentsAgeGreaterThan(age: String): List<Student>
}