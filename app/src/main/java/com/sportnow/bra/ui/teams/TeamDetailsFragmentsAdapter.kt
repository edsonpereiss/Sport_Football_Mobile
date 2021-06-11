package com.sportnow.bra.ui.teams

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sportnow.bra.ui.teams.fixtures.TeamFixturesFragment
import com.sportnow.bra.ui.teams.overview.TeamOverviewFragment
import com.sportnow.bra.ui.teams.results.TeamResultsFragment
import com.sportnow.bra.ui.teams.squad.TeamSquadFragment
import com.sportnow.bra.ui.teams.transfers.TeamTransfersFragment

class TeamDetailsFragmentsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamOverviewFragment()
            1 -> TeamResultsFragment()
            2 -> TeamFixturesFragment()
            3 -> TeamSquadFragment()
            else -> TeamTransfersFragment()
        }
    }

    override fun getCount() = 5

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            1 -> "Results"
            2 -> "Fixtures"
            3 -> "Squad"
            else -> "Transfers"
        }
    }

}