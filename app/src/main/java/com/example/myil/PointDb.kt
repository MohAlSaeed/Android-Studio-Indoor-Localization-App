package com.example.myil

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Point::class], version = 1)
abstract class PointDb : RoomDatabase() {

    abstract fun pointDo(): PointDao

    companion object {
        private var instance: PointDb? = null

        fun getDatabase(context: Context): PointDb {
            if (instance != null)
                return instance as PointDb
            instance = Room.databaseBuilder(
                context.applicationContext,
                PointDb::class.java,
                "PointDatabase6"
            ).allowMainThreadQueries().build()
            return instance as PointDb
        }
    }

}