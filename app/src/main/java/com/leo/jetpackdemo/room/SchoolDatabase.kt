package com.leo.jetpackdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leo.jetpackdemo.room.student.Student
import com.leo.jetpackdemo.room.student.StudentDao


@Database(entities = [Student::class], version = 2)
abstract class SchoolDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "school_db"
        private var mInstance: SchoolDatabase? = null
        fun getInstance(context: Context): SchoolDatabase {
            if (null == mInstance) {
                synchronized(this) {
                    if (null == mInstance) {
                        mInstance = Room.databaseBuilder(
                            context.applicationContext,
                            SchoolDatabase::class.java,
                            DB_NAME
                        )
                            // 可添加多个Migration
                            .addMigrations(MIGRATION_1_2)
                            // 若升级没有匹配到相应的Migration，此方法可以防止程序崩溃 -> 重新创建表，会导致数据丢失
                            // .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return mInstance!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE student ADD COLUMN gender INTEGER NOT NULL DEFAULT 0")
            }
        }
    }

    abstract fun studentDao(): StudentDao
}