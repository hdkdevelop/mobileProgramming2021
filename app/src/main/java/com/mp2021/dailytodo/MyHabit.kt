package com.mp2021.dailytodo

import java.io.Serializable

data class MyHabit(var habit_name:String, var start_date:String, var streak:Int, var habit_detail:String):Serializable{
}