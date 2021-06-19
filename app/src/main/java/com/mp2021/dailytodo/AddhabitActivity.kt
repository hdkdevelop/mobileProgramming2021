package com.mp2021.dailytodo

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.mp2021.dailytodo.data.Habit
import com.mp2021.dailytodo.databinding.ActivityAddhabitBinding
import java.util.*
import kotlin.collections.ArrayList

class AddhabitActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddhabitBinding
    lateinit var DB: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivityAddhabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        init()
        initSpinner()
    }

    private fun initSpinner() {
        val CateArray = DB.getCategoryName()
        val adapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item,CateArray)
        binding.apply {
            habitcatespinner.adapter = adapter
            habitcatespinner.setSelection(0)
            habitcatespinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                        }
                        1 -> {
                        }
                        2 -> {
                        }
                    }
                }
            }
        }
    }

    private fun init() {

        DB = Database(this)

        binding.apply {

            habitname.text.clear()
            habitdetail.text.clear()

            btadd.setOnClickListener {
                //모든 내용 null이 아닐때만 습관 추가하고 습관목록화면으로 가기
                val habitname = habitname.text.toString()
                val habitdetail = habitdetail.text.toString()
                val habitcategory = habitcatespinner.selectedItem.toString()

                val currentDateTime = Calendar.getInstance().time

                if(habitname == "" || habitdetail == "" || habitcategory == ""){
                    Toast.makeText(this@AddhabitActivity, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    val habitset = Habit(1, habitname, habitdetail, currentDateTime, 0, false, false, habitcategory, 0)
                    //habitid는 auto increment
                    val result = DB.insertHabit(habitset)
                    if(result){
                        Toast.makeText(this@AddhabitActivity, "습관 추가 성공", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@AddhabitActivity, "습관 추가 실패", Toast.LENGTH_SHORT).show()
                    }
                    clearEditText()
                    finish()
                }

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
            habitname.text.clear()
            habitdetail.text.clear()
        }
    }

}