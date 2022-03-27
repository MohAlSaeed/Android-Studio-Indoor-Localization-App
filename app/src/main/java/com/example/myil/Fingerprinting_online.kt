package com.example.myil

import android.content.SharedPreferences
import android.content.res.Resources
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_fingerprinting_online.*
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat

class Fingerprinting_online : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fingerprinting_online)
        sp = getSharedPreferences("Aps", MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        var wifiManager: WifiManager = application.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels
        val room_x = sp.getFloat("room_x",0F)
        val room_y = sp.getFloat("room_y",0F)
        val Ap1 = sp.getString("Ap1"," ")
        val Ap2 = sp.getString("Ap2"," ")
        val Ap3 = sp.getString("Ap3"," ")
        val x1 = sp.getFloat("Ap1_x",0F)/room_x * width
        val y1 = sp.getFloat("Ap1_y",0F)/room_y * height
        val x2 = sp.getFloat("Ap2_x",0F)/room_x * width
        val y2 = sp.getFloat("Ap2_y",0F)/room_y * height
        val x3 = sp.getFloat("Ap3_x",0F)/room_x * width
        val y3 = sp.getFloat("Ap3_y",0F)/room_y * height
        var x:Float = 0F
        var y: Float = 0F
        bt5.setOnClickListener {
            var r1 = wifiManager.connectionInfo.rssi.toFloat()
            var r2 = wifiManager.connectionInfo.rssi.toFloat()
            var r3 = wifiManager.connectionInfo.rssi.toFloat()
            wifiManager.startScan()
            val scanResult: List<ScanResult> = wifiManager.scanResults
            for (list in scanResult) {
                if (list.SSID == Ap1) {
                    r1 = Math.pow(10.0, (((list.level * -1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r1 = num.format(r1).toFloat()
                } else if (list.SSID == Ap2) {
                    r2 = Math.pow(10.0, (((list.level * -1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r2 = num.format(r2).toFloat()
                } else if (list.SSID == Ap3) {
                    r3 = Math.pow(10.0, (((list.level * -1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r3 = num.format(r3).toFloat()
                }
            }
            val pointViewModel = PointViewModel(application)
            val list: List<Point> = pointViewModel.findPoint(r1 = r1, r2 = r2, r3 = r3)
            val listall: List<Point> = pointViewModel.findall()
            println("am here")
            for (nlist in list){
                x = nlist.x
                y = nlist.y
            }

            println("am here check one")
            println("r1 = $r1")
            println("r2 = $r2")
            println("r3 = $r3")
            println("am here check two")
            for (nlist2 in listall){
                println("x = ${nlist2.x}")
                println("y = ${nlist2.y}")
                println("r1 = ${nlist2.r1}")
                println("r2 = ${nlist2.r2}")
                println("r3 = ${nlist2.r3}")
                if (r1 < nlist2.r1+1 && r1 > nlist2.r1-1 && r2 < nlist2.r2+1 && r2 > nlist2.r2-1 && r3 < nlist2.r3+1 && r3 > nlist2.r3-1) {
                    println("we have matching value below")
                    println("x = ${nlist2.x}")
                    println("y = ${nlist2.y}")
                    println("r1 = ${nlist2.r1}")
                    println("r2 = ${nlist2.r2}")
                    println("r3 = ${nlist2.r3}")
                    tvf1.setText(x.toString())
                    tvf2.setText(y.toString())
                    imloc1.x = nlist2.x
                    imloc1.y = nlist2.y
                }
            }
            x = 0F
            y = 0F
            val x01 = sp.getFloat("Ap1_x",0F)
            val y01 = sp.getFloat("Ap1_y",0F)
            val x02 = sp.getFloat("Ap2_x",0F)
            val y02 = sp.getFloat("Ap2_y",0F)
            val x03 = sp.getFloat("Ap3_x",0F)
            val y03 = sp.getFloat("Ap3_y",0F)
            println("room_x = $room_x , room_y = $room_y , x1 = $x01 , y1 = $y01 , x2 = $x02 , y2 = $y02 , x3 = $x03 , y3 = $y03")
        }

            imwi1.x = x1
            imwi1.y = y1
            imwi2.x = x2
            imwi2.y = y2
            imwi3.x = x3
            imwi3.y = y3
    }
}