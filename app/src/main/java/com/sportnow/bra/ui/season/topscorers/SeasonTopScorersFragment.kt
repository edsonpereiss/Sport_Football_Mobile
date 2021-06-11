package com.sportnow.bra.ui.season.topscorers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sportnow.bra.R
import com.sportnow.bra.api.RetrofitAdapter
import com.sportnow.bra.api.callbacks.SeasonTopScorersCallback
import com.sportnow.bra.ui.season.LeagueDetailActivity
import com.sportnow.bra.utils.hideLoadingProgress
import com.sportnow.bra.utils.showLoadingProgress
import com.sportnow.bra.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_top_scorers.tabLayout
import kotlinx.android.synthetic.main.fragment_top_scorers.viewpager
import kotlinx.android.synthetic.main.fragment_viewpager_with_loading.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SeasonTopScorersFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coverage = (context as LeagueDetailActivity).getSeasonCoverage()
        if (coverage != null) {
            if (coverage.topscorer_assists && coverage.topscorer_cards && coverage.topscorer_goals) {
                fetchDataFromServer()
            } else {
                showMessageLayout("Top scorers not available at the moment", viewpagerLoading)
            }
        }
    }

    private fun fetchDataFromServer() {
        showLoadingProgress(viewpagerLoading)
        val callback = RetrofitAdapter.createAPI().seasonTopPlayers((context as LeagueDetailActivity).getSeasonId())
        callback.enqueue(object : Callback<SeasonTopScorersCallback> {
            override fun onFailure(call: Call<SeasonTopScorersCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
            }

            override fun onResponse(call: Call<SeasonTopScorersCallback>, response: Response<SeasonTopScorersCallback>) {
                hideLoadingProgress(viewpagerLoading)
                if (response.isSuccessful) {
                    val topScorersCallback = response.body()!!.data
                    viewpager.adapter = SeasonTopScorersFragmentsAdapter(childFragmentManager, topScorersCallback)
                    tabLayout.setupWithViewPager(viewpager)
                    viewpager.offscreenPageLimit = 2
                    viewpagerTabsLayout.visibility = View.VISIBLE
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
                }
            }
        })
    }

}
