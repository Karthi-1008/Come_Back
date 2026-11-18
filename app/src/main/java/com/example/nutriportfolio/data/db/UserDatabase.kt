package com.example.nutriportfolio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserProfileEntity::class], version = 1, exportSchema = true)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
}
