package com.mp2021.dailytodo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.mp2021.dailytodo.databinding.ActivityStatisticBinding

class StatisticActivity: AppCompatActivity() {
    enum class Period {
        WEEK, MONTH, YEAR
    }
    private lateinit var periodAchievementsAdapter: HorizontalPairAdapter
    private lateinit var categoryRatesAdapter: HorizontalPairAdapter
    private lateinit var binding: ActivityStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
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

    // todo: daysAgo 이전부터 오늘까지 날짜별 성취도 가져오기 (날짜별 전체 habit 중, 완료된 것 비율)
    private fun getAchievements(daysAgo: Int): ArrayList<Pair<String, String>> {
        val dummy = ArrayList<Pair<String, String>>()
        dummy.add(Pair("5월 7일", "100%"))
        dummy.add(Pair("5월 8일", "5%"))
        dummy.add(Pair("5월 9일", "100%"))
        dummy.add(Pair("5월 10일", "21%"))
        dummy.add(Pair("5월 11일", "100%"))
        dummy.add(Pair("5월 12일", "100%"))
        dummy.add(Pair("5월 13일", "33%"))
        dummy.add(Pair("5월 14일", "100%"))
        dummy.add(Pair("5월 15일", "100%"))
        return dummy
    }

    // todo: daysAgo 이전부터 오늘까지 카테고리별 습관 비율 가져오기 (기간 전체 > 카테고리 카운트)
    private fun getCategoryRates(daysAgo: Int): ArrayList<Pair<String, String>> {
        val dummy = ArrayList<Pair<String, String>>()
        dummy.add(Pair("건강해지기", "42.9%"))
        dummy.add(Pair("자기관리하기", "42.9%"))
        dummy.add(Pair("카테고리 미분류", "42.9%"))
        return dummy
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
        periodAchievementsAdapter.items.addAll(getAchievements(periodToDate(p)))
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
        categoryRatesAdapter.items.addAll(getCategoryRates(periodToDate(p)))
        categoryRatesAdapter.notifyDataSetChanged()
    }
}
