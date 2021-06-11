package com.sportnow.bra.ui.matches.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sportnow.bra.R

class MatchHomeFragmentsAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> YesterdayMatchesFragment()
            1 -> TodayMatchesFragment()
            else -> TomorrowMatchesFragment()
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.tab_recent_title)
            1 -> context.getString(R.string.tab_today_title)
            else -> context.getString(R.string.tab_upcoming_title)
        }
    }

}