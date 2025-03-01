package com.sercroft.dummyappasd.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sercroft.dummyappasd.data.dao.UserDao
import com.sercroft.dummyappasd.data.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}