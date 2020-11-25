package com.leo.jetpackdemo.room

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMulti(vararg entity: T)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMulti(entity: List<T>)

    @Delete
    fun delete(entity: T)

    @Transaction
    @Delete
    fun deleteMulti(vararg entity: T)

    @Transaction
    @Delete
    fun deleteMulti(entity: List<T>)

    @Update
    fun update(entity: T)

    @Transaction
    @Update
    fun updateMulti(vararg entity: T)

    @Transaction
    @Update
    fun updateMulti(entity: List<T>)
}