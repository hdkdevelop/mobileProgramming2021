package com.mp2021.dailytodo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
   // lateinit var intent:Intent
    lateinit var adapter: MyHabitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // git test
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
//Todo 메뉴바가 제대로 안나오는 부분 확인해봐야함.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addact -> {
                var intent = Intent(this, AddhabitActivity::class.java)
                startActivity(intent)
            }
            R.id.addcate -> {
                var intent = Intent(this, AddcateActivity::class.java)
                startActivity(intent)
            }
            //TODO 이름 알게되서 완성.
        }
        return super.onOptionsItemSelected(item)
    }
    //Todo 어댑터 구현., init구현.
}