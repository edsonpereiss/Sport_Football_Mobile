package com.aroniez.futaa.ui.matches.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.AppExecutors
import com.aroniez.futaa.R
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import java.util.*


class MatchesHomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = MatchHomeFragmentsAdapter(childFragmentManager, context!!)
        tabLayout.setupWithViewPager(viewpager)
        viewpagerTabsLayout.visibility = View.VISIBLE
        viewpager.currentItem = 1
        viewpager.offscreenPageLimit = 2


        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                AppExecutors().mainThread().execute {
                    //switchTabs()
                }
            }
        }, 10000, 1000 * 60)
    }

    private fun switchTabs() {
        if (viewpager != null) {
            when (viewpager.currentItem) {
                0 -> viewpager.currentItem = 1
                1 -> viewpager.currentItem = 2
                else -> viewpager.currentItem = 0
            }
        }
    }

}
