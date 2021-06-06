package com.mp2021.dailytodo.data;

import java.io.Serializable
import java.util.*

data class Habit(var id: Long?, var title: String, var detail:String, var startDate: Date, var streak:Int, var completed:Boolean, var favored:Boolean,var catagory:String, var completeddate: Date): Serializable {}
