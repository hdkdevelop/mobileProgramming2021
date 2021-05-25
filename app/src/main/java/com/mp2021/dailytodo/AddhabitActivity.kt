package com.mp2021.dailytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.mp2021.dailytodo.databinding.ActivityAddhabitBinding

class AddhabitActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddhabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityAddhabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.apply {

            habitname.text.clear()
            habitdetail.text.clear()

            btadd.setOnClickListener {
                //모든 내용 null이 아닐때만 습관 추가하고 습관목록화면으로 가기
            }
            btcancel.setOnClickListener {
                //변경사항 없이 뒤로가기

            }


        }
    }
}