package com.aroniez.futaa.ui.teams.fixtures

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.R
import com.aroniez.futaa.models.team.Team

class TeamFixturesFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val team: Team,
        private val context: Context
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamHomeFixturesFragment(team)
            1 -> TeamAwayFixturesFragment(team)
            else -> TeamUpcomingFixturesFragment(team)
        }
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.tab_home_title)
            1 -> context.getString(R.string.tab_away_title)
            else -> context.getString(R.string.tab_upcoming_title)
        }
    }

}