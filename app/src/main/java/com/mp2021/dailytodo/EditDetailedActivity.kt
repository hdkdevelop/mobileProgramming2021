package com.mp2021.dailytodo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class EditDetailedActivity : AppCompatActivity() {
    var db = Database(this)
    var id=-1
    var name=""
    var detail =""
    var category = ""
    var streak=0
    var total=0
    var start=""
    lateinit var nametext: EditText
    lateinit var detailtext: EditText
    lateinit var categorytext: EditText
    lateinit var streaktext: EditText
    lateinit var totaltext: EditText
    lateinit var starttext: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_detailed)
        if (intent.hasExtra("id")){
            id = intent.getIntExtra("id",-1)
        }else{
            id=-1
        }
        if (intent.hasExtra("name")){
            name = intent.getStringExtra("name").toString()
        }else{
            name="error"
        }
        if (intent.hasExtra("detail")){
            detail = intent.getStringExtra("detail").toString()
        }else{
            detail="error"
        }
        if (intent.hasExtra("category")){
            category = intent.getStringExtra("category").toString()
        }else{
            category="error"
        }
        if (intent.hasExtra("streak")){
            streak = intent.getIntExtra("streak",-1)
        }else{
            streak = -1
        }
        if (intent.hasExtra("total")){
            total = intent.getIntExtra("total",-1)
        }else{
            total = -1
        }
        if (intent.hasExtra("start")){
            start = intent.getStringExtra("start").toString()
        }else{
            start="error"
        }
        init()
    }
    private fun init(){
        val backBtn=findViewById<Button>(R.id.button4)
        val cancelBtn=findViewById<Button>(R.id.button5)
        val editBtn=findViewById<Button>(R.id.button6)
        nametext=findViewById(R.id.textView2)
        detailtext=findViewById(R.id.textView4)
        categorytext=findViewById(R.id.textView6)
        streaktext=findViewById(R.id.textView8)
        totaltext=findViewById(R.id.textView10)
        starttext=findViewById(R.id.textView12)
        initText()
        editBtn.setOnClickListener{
            db.updateHabit(id,name,detail)
            Handler().postDelayed({
            }, 200)
            onBackPressed()
        }
        backBtn.setOnClickListener{
            onBackPressed()
        }
        cancelBtn.setOnClickListener{
            onBackPressed()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun initText(){
        if(id==-1){
            nametext.setText("error")
            detailtext.setText("error")
            categorytext.setText("error")
            streaktext.setText("error")
            totaltext.setText("error")
            starttext.setText("error")
        }else{
            nametext.setText(name)
            detailtext.setText(detail)
            categorytext.setText(category)
            streaktext.setText(streak)
            totaltext.setText(total)
            starttext.setText(start)
        }
    }

}