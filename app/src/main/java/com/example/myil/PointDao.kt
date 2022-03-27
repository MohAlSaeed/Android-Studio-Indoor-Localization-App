package com.example.myil

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PointDao {
    @Query("select * from Point")
    fun getPoints(): LiveData<List<Point>>

    @Insert
    fun insertPoint(point: Point)

    @Query("DELETE FROM Point")
    fun delteAll()

    @Delete
    fun deletePoint(point: Point)

    @Query(
        "SELECT * FROM Point WHERE Point.r1 = :r1 and Point.r2 = :r2 and Point.r3 = :r3"
    )
    fun findPoint(r1: Float, r2: Float, r3: Float): List<Point>

    @Query("select * from Point")
    fun findall(): List<Point>
}