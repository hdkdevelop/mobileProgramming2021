package com.mp2021.dailytodo;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList

class Database(val context:Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        companion object {
                const val DB_NAME = "_.db"
                const val DB_VERSION = 1
        }

        // 디비 필요한부분 각자 추가
        override fun onCreate(db: SQLiteDatabase?) {}
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

}