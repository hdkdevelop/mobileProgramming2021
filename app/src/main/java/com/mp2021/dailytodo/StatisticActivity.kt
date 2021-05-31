package com.mp2021.dailytodo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.mp2021.dailytodo.databinding.ActivityStatisticBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class StatisticActivity: AppCompatActivity() {
    enum class Period {
        WEEK, MONTH, YEAR
    }
    private lateinit var periodAchievementsAdapter: HorizontalPairAdapter
    private lateinit var categoryRatesAdapter: HorizontalPairAdapter
    private lateinit var binding: ActivityStatisticBinding
    private lateinit var db: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        db = Database(this)
        periodAchievementsAdapter = HorizontalPairAdapter(ArrayList())
        categoryRatesAdapter = HorizontalPairAdapter(ArrayList())

        binding.apply {
            periodSelector.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val period = Period.values()[position]
                    refreshAchievements(period)
                    refreshCategoryRate(period)
                }
            }
            periodAchievements.adapter = periodAchievementsAdapter
            categoryRates.adapter = categoryRatesAdapter
        }
    }

    private fun periodToDate(p: Period): Int {
        return when (p) {
            Period.WEEK -> 7
            Period.MONTH -> 30
            Period.YEAR -> 365
        }
    }

    private fun refreshAchievements(p: Period) {
        periodAchievementsAdapter.items.clear()
        val achievements = ArrayList<Pair<String, String>>()
        db.getAchievements(periodToDate(p)).forEach {
            val formatter = DateTimeFormatter.ofPattern("MM${getString(R.string.month)} dd${getString(R.string.day)}")
            val date = LocalDateTime.ofInstant(it.first.toInstant(), ZoneId.systemDefault())
            achievements.add(Pair(date.format(formatter), it.second.toString() + "%"))
        }
        periodAchievementsAdapter.items.addAll(achievements)
        binding.apply {
            period.text = when (p) {
                Period.WEEK -> getString(R.string.period_week)
                Period.MONTH -> getString(R.string.period_month)
                Period.YEAR -> getString(R.string.period_year)
            }
        }
        periodAchievementsAdapter.notifyDataSetChanged()
    }
    private fun refreshCategoryRate(p: Period) {
        categoryRatesAdapter.items.clear()
        val categoryRates = ArrayList<Pair<String, String>>()
        db.getCategoryRates(periodToDate(p)).forEach {
            categoryRates.add(Pair(it.first, it.second.toString() + "%"))
        }
        categoryRatesAdapter.items.addAll(categoryRates)
        categoryRatesAdapter.notifyDataSetChanged()
    }
}
