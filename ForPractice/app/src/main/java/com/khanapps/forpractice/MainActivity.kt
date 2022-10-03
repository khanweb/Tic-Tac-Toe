package com.khanapps.forpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.khanapps.forpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hello.setOnClickListener{
            goTo()
        }
    }

    fun goTo(){
        val intent = Intent(this,NotificationSet::class.java)
        startActivity(intent)

    }
}