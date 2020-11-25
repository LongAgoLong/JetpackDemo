package com.leo.jetpackdemo.room.student

import androidx.annotation.IntRange
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "student")
class Student {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var mId = 0

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var mName: String? = ""

    @ColumnInfo(name = "grade", typeAffinity = ColumnInfo.INTEGER)
    var mGrade = 0

    @ColumnInfo(name = "class", typeAffinity = ColumnInfo.INTEGER)
    var mClass = 0

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var mAge = 0

    @ColumnInfo(name = "gender", typeAffinity = ColumnInfo.INTEGER)
    var mGender = 0

    constructor(
        id: Int,
        name: String,
        @IntRange(from = 1, to = 16) grade: Int,
        @IntRange(from = 1, to = 10) mClass: Int,
        @IntRange(from = 8, to = 14) age: Int,
        @IntRange(from = 0, to = 1) gender: Int
    ) {
        this.mId = id
        this.mName = name
        this.mGrade = grade
        this.mClass = mClass
        this.mAge = age
        this.mGender = gender
    }

    @Ignore
    constructor(
        name: String,
        @IntRange(from = 1, to = 16) grade: Int,
        @IntRange(from = 1, to = 10) mClass: Int,
        @IntRange(from = 8, to = 14) age: Int,
        @IntRange(from = 0, to = 1) gender: Int
    ) {
        this.mName = name
        this.mGrade = grade
        this.mClass = mClass
        this.mAge = age
        this.mGender = gender
    }

    @Ignore
    constructor()

    override fun toString(): String {
        return "{\"id\":$mId,\"name\":\"$mName\",\"grade\":$mGrade,\"class\":$mClass,\"age\":$mAge,\"gender\":$mGender}"
    }
}