package com.mp2021.dailytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.mp2021.dailytodo.databinding.ActivityAddcateBinding

class AddcateActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddcateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityAddcateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        binding.apply {

            catename.text.clear()
            catedetail.text.clear()

            btadd.setOnClickListener {
                //모든 내용 null이 아닐때만 카테고리 추가하고 습관목록화면으로 가기
            }
            btcancel.setOnClickListener {
                //변경사항 없이 뒤로가기
            }

        }

    }
}