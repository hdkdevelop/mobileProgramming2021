package com.mp2021.dailytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.mp2021.dailytodo.databinding.ActivityAddcateBinding

class AddcateActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddcateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityAddcateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        init()
    }

    private fun init() {

        binding.apply {

            catename.text.clear()
            catedetail.text.clear()

            btadd.setOnClickListener {
                //모든 내용 null이 아닐때만 카테고리 추가하고 습관목록화면으로 가기
                val catename = catename.text.toString()
                val catedetail = catedetail.text.toString()


                if(catename == "" || catedetail == "") {
                    Toast.makeText(this@AddcateActivity, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    //카테고리추가
                }
                clearEditText()
                //이전화면으로
            }
            btcancel.setOnClickListener {
                //변경사항 없이 뒤로가기
                clearEditText()
                finish()
            }

        }

    }

    fun clearEditText(){
        binding.apply {
            catename.text.clear()
            catedetail.text.clear()
        }
    }

}