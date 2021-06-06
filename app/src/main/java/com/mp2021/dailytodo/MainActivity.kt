package com.mp2021.dailytodo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var adapter: MyHabitAdapter
    var data = ArrayList<MyHabit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // git test
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

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
        }
        return super.onOptionsItemSelected(item)
    }

    //Todo 어댑터 구현., init구현.
    override fun onResume() {
        super.onResume()
        //다시 로드될때, 정보들 초기화.
        init()
    }

    private fun init(){
        initData()
        adapter= MyHabitAdapter(data)
        adapter.itemClickListener = object : MyHabitAdapter.OnItemClickListener{
            override fun OnItemClick(holder: MyHabitAdapter.ViewHolder, view: View, data: MyHabit, position: Int) {
                var i = Intent(this@MainActivity, AddhabitActivity::class.java)
                i.putExtra("id",data.id)
                startActivity(i)
            }
        }
        //db에서 정보들 받아와야함.
        //4가지 정보를 initData에서 받은뒤에, adapter초기화해주고, 3가지만 받는 규칙 제정해야함.- 그냥 EditDetailed에서 3개이상이면 작동하지 않도록 제한걸면될듯!
        //즐겨찾기는 시작날짜와 이름으로 생각중.
    }
    private fun initData(){
        //db에서 정보 받아와야함
    }
}