package com.mp2021.dailytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class DatailedActivity : AppCompatActivity() {
    var id=-1
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
        //값 db에서 받아와야함.
        //일단 판별부분은 이름과 작성일자로 판별예정.
        //정보 전송과 수신은 put extra와 get extra사용.
    }
    override fun onResume() {
        super.onResume()
        //다시 변경된값 로드해야함.
    }
}