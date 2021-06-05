package com.mp2021.dailytodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView

class DatailedActivity : AppCompatActivity() {
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
        var backbtn=findViewById<Button>(R.id.button1)
        var favorbtn=findViewById<Button>(R.id.button2)
        var editbtn=findViewById<Button>(R.id.button3)
        nametext=findViewById(R.id.textView2)
        detailtext=findViewById(R.id.textView4)
        categorytext=findViewById(R.id.textView6)
        streaktext=findViewById(R.id.textView8)
        totaltext=findViewById(R.id.textView10)
        starttext=findViewById(R.id.textView12)
        inittext()
        editbtn.setOnClickListener{
            var intent = Intent(this, EditDetailedActivity::class.java)
            intent.putExtra("name",nametext.text.toString())
            intent.putExtra("detail",detailtext.text.toString())
            intent.putExtra("category",categorytext.text.toString())
            intent.putExtra("streak",streaktext.text.toString().toInt())
            intent.putExtra("total",totaltext.text.toString().toInt())
            intent.putExtra("start",starttext.text.toString())
            intent.putExtra("id",id)
            startActivity(intent)
        }
        backbtn.setOnClickListener{
            onBackPressed()
        }
        favorbtn.setOnClickListener{
            //Todo-즐겨찾기 난감하네
        }
    }
    override fun onResume() {
        super.onResume()
        inittext()
    }
    private fun inittext{
        if(id==-1){
            nametext.setText("error")
            detailtext.setText("error")
            categorytext.setText("error")
            streaktext.setText("error")
            totaltext.setText("error")
            starttext.setText("error")
        }else{
            //TODO-db에서 정보가져와야되는데 모르겠음.
        }
    }
}