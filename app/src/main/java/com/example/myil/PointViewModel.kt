package com.example.myil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PointViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: PointRepo
    val allPoints: LiveData<List<Point>>

    init {
        repo = PointRepo(PointDb.getDatabase(application).pointDo())
        allPoints = repo.allPoints
    }

    fun insert(point: Point) = repo.insert(point)
    fun delete(point: Point) = repo.delete(point)
    fun deleteAll() = repo.deleteAll()
    fun findPoint(r1: Float , r2: Float, r3: Float) = repo.findPoint(r1,r2,r3)
    fun findall() = repo.findall()
}