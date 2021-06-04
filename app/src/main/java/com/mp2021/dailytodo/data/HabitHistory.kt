package com.mp2021.dailytodo.data;

import java.io.Serializable
import java.util.*

data class HabitHistory(var id: Long?, var habitId: Long?, var date: Date,var Streak:Int ,var completed:Int): Serializable {}
