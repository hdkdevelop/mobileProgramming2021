package com.mp2021.dailytodo;


import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mp2021.dailytodo.data.Habit
import java.util.*
import kotlin.collections.ArrayList

class Database(val context:Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        companion object {
                const val DB_NAME = "_.db"
                const val DB_VERSION = 1
                val habitid= "habit_id"//습관 id
                val title="title"//습관 이름
                val tableName="product"//테이블 이름
                val tableName2="catagory"//테이블 이름
                val categoryId= "catagory_id"//카테고리id
                val startDate= "date"//시작날짜.
                var detail="habit_detail"//습관 설명.
                val favored="favored"//0과1로 favor했는지 확인.
                val streak="streak"//연속 완료 일 수.
                val categoryname="catagory_name"//카테고리 이름.
                val completed="completed"//완료했는지
                val completeddate="completed_date"//완료한 총 일 수.
        }

        // 디비 필요한부분 각자 추가
        //TODO 카테고리별로 1차분류필요.or카테고리를 정보에 포함시켜야함. 연속달성 일수와 총 달성 일수도 필요함,즐겨찾기관련 데이터(앱이름, 시작날짜) 즐겨찾기 개수도!-구현 요청.-main
        override fun onCreate(db: SQLiteDatabase?) {
                val create_table="create table if not exists $tableName(" +
                        "$habitid integer primary key autoincrement, " +
                        "$title text," +
                        "$detail text," +
                        "$startDate text," +
                        "$streak integer," +
                        "$completed integer" +
                        "$favored integer" +
                        "$completeddate integer);"
                val create_table2="create table if not exists $tableName2(" +
                        "$categoryId integer primary key autoincrement, " +
                        "$categoryname text);"
                db!!.execSQL(create_table2)
                db!!.execSQL(create_table)
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

        fun getAchievements(daysAgo: Int): ArrayList<Pair<Date, Int>> {
                // todo: DB now ~ daysAgo 각 date에 대해, habitHistory 갯수 / habit 갯수 * 100
                val dummy = ArrayList<Pair<Date, Int>>()
                dummy.add(Pair(Date(), 100))
                dummy.add(Pair(Date(), 5))
                dummy.add(Pair(Date(), 100))
                dummy.add(Pair(Date(), 21))
                dummy.add(Pair(Date(), 100))
                dummy.add(Pair(Date(), 100))
                dummy.add(Pair(Date(), 33))
                dummy.add(Pair(Date(), 100))
                dummy.add(Pair(Date(), 100))
                return dummy
        }

        fun getCategoryRates(daysAgo: Int): ArrayList<Pair<String, Double>> {
                // todo: DB now ~ daysAgo category별로 나눠서 %
                val dummy = ArrayList<Pair<String, Double>>()
                dummy.add(Pair("건강해지기", 42.9))
                dummy.add(Pair("자기관리하기", 42.3))
                dummy.add(Pair("카테고리 미분류", 42.9))
                return dummy
        }

        fun insertHabit(habit: Habit):Boolean{
                val values = ContentValues()
                values.put(habitid, habit.id)
                values.put(categoryId, habit.categoryId)
                values.put(title, habit.title)
                values.put(startDate, habit.startDate.toString())
                values.put(detail, habit.detail)
                values.put(favored, habit.favored)
                values.put(categoryname, habit.catagoryname)
                val db = writableDatabase
                val flag = db.insert(tableName, null, values)>0
                db.close()
                return flag
        }
        //즐찾 확인.(메인확인에 보여주기 위해.)
        fun getFavored():ArrayList<MyHabit>{
                var AL = ArrayList<MyHabit>()
                var database=readableDatabase
                var query = "select * from $tableName where $favored ='1';"
                var c = database.rawQuery(query,null)
                var attrcount = c.columnCount
                if(attrcount>3)
                        attrcount=3
                for (i in 0 until attrcount) {
                        var habit_detail = c.getString(c.getColumnIndex(detail))
                        var habit_name = c.getString(c.getColumnIndex(title))
                        var id = c.getLong(c.getColumnIndex(habitid))
                        var start_date = c.getString(c.getColumnIndex(startDate))
                        var streak = c.getInt(c.getColumnIndex(streak))
                        var completed2 = c.getInt(c.getColumnIndex(completed))
                        AL.add(MyHabit(habit_name,start_date,streak,habit_detail,id, completed2))
                }
                return AL
        }
        fun getName(id:Int):String{
                var database=readableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getString(c.getColumnIndex(title))
        }
        fun getDetail(id:Int):String{
                var database=readableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getString(c.getColumnIndex(detail))
        }
        fun getCategory(id:Int):String{
                var database=readableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getString(c.getColumnIndex(categoryname))
        }
        fun getStreak(id:Int):String{
                var database=readableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getInt(c.getColumnIndex(streak)).toString()
        }
        fun getTotal(id:Int):String{
                var database=readableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getInt(c.getColumnIndex(completeddate)).toString()
        }
        fun getStart(id:Int):String{
                var database=this.writableDatabase
                var query = "select * from $tableName where $habitid ='$id';"
                var c = database.rawQuery(query,null)
                return c.getString(c.getColumnIndex(startDate))
        }

}