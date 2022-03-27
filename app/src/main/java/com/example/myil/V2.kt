package com.example.myil

import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_bgi.*
import kotlinx.android.synthetic.main.activity_main01.*
import kotlinx.android.synthetic.main.activity_v2.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.properties.Delegates

class V2 : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor
    private var x by Delegates.notNull<Double>()
    private var y by Delegates.notNull<Double>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v2)
        sp = getSharedPreferences("Aps", MODE_PRIVATE)
        edt = sp.edit()
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
        ap1.x = x1
        ap1.y = y1
        ap2.x = x2
        ap2.y = y2
        ap3.x = x3
        ap3.y = y3
        var r1 = 0.0
        var r2 = 0.0
        var r3 = 0.0

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bitmap)
        wifiManager.startScan()
        val scanResult: List<ScanResult> = wifiManager.scanResults
        var nx1:Double = 2.0
        var nx2:Double = 2.0
        var nx3:Double = 2.0
        vars.setOnClickListener {
            var ap1r1 = ap1_r1.text.toString().toDouble()
            var ap2r2 = ap2_r2.text.toString().toDouble()
            var ap3r3 = ap3_r3.text.toString().toDouble()
            for (list in scanResult) {
                if (list.SSID == Ap1) {
                    println("Ap1 power = ${list.level}")
                    nx1 = (list.level * -1.0 - 24.0 - 5.0) / (Math.log10(ap1r1) * 10.0)
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    nx1 = num.format(nx1).toDouble()
                } else if (list.SSID == Ap2) {
                    println("Ap2 power = ${list.level}")
//                    nx2 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * ap2r2))
                    nx2 = (list.level * -1.0 - 24.0 - 5.0) / (Math.log10(ap2r2) * 10.0)
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    nx2 = num.format(nx2).toDouble()
                } else if (list.SSID == Ap3) {
                    println("Ap3 power = ${list.level}")
//                    nx3 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * ap3r3))
                    nx3 = (list.level * -1.0 - 24.0 - 5.0) / (Math.log10(ap3r3) * 10.0)
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    nx3 = num.format(nx3).toDouble()

                }
            }
            edt.putFloat("rv2_1",nx1.toFloat())
            edt.putFloat("rv2_2",nx2.toFloat())
            edt.putFloat("rv2_3",nx3.toFloat())
            edt.apply()
        }

        floc.setOnClickListener {
            nx1 = sp.getFloat("rv2_1",0F).toDouble()
            nx2 = sp.getFloat("rv2_2",0F).toDouble()
            nx3 = sp.getFloat("rv2_3",0F).toDouble()
            println(nx1)
            println(nx2)
            println(nx3)
            wifiManager.startScan()
            val scanResult: List<ScanResult> = wifiManager.scanResults
            for (list in scanResult) {
                if (list.SSID == Ap1) {
                    println("Ap1 power = ${list.level}")
                    r1 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * nx1))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r1 = num.format(r1).toDouble()
                } else if (list.SSID == Ap2) {
                    println("Ap2 power = ${list.level}")
                    r2 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * nx2))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r2 = num.format(r2).toDouble()
                } else if (list.SSID == Ap3) {
                    println("Ap3 power = ${list.level}")
                    r3 = Math.pow(10.0, ((list.level * -1.0) - 24.0 - 5)/(10 * nx3))
                    val num = DecimalFormat("##.#")
                    num.roundingMode = RoundingMode.CEILING
                    r3 = num.format(r3).toDouble()
                }
                println("LOGGGGGGGGGGGGGGG")
                println(Math.log10(10000000.0))
            }
//            ######### trying to find X & Y values ##########
            val x01 = sp.getFloat("Ap1_x",0F)
            val y01 = sp.getFloat("Ap1_y",0F)
            val x02 = sp.getFloat("Ap2_x",0F)
            val y02 = sp.getFloat("Ap2_y",0F)
            val x03 = sp.getFloat("Ap3_x",0F)
            val y03 = sp.getFloat("Ap3_y",0F)

            var k1 = (r1*r1 - r2*r2 - x01*x01 - y01*y01 + x02*x02 + y02*y02) / (2*(x02 - x01))
            var n1 = (y02 - y01) / (x02 - x01)
            var a1 = n1*n1 + 1
            var b1 = 2*x01*n1 - 2*k1*n1 - 2*y01
            var c1 = k1*k1 - r1*r1 - 2*k1*x01 + x01*x01 + y01*y01
            val iy1 = ((-b1 + Math.sqrt((b1*b1 - 4*a1*c1)) ) / (2*a1))
            val iyv1 = ((-b1 - Math.sqrt((b1*b1 - 4*a1*c1)) ) / (2*a1))
            val ix1 =  k1 - n1*iy1
            val ixv1 =  k1 - n1*iyv1

            var k2 = (r1*r1 - r3*r3 - x01*x01 - y01*y01 + x03*x03 + y03*y03) / (2*(x03 - x01))
            var n2 = (y03 - y01) / (x03 - x01)
            var a2 = n2*n2 + 1
            var b2 = 2*x01*n2 - 2*k2*n2 - 2*y01
            var c2 = k2*k2 - r1*r1 - 2*k2*x01 + x01*x01 + y01*y01
            val iy2 = ((-b2 + Math.sqrt((b2*b2 - 4*a2*c2)) ) / (2*a2))
            val iyv2 = ((-b2 - Math.sqrt((b2*b2 - 4*a2*c2)) ) / (2*a2))
            val ix2 =  k2- n2*iy2
            val ixv2 =  k2- n2*iyv2

            var k3 = (r2*r2 - r3*r3 - x02*x02 - y02*y02 + x03*x03 + y03*y03) / (2*(x03 - x02))
            var n3 = (y03 - y02) / (x03 - x02)
            var a3 = n3*n3 + 1
            var b3 = 2*x02*n3 - 2*k3*n3 - 2*y02
            var c3 = k3*k3 - r2*r2 - 2*k3*x02 + x02*x02 + y02*y02
            var iy3 = ((-b3 + Math.sqrt((b3*b3 - 4*a3*c3)) ) / (2*a3))
            var iyv3 = ((-b3 - Math.sqrt((b3*b3 - 4*a3*c3)) ) / (2*a3))
            var ix3 =  k3 - n3*iy3
            var ixv3 =  k3 - n3*iyv3

            val mapxy = LinkedHashMap<Double,Double>()
            mapxy.put(ix1,iy1)
            mapxy.put(ixv1,iyv1)
            mapxy.put(ix2,iy2)
            mapxy.put(ixv2,iyv2)
            mapxy.put(ix3,iy3)
            mapxy.put(ixv3,iyv3)


            var fx1 =0.0
            var fxv1 =0.0
            var fx2 =0.0
            var fxv2 =0.0
            var fx3 =0.0
            var fxv3 =0.0
            var fy1 =0.0
            var fyv1 =0.0
            var fy2 =0.0
            var fyv2 =0.0
            var fy3 =0.0
            var fyv3 =0.0


            if((ix2 - ix1) + (ixv2 - ix1) < ((ix2 - ixv1) + (ixv2 - ixv1))){fx1 = ix1} else fx1 = ixv1
            if((ix3 - ix1) + (ixv3 - ix1) < ((ix3 - ixv1) + (ixv3 - ixv1))){fxv1 = ix1} else fxv1 = ixv1
            if((ix1 - ix2) + (ixv1 - ix2) < ((ix1 - ixv2) + (ixv1 - ixv2))){fx2 = ix2} else fx2 = ixv2
            if((ix3 - ix2) + (ixv3 - ix2) < ((ix3 - ixv2) + (ixv3 - ixv2))){fxv2 = ix2} else fxv2 = ixv2
            if((ix1 - ix3) + (ixv1 - ix3) > ((ix1 - ixv3) + (ixv1 - ixv3))){fx3 = ix3} else fx3 = ixv3
            if((ix2 - ix3) + (ixv2 - ix3) > ((ix2 - ixv3) + (ixv2 - ixv3))){fxv3 = ix3} else fxv3 = ixv3
            println("fx1 , fxv1, = $fx1 , $fxv1, fx2 , fxv2, = $fx2 , $fxv2, fx3 , fxv3, = $fx3 , $fxv3")
            if(
                (
                                Math.abs(ix1 - ix2) +
                                Math.abs(ix1 - ixv2) +
                                Math.abs(ix1 - ix3) +
                                Math.abs(ix1 - ixv3)
                        ) < (
                                Math.abs(ixv1 - ix2) +
                                Math.abs(ixv1 - ixv2) +
                                Math.abs(ixv1 - ix3) +
                                Math.abs(ixv1 - ixv3)
                                )
            ){fx1 = ix1} else fx1 = ixv1
            if(
                (
                                Math.abs(ix2 - ix1) +
                                Math.abs(ix2 - ixv1) +
                                Math.abs(ix2 - ix3) +
                                Math.abs(ix2 - ixv3)
                        ) < (
                                Math.abs(ixv2 - ix1) +
                                Math.abs(ixv2 - ixv1) +
                                Math.abs(ixv2 - ix3) +
                                Math.abs(ixv2 - ixv3)
                        )
            ){fx2 = ix2} else fx2 = ixv2

            if(
                (
                                Math.abs(ix3 - ix1) +
                                Math.abs(ix3 - ixv1) +
                                Math.abs(ix3 - ix2) +
                                Math.abs(ix3 - ixv2)
                        ) < (
                                Math.abs(ixv3 - ix1) +
                                Math.abs(ixv3 - ixv1) +
                                Math.abs(ixv3 - ix2) +
                                Math.abs(ixv3 - ixv2)
                        )
            ){fx3 = ix3} else fx3 = ixv3


            fy1 = mapxy.get(fx1)!!
            fy2 = mapxy.get(fx2)!!
            fy3 = mapxy.get(fx3)!!
            println("fy1 , fy2, fy2, = $fy1 , $fy2, $fy3")
            println("Diff in x values $r1  ,$x01 , $y01")
            println("Diff in x values $ix1  ,$ix2 , $ix3")
//            println("Diff in x values $diffx1  ,$diffx2 , $diffx3")
            println("Diff in  values ix1  ,ixv1 ,ix2  ,ixv2, ix3  ,ixv3")
            println("Diff in  values $ix1  ,$ixv1 ,$ix2  ,$ixv2, $ix3  ,$ixv3")
            println("fx1 = $fx1")
            println("fy1 = $fy1")
            imlocv2.x = ((fx1+fx2+fx3)/3).toFloat() /room_x * width
            imlocv2.y = ((fy1+fy2+fy3)/3).toFloat() /room_y * height




            x = (ix1 + ix2 + ix3 + ixv1 + ixv2 + ixv3) / 6.0
            y = (iy1 + iy2 + iy3 + iyv1 + iyv2 + iyv3) / 6.0
//            ###################

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
            canvas.drawCircle(center_x2, center_y2, radius2, paint)
            canvas.drawCircle(center_x3, center_y3, radius3, paint)
            // set bitmap as background to ImageView
            // imageV
            imageV2.background= BitmapDrawable(getResources(), bitmap)

        }
    }
}