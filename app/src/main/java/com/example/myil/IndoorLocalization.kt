package com.example.myil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main01.*

class IndoorLocalization : AppCompatActivity() {
    private lateinit var sp: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main01)
        sp = getSharedPreferences("Aps", Context.MODE_PRIVATE)
        edt = sp.edit()
        val intent_BGI = Intent(this, BGI::class.java)
        val intent_Fingerprinting = Intent(this, Fingerprinting::class.java)

        bt2.setOnClickListener {
            if (tin1.text.toString() != ""){
                edt.putString("Ap1",tin1.text.toString())
                edt.putString("Ap2",tin2.text.toString())
                edt.putString("Ap3",tin3.text.toString())
                edt.putFloat("Ap1_x",tin101.text.toString().toFloat())
                edt.putFloat("Ap1_y",tin102.text.toString().toFloat())
                edt.putFloat("Ap2_x",tin201.text.toString().toFloat())
                edt.putFloat("Ap2_y",tin202.text.toString().toFloat())
                edt.putFloat("Ap3_x",tin301.text.toString().toFloat())
                edt.putFloat("Ap3_y",tin302.text.toString().toFloat())
                edt.putFloat("room_x",tinx.text.toString().toFloat())
                edt.putFloat("room_y",tiny.text.toString().toFloat())
                edt.apply()
            }
            startActivity(intent_BGI)
        }
        bt3.setOnClickListener {
            if (tin1.text.toString() != ""){
                edt.putString("Ap1",tin1.text.toString())
                edt.putString("Ap2",tin2.text.toString())
                edt.putString("Ap3",tin3.text.toString())
                edt.putFloat("Ap1_x",tin101.text.toString().toFloat())
                edt.putFloat("Ap1_y",tin102.text.toString().toFloat())
                edt.putFloat("Ap2_x",tin201.text.toString().toFloat())
                edt.putFloat("Ap2_y",tin202.text.toString().toFloat())
                edt.putFloat("Ap3_x",tin301.text.toString().toFloat())
                edt.putFloat("Ap3_y",tin302.text.toString().toFloat())
                edt.putFloat("room_x",tinx.text.toString().toFloat())
                edt.putFloat("room_y",tiny.text.toString().toFloat())
                edt.apply()
            }

            startActivity(intent_Fingerprinting)
        }
    }

}