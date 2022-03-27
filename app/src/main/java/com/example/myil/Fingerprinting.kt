package com.example.myil

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.net.wifi.aware.WifiAwareManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myil.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main01.*
import java.math.RoundingMode
import java.text.DecimalFormat

class Fingerprinting : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("Aps", MODE_PRIVATE)
        edt = sp.edit()
        val tv = findViewById<TextView>(R.id.tv1)
        tv.setText("off-line Fingerprinting")

        // permissions check Give permissions
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE ) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(permissions, 0)
        }
    }

    override fun onStart() {
        super.onStart()

        rv1.layoutManager = LinearLayoutManager(this)
        rv1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val pointViewModel = PointViewModel(application)
        val adap = PointAdapter(this, pointViewModel)
        rv1.adapter = adap
        pointViewModel.allPoints.observe(this, Observer {
                points -> points?.let {
            adap.setPoints(it)
        }

        })

        var wifiManager: WifiManager = application.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val height = Resources.getSystem().displayMetrics.heightPixels
        val width = Resources.getSystem().displayMetrics.widthPixels
        val Ap1 = sp.getString("Ap1"," ")
        val Ap2 = sp.getString("Ap2"," ")
        val Ap3 = sp.getString("Ap3"," ")
        val room_x = sp.getFloat("room_x",0F)
        val room_y = sp.getFloat("room_y",0F)
        val Ap1_x = sp.getFloat("Ap1_x",0F)/room_x * width
        val Ap2_x = sp.getFloat("Ap2_x",0F)/room_x * width
        val Ap3_x = sp.getFloat("Ap3_x",0F)/room_x * width
        val Ap1_y = sp.getFloat("Ap1_y",0F)/room_y * height
        val Ap2_y = sp.getFloat("Ap2_y",0F)/room_y * height
        val Ap3_y = sp.getFloat("Ap3_y",0F)/room_y * height
        val x = tn1.text.toString()
        val y = tn2.text.toString()



        bt1.setOnClickListener{
            var findall = pointViewModel.findall()
            var r = wifiManager.connectionInfo.rssi
            val n = wifiManager.connectionInfo.ssid
            val d = Math.pow(10.0, (((r*-1.0) - 30.0) / 50.0))
            val point = Point( ap1="0", r1=0F, x1=0F, y1=0F, ap2="0", r2=0F, x2=0F, y2=0F, ap3="0", r3=0F, x3=0F, y3=0F, x=0F, y=0F )
            var exists = 0
            wifiManager.startScan()
            val listall: List<Point> = pointViewModel.findall()
            val scanResult: List<ScanResult> = wifiManager.scanResults
            for (list in scanResult){
                println("${list.SSID}  ${list.level}")
                if (list.SSID == Ap1){
                    point.ap1= list.SSID
                    val r1=Math.pow(10.0, (((list.level*-1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("####.#")
                    num.roundingMode = RoundingMode.CEILING
                    point.r1 = num.format(r1).toFloat()
                    point.x1= num.format(Ap1_x).toFloat()
                    point.y1= num.format(Ap1_y).toFloat()
                    if (tn1.text.toString().toFloat() > 0F){
                        point.x= num.format(tn1.text.toString().toFloat()/room_x * width).toFloat()
                        point.y= num.format(tn2.text.toString().toFloat()/room_y * height).toFloat()
                    }
                } else if (list.SSID == Ap2){
                    point.ap2= list.SSID
                    val r2=Math.pow(10.0, (((list.level*-1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("####.#")
                    num.roundingMode = RoundingMode.CEILING
                    point.r2 = num.format(r2).toFloat()
                    point.x2=num.format(Ap2_x).toFloat()
                    point.y2=num.format(Ap2_y).toFloat()
                    point.x= num.format(tn1.text.toString().toFloat()/room_x * width).toFloat()
                    point.y= num.format(tn2.text.toString().toFloat()/room_y * height).toFloat()
                } else if (list.SSID == Ap3){
                    point.ap3= list.SSID
                    val r3=Math.pow(10.0, (((list.level*-1.0) - 30.0) / 50.0)).toFloat()
                    val num = DecimalFormat("####.#")
                    num.roundingMode = RoundingMode.CEILING
                    point.r3 = num.format(r3).toFloat()
                    point.x3=num.format(Ap3_x).toFloat()
                    point.y3=num.format(Ap3_y).toFloat()
                    point.x= num.format(tn1.text.toString().toFloat()/room_x * width).toFloat()
                    point.y= num.format(tn2.text.toString().toFloat()/room_y * height).toFloat()
                }
            }
            for (nlist in findall){
                if (nlist.r1 == point.r1 && nlist.r2 == point.r2 && nlist.r3 == point.r3){
                    exists = 1
                }
            }
            if (exists == 0){
                println("not exists")
                pointViewModel.insert(point)
            } else if (exists == 1){
                println("exists")
            }
        }
        bt1.setOnLongClickListener {
            pointViewModel.deleteAll()
            Toast.makeText(this, "Points Removed",
                Toast.LENGTH_SHORT ).show()
            true
        }
        val intent_FPO = Intent(this,Fingerprinting_online::class.java)
        bt4.setOnClickListener {
            startActivity(intent_FPO)
        }
    }
}