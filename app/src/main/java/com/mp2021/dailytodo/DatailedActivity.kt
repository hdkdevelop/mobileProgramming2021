package com.mp2021.dailytodo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView

class DatailedActivity : AppCompatActivity() {
    var db = Database(this)
    var id=-1
    lateinit var nametext:TextView
    lateinit var detailtext:TextView
    lateinit var categorytext:TextView
    lateinit var streaktext:TextView
    lateinit var totaltext:TextView
    lateinit var starttext:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datailed)
        if (intent.hasExtra("id")){
            id = intent.getIntExtra("id",-1)
        }else{
            id=-1
        }
        init()
    }
    private fun init(){
        val backBtn=findViewById<Button>(R.id.button1)
        val favorBtn=findViewById<Button>(R.id.button2)
        val editBtn=findViewById<Button>(R.id.button3)
        nametext=findViewById(R.id.textView2)
        detailtext=findViewById(R.id.textView4)
        categorytext=findViewById(R.id.textView6)
        streaktext=findViewById(R.id.textView8)
        totaltext=findViewById(R.id.textView10)
        starttext=findViewById(R.id.textView12)
        inittext()
        editBtn.setOnClickListener{
            val intent = Intent(this, EditDetailedActivity::class.java)
            intent.putExtra("name",nametext.text.toString())
            intent.putExtra("detail",detailtext.text.toString())
            intent.putExtra("category",categorytext.text.toString())
            intent.putExtra("streak",streaktext.text.toString().toInt())
            intent.putExtra("total",totaltext.text.toString().toInt())
            intent.putExtra("start",starttext.text.toString())
            intent.putExtra("id",id)
            startActivity(intent)
        }
        backBtn.setOnClickListener{
            onBackPressed()
        }
        favorBtn.setOnClickListener{
            //Todo-즐겨찾기 난감하네
        }
    }
    override fun onResume() {
        super.onResume()
        inittext()
    }
    @SuppressLint("SetTextI18n")
    private fun inittext(){
        if(id==-1){
            nametext.setText("error")
            detailtext.setText("error")
            categorytext.setText("error")
            streaktext.setText("error")
            totaltext.setText("error")
            starttext.setText("error")
        }else{
            nametext.setText(db.getName(id))
            detailtext.setText(db.getDetail(id))
            categorytext.setText(db.getCategory(id))
            streaktext.setText(db.getStreak(id))
            totaltext.setText(db.getTotal(id))
            starttext.setText(db.getStart(id))
        }
    }
}