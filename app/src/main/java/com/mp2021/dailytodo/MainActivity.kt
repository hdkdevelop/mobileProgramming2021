package com.mp2021.dailytodo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mp2021.dailytodo.data.HabitHistory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var adapter: MyHabitAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var DB: Database
    lateinit var final_date: String
    var flag = false
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
            R.id.statistic -> {
                var intent = Intent(this, StatisticActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Todo ????????? ??????., init??????.
    override fun onResume() {
        super.onResume()
        //?????? ????????????, ????????? ?????????.
        init()
    }

    private fun init() {
        val instance = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        final_date = formatter.format(instance.time)
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        val gc = GregorianCalendar(tz)
        var hour= gc.get(GregorianCalendar.HOUR).toString()
        if(hour.toInt()<10)
            hour = "0$hour"
        var min = gc.get(GregorianCalendar.MINUTE).toString()
        if(min.toInt()<10)
            min = "0$min"
        var time = hour+":"+min


        val dateText = findViewById<TextView>(R.id.date_text)
        val timeText = findViewById<TextView>(R.id.date_text2)
        dateText.setText(final_date)
        timeText.setText(time)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        DB = Database(this)
        initData()
        adapter = MyHabitAdapter(data)
        adapter.itemClickListener = object : MyHabitAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: MyHabitAdapter.ViewHolder,
                view: View,
                data: MyHabit,
                position: Int
            ) {
                var i = Intent(this@MainActivity, DatailedActivity::class.java)
                i.putExtra("id", data.id.toInt())
                startActivity(i)
            }
        }
        adapter.itemLongClickListener = object : MyHabitAdapter.OnItemLongClickListener {
            override fun OnItemLongClick(
                holder: MyHabitAdapter.ViewHolder,
                view: View,
                data: MyHabit,
                position: Int
            ) {
                if (adapter.items[position].completed == 0) {
                    val hh = HabitHistory(1, data.id, final_date)
                    DB.insertHabitHist(hh)
                    adapter.items[position].completed = 1
                    DB.updateCompleted(data.id.toInt(), data.completed)
                    DB.plusTotal(adapter.items[position].id.toInt())
                    DB.plusStreak(adapter.items[position].id.toInt())
                    adapter.items[position].streak++
                    showNoti(adapter.items[position].habit_name, adapter.items[position].streak)
                    adapter.onBindViewHolder(holder,position)
                } else {
                    DB.deleteHistory(data.id.toInt(), final_date)
                    adapter.items[position].completed = 0
                    DB.updateCompleted(data.id.toInt(), data.completed)
                    if (DB.getStreak(data.id.toInt()).toInt() > 0) {
                        DB.minusStreak(adapter.items[position].id.toInt())
                        adapter.items[position].streak--
                    }
                    adapter.onBindViewHolder(holder,position)
                }
            }
        }
        recyclerView.adapter = adapter
        //db?????? ????????? ???????????????.
        //4?????? ????????? initData?????? ????????????, adapter??????????????????, 3????????? ?????? ?????? ???????????????.- ?????? EditDetailed?????? 3??????????????? ???????????? ????????? ??????????????????!
        //??????????????? ??????????????? ???????????? ?????????.
    }

    private fun initData() {
        //data.clear()
        var favored = DB.getFavored()
        if (favored == null)
            data.add(MyHabit("12.1", "12.2", 3, "13", 1.2.toLong(), 0))
        else
            data = favored
        for (i in data) {
            if (DB.isHistory(i.id.toInt(), final_date)) {
                i.completed = 1
                DB.updateCompleted(i.id.toInt(), 1)
            } else {
                i.completed = 0
                DB.updateCompleted(i.id.toInt(), 0)

            }
            //i.streak=DB.getStreak(i.id.toInt()).toInt()
        }
    }

    private fun showNoti(title:String, streak:Int) {
        var text = title + "??????! - " + streak + "??? ??? ?????? ???!! ?????????"
        if(streak % 7 == 0) {
            var text = title + "??????! - " + streak + "??? ??? ?????? ???!! ?????? 1????????? ????????????~~ ?????? ??? ????????? ??????????????? :)"
        }

        var builder = NotificationCompat.Builder(this, "My_channel")
            .setSmallIcon(R.drawable.ic_baseline_add_task_24)
            .setContentTitle("Daily To-Do")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // ????????? ?????? ???????????? ????????? ?????? ??? ????????? ??????
            val channel_id = "My_channel" // ????????? ?????? ?????? id ??????
            val channel_name = "????????????" // ?????? ?????? ??????
            val descriptionText = "?????????" // ?????? ????????? ??????
            val importance = NotificationManager.IMPORTANCE_DEFAULT // ?????? ???????????? ??????
            val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                description = descriptionText
            }

            // ?????? ?????? ????????? ???????????? ??????
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // ?????? ??????: ????????? ?????? ID(ex: 1002), ?????? ??????
            notificationManager.notify(1002, builder.build())
        }

    }
}