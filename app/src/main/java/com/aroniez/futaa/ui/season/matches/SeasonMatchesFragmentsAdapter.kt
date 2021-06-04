package com.aroniez.futaa.ui.season.matches

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.models.seasons.SeasonMatches

class SeasonMatchesFragmentsAdapter(fragmentManager: FragmentManager, private val seasonMatches: SeasonMatches) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SeasonResultMatchesFragment(seasonMatches.results.data)
            else -> SeasonUpcomingMatchesFragment(seasonMatches.upcoming.data)
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Results"
            else -> "Upcoming"
        }
    }

}