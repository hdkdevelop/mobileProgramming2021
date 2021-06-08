package com.mp2021.dailytodo;


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mp2021.dailytodo.data.Category
import com.mp2021.dailytodo.data.Habit
import com.mp2021.dailytodo.data.HabitHistory
import java.util.*
import kotlin.collections.ArrayList

class Database(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        companion object {
                const val DB_NAME = "_.db"
                const val DB_VERSION = 1
                val tableName="product"//테이블 이름
                val habitid= "habit_id"//습관 id
                val title="title"//습관 이름
                val startDate= "date"//시작날짜.
                var detail="habit_detail"//습관 설명.
                val favored="favored"//0과1로 favor했는지 확인.
                val streak="streak"//연속 완료 일 수.
                val category="category"//습관이 속한 카테고리 이름
                val completed="completed"//완료했는지
                val completeddate="completed_date"//완료한 총 일 수.
                val tableName2="catagory"//테이블 이름
                val categoryId= "category_id"//카테고리id
                val categoryname="catagory_name"//카테고리 이름.
                val tableName3="history"//히스토리 테이블 이름.
                val historyid="id"//히스토리id
                val date="date_history"
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
                        "$completed integer," +
                        "$favored integer," +
                        "$category text," +
                        "$completeddate integer);"
                val create_table2="create table if not exists $tableName2(" +
                        "$categoryId integer primary key autoincrement, " +
                        "$categoryname text);"
                val create_table3="create table if not exists $tableName3(" +
                        "$historyid integer primary key autoincrement, " +
                        "$habitid text," +
                        "$date text);"

                db!!.execSQL(create_table2)
                db.execSQL(create_table)
                db.execSQL(create_table3)
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
                values.putNull(habitid)
//              values.put(categoryId, habit.categoryId)
                values.put(title, habit.title)
                values.put(detail, habit.detail)
                values.put(startDate, habit.startDate.toString())
                values.put(streak, habit.streak)
                values.put(completed, habit.completed)
                values.put(favored, habit.favored)
                values.put(category, habit.catagory)
                values.putNull(completeddate)
                val db = writableDatabase
                val flag = db.insert(tableName, null, values)>0
                db.close()
                return flag
        }

        fun insertHabitHist(habitHistory: HabitHistory){
                val values = ContentValues()
                values.putNull(historyid)
                values.put(habitid,habitHistory.habitId.toString())
                values.put(date, habitHistory.date)
                val db = writableDatabase
                db.insert(tableName3,null,values)
        }
        fun deleteHistory(id2:Int, date2:String) {
                val db = this.writableDatabase
                db.delete(tableName3, "$habitid=? AND $date=?", arrayOf(id2.toString(), date2))
                db.close()
        }
        fun isHistory(id2:Int, date2:String):Boolean{
                val db = readableDatabase
                val query = "select * from $tableName where $historyid ='$id2' AND $date = '$date2';"
                var flag:Boolean = false
                val c = db.rawQuery(query,null)
                flag= c.moveToNext()
                c.close()
                db.close()
                return flag
        }
        fun insertCate(category: Category):Boolean{
                val values = ContentValues()
                values.putNull(categoryId)
                values.put(categoryname, category.categoryname)
                val db = writableDatabase
                val flag = db.insert(tableName2, null, values)>0
                db.close()
                return flag
        }

        //즐찾 확인.(메인확인에 보여주기 위해.)
        fun getFavored():ArrayList<MyHabit>{
                var AL = ArrayList<MyHabit>()
                var AL2 :MyHabit
                val database=readableDatabase
                val query = "select * from $tableName;"
                val c = database.rawQuery(query,null)
                while(c.moveToNext()){
                        AL2= MyHabit(c.getString(c.getColumnIndex(title)),
                                c.getString(c.getColumnIndex(startDate)),
                                c.getInt(c.getColumnIndex(streak)),
                                c.getString(c.getColumnIndex(detail)),
                                c.getLong(c.getColumnIndex(habitid)),
                                c.getInt(c.getColumnIndex(completed)))
                        AL.add(AL2)
                }
                c.close()
                database.close()
                return AL
        }
        fun getName(id:Int):String{
                val database=readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getString(c.getColumnIndex(title))
                database.close()
                c.close()
                return str

        }
        fun getDetail(id:Int):String{
                val database=readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getString(c.getColumnIndex(detail))
                database.close()
                c.close()
                return str
        }
        fun getCategory(id:Int):String{
                val database=readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getString(c.getColumnIndex(category))
                database.close()
                c.close()
                return str
        }
        fun getStreak(id:Int):String{
                val database=readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getInt(c.getColumnIndex(streak)).toString()
                database.close()
                c.close()
                return str
        }
        fun getTotal(id:Int):String{
                val database=readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getInt(c.getColumnIndex(completeddate)).toString()
                database.close()
                c.close()
                return str
        }
        fun getStart(id:Int):String{
                val database=this.readableDatabase
                val query = "select * from $tableName where $habitid ='$id';"
                val c = database.rawQuery(query,null)
                var str = ""
                while(c.moveToNext())
                        str= c.getString(c.getColumnIndex(startDate))
                database.close()
                c.close()
                return str
        }
        fun updateHabit(id:Int, name:String, detail2:String):Boolean{
                val strsql = "select * from $tableName where $habitid='$id';"
                val db = writableDatabase
                val cursor = db.rawQuery(strsql, null)
                val flag = cursor.moveToFirst()
                if(flag) {
                        val values = ContentValues()
                        values.put(title, name)
                        values.put(detail, detail2)
                        db.update(
                                tableName, values, "$title=? AND $detail=?", arrayOf(
                                        cursor.getString(cursor.getColumnIndex(title)),
                                        cursor.getString(cursor.getColumnIndex(detail))
                                )
                        )
                }
                cursor.close()
                db.close()
                return flag
        }
        fun updateCompleted(id:Int,completed2:Int){
                val strsql = "select * from $tableName where $habitid='$id';"
                val db = writableDatabase
                val cursor = db.rawQuery(strsql, null)
                val flag = cursor.moveToFirst()
                if(flag) {
                        val values = ContentValues()
                        values.put(completed,completed2)
                        db.update(
                                tableName, values, "$completed=?", arrayOf(
                                        cursor.getString(cursor.getColumnIndex(completed))
                                )
                        )
                }
                cursor.close()
                db.close()
        }
}