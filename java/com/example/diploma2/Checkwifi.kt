package com.example.diploma2

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.diploma2.databinding.ActivityCheckwifiBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class heckwifi: AppCompatActivity() {

    private lateinit var response: Response
    private lateinit var binding: ActivityCheckwifiBinding
    private lateinit var pref: SharedPreferences
    var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckwifiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("MyPref", MODE_PRIVATE)
        onClickSaveIP()
        getIp()
        binding.apply {
            getinfo.setOnClickListener(onClickListener())
        }
    }

    private fun onClickListener(): View.OnClickListener{
        return View.OnClickListener {
            post("1")
        }
    }

    private fun getIp() = with(binding){
        val ip = pref.getString("ip", "")
        if(ip != null){
            if (ip.isNotEmpty()) edip.setText(ip)
        }
    }
    private fun onClickSaveIP() = with(binding){
        bsave.setOnClickListener {
            if (edip.text.isNotEmpty())saveIP(edip.text.toString())
        }
    }

    private fun saveIP(ip:String){
        val editor = pref.edit()
        editor.putString("ip", ip)
        editor.apply()

    }


    private fun post(post: String) {

        val okHttpBuilder = client.newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
        this.client = okHttpBuilder.build()




    }
    }