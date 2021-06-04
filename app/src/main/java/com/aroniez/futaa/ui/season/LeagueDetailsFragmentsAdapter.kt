package com.aroniez.futaa.ui.season

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aroniez.futaa.models.leagues.Coverage
import com.aroniez.futaa.ui.season.matches.SeasonFixturesFragment
import com.aroniez.futaa.ui.season.topscorers.SeasonTopScorersFragment
import com.aroniez.futaa.ui.standings.LeagueStandingsFragment

class LeagueDetailsFragmentsAdapter(
        fragmentManager: FragmentManager,
        private val hasStandings: Boolean
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        if (hasStandings) {
            return when (position) {
                0 -> LeagueStandingsFragment()
                1 -> SeasonFixturesFragment()
                else -> SeasonTopScorersFragment()
            }
        } else {
            return when (position) {
                0 -> SeasonFixturesFragment()
                else -> SeasonTopScorersFragment()
            }
        }
    }

    override fun getCount(): Int {
        if (hasStandings) {
            return 3
        } else {
            return 2
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (hasStandings) {
            return when (position) {
                0 -> "Standings"
                1 -> "Matches"
                else -> "Top Scorers"
            }
        } else {
            return when (position) {
                0 -> "Matches"
                else -> "Top Scorers"
            }
        }

    }

}