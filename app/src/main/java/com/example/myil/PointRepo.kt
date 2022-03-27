package com.example.myil

class PointRepo(private val pointDao: PointDao) {
    val allPoints = pointDao.getPoints()

    fun insert(point: Point) = pointDao.insertPoint(point)

    fun  delete(point: Point) = pointDao.deletePoint(point)

    fun deleteAll() = pointDao.delteAll()

    fun findPoint(r1: Float , r2: Float, r3: Float) = pointDao.findPoint(r1,r2, r3)

    fun findall() = pointDao.findall()

}