package com.example.myil

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Point(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var ap1: String,
    var r1: Float ,
    var x1: Float ,
    var y1: Float ,
    var ap2: String,
    var r2: Float,
    var x2: Float,
    var y2: Float,
    var ap3: String,
    var r3: Float,
    var x3: Float,
    var y3: Float,
    var x: Float,
    var y: Float,

    )

{}
