package com.aroniez.futaa.ui.season.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.api.callbacks.SeasonMatchesCallback
import com.aroniez.futaa.api.callbacks.SeasonTopScorersCallback
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_top_scorers.tabLayout
import kotlinx.android.synthetic.main.fragment_top_scorers.viewpager
import kotlinx.android.synthetic.main.fragment_viewpager_with_loading.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SeasonFixturesFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchDataFromServer()
    }

    private fun fetchDataFromServer() {
        showLoadingProgress(viewpagerLoading)
        val callback = RetrofitAdapter.createAPI().seasonMatches((context as LeagueDetailActivity).getSeasonId())
        callback.enqueue(object : Callback<SeasonMatchesCallback> {
            override fun onFailure(call: Call<SeasonMatchesCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
            }

            override fun onResponse(call: Call<SeasonMatchesCallback>, response: Response<SeasonMatchesCallback>) {
                hideLoadingProgress(viewpagerLoading)
                if (response.isSuccessful) {
                    val topScorersCallback = response.body()!!.data
                    viewpager.adapter = SeasonMatchesFragmentsAdapter(childFragmentManager, topScorersCallback)
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
