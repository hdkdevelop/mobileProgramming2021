package com.mp2021.dailytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class DatailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datailed)
    }
}