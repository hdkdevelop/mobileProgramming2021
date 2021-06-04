package com.mp2021.dailytodo.data;

import java.io.Serializable
import java.util.*

data class Habit(var id: Long?, var title: String, var categoryId: Long?, var startDate: Date, var detail:String, var favored:Boolean,var catagotyname:String): Serializable {}
