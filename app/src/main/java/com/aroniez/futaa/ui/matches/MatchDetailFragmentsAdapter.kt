package com.aroniez.futaa.ui.matches

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.aroniez.futaa.ui.matches.chats.MatchChatsFragment
import com.aroniez.futaa.ui.matches.h2h.H2HFragment
import com.aroniez.futaa.ui.matches.lineup.LineupsFragment
import com.aroniez.futaa.ui.matches.statistics.MatchStatisticsFragment
import com.aroniez.futaa.ui.matches.timeline.MatchOverviewFragment

class MatchDetailFragmentsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MatchOverviewFragment()
            1 -> MatchChatsFragment()
            2 -> LineupsFragment()
            else -> H2HFragment()
        }
    }

    override fun getCount() = 4

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Timeline"
            1 -> "Chats"
            2 -> "Lineup"
            else -> "H2H"
        }
    }

}