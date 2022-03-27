package com.example.myil

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_bgi.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BGI : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bgi)
        sp = getSharedPreferences("Aps", MODE_PRIVATE)

        val v2_intent = Intent(this,V2::class.java)
        bt_v2.setOnClickListener {
            startActivity(v2_intent)
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.Q)
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
        var x:Double = 0.0
        var y: Double = 0.0
        bgi_img1.x = x1
        bgi_img1.y = y1
        bgi_img2.x = x2
        bgi_img2.y = y2
        bgi_img3.x = x3
        bgi_img3.y = y3

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)

        var p01 = 0.0
        var p02 = 0.0
        var p03 = 0.0
        var p00 = 0.0
        var p11 = 0.0
        var p22 = 0.0

        bgi_floc.setOnClickListener(){
            var nx = 5.0
            var xg = 2.0
            val wratio = width / 1290.0
            val hratio = height / 2090.0
            var r1 = wifiManager.connectionInfo.rssi.toDouble()
            var r2 = wifiManager.connectionInfo.rssi.toDouble()
            var r3 = wifiManager.connectionInfo.rssi.toDouble()


            wifiManager.startScan()
            val scanResult: List<ScanResult> = wifiManager.scanResults
            for (list in scanResult) {
                if (list.SSID == Ap1) {
                    println("Ap1 power = ${list.level}")
//                    r1 = Math.pow(10.0, (((list.level * -1.0) - 27.0) / 50.0)).toDouble()
                    r1 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * 5))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r1 = num.format(r1).toDouble()
                } else if (list.SSID == Ap2) {
                    println("Ap2 power = ${list.level}")
//                    r2 = Math.pow(10.0, (((list.level * -1.0) - 27.0) / 50.0)).toDouble()
                    r2 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * 4))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r2 = num.format(r2).toDouble()
                } else if (list.SSID == Ap3) {
                    println("Ap3 power = ${list.level}")
//                    r3 = Math.pow(10.0, (((list.level * -1.0) - 27.0) / 50.0)).toDouble()
                    r3 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * 5))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r3 = num.format(r3).toDouble()

                }
            }
            println("r01 value ,  , $r1")
            println("r02 value ,  , $r2")
            println("r03 value ,  , $r3")


            var k1 = (r1*r1 - r2*r2 - x1*x1 - y1*y1 + x2*x2 + y2*y2) / (2*(x2 - x1))
            var n1 = (y2 - y1) / (x2 - x1)
            var a1 = n1*n1 + 1
            var b1 = 2*x1*n1 - 2*k1*n1 - 2*y1
            var c1 = k1*k1 - r1*r1 - 2*k1*x1 + x1*x1 + y1*y1
            val iy1 = ((-b1 + Math.sqrt((b1.toDouble()*b1.toDouble() - 4*a1.toDouble()*c1.toDouble())) ) / (2*a1)).toDouble()
            val iyv1 = ((-b1 - Math.sqrt((b1.toDouble()*b1.toDouble() - 4*a1.toDouble()*c1.toDouble())) ) / (2*a1)).toDouble()
            val ix1 =  k1 - n1*iy1
            val ixv1 =  k1 - n1*iyv1

            var k2 = (r1*r1 - r3*r3 - x1*x1 - y1*y1 + x3*x3 + y3*y3) / (2*(x3 - x1))
            var n2 = (y3 - y1) / (x3 - x1)
            var a2 = n2*n2 + 1
            var b2 = 2*x1*n2 - 2*k2*n2 - 2*y1
            var c2 = k2*k2 - r1*r1 - 2*k2*x1 + x1*x1 + y1*y1
            val iy2 = ((-b2 + Math.sqrt((b2.toDouble()*b2.toDouble() - 4*a2.toDouble()*c2.toDouble())) ) / (2*a2)).toDouble()
            val iyv2 = ((-b2 - Math.sqrt((b2.toDouble()*b2.toDouble() - 4*a2.toDouble()*c2.toDouble())) ) / (2*a2)).toDouble()
            val ix2 =  k2- n2*iy2
            val ixv2 =  k2- n2*iyv2

            var k3 = (r2*r2 - r3*r3 - x2*x2 - y2*y2 + x3*x3 + y3*y3) / (2*(x3 - x2))
            var n3 = (y3 - y2) / (x3 - x2)
            var a3 = n3*n3 + 1
            var b3 = 2*x2*n3 - 2*k3*n3 - 2*y2
            var c3 = k3*k3 - r2*r2 - 2*k3*x2 + x2*x2 + y2*y2
            var iy3 = ((-b3 + Math.sqrt((b3.toDouble()*b3.toDouble() - 4*a3.toDouble()*c3.toDouble())) ) / (2*a3)).toDouble()
            var iyv3 = ((-b3 - Math.sqrt((b3.toDouble()*b3.toDouble() - 4*a3.toDouble()*c3.toDouble())) ) / (2*a3)).toDouble()
            var ix3 =  k3 - n3*iy3
            var ixv3 =  k3 - n3*iyv3

            x = (ix1 + ix2 + ix3 + ixv1 + ixv2 + ixv3) / 6.0
            y = (iy1 + iy2 + iy3 + iyv1 + iyv2 + iyv3) / 6.0

            println(Ap1)
            println(r1)
            println(Ap2)
            println(r2)
            println(Ap3)
            println(r3)
            println("x and y")
            println("initial x : $ix1 , $ixv1 , $ix2, $ixv2 ,$ix3 ,$ixv3 ")
            println("initial y : $iy1 , $iyv1 ,$iy2 ,$iyv2 ,$iy3 ,$iyv3")
            println(x)
            println(y)

            val fx = x * 100 * wratio
            val fy = y * 100 * hratio
            println(fx)
            println(fy)
            bgi_img_loc.x = fx.toFloat()
            bgi_img_loc.y = fy.toFloat()
            bgi_tvx.text = (x * 400/3.4).toString()
            bgi_tvy.text = (y * 800/6.3).toString()

            println("n0 =   , xq  = ")

            canvas.drawColor(Color.TRANSPARENT);
            var paint = Paint()
            paint.setColor(Color.parseColor("#b0210b"))
            paint.setStrokeWidth(5F)
            paint.setStyle(Paint.Style.STROKE)
            paint.setAntiAlias(true)
            paint.setDither(true)
            // get device dimensions
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            // circle center
            System.out.println("Width : "+displayMetrics.widthPixels)
            System.out.println("height : "+displayMetrics.heightPixels)
            System.out.println("width : "+width)
            var center_x1 = x1 + 45
            var center_y1 = y1 + 145
            var radius1 = r1.toFloat()/room_y * height
            var center_x2 = x2 + 45
            var center_y2 = y2 + 140
            var radius2 = r2.toFloat()/room_y * height
            var center_x3 = x3 + 45
            var center_y3 = y3 + 145
            var radius3 = r3.toFloat()/room_y * height

            // draw circle
            canvas.drawCircle(center_x1, center_y1, radius1, paint)
            canvas.drawCircle(center_x2, center_y2, radius2/2F, paint)
            canvas.drawCircle(center_x3, center_y3, radius3, paint)
            // set bitmap as background to ImageView
            // imageV
            imageV1.background= BitmapDrawable(getResources(), bitmap)

        }
    }
}