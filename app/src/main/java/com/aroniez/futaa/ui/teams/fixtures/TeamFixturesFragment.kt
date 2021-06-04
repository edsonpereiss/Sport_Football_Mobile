package com.aroniez.futaa.ui.teams.fixtures

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.models.fixture.color.TeamColor
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.teams.TeamDetailActivity
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.fragment_viewpager_with_loading.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamFixturesFragment : Fragment() {

    private var team: Team? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_with_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchTeamDetailFromAPI((context as TeamDetailActivity).getTeam().id)
    }

    fun getTeamFixtures() = team


    private fun fetchTeamDetailFromAPI(teamId: Long) {
        showLoadingProgress(viewpagerLoading)
        val callback = RetrofitAdapter.createAPI().teamFixturesById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                hideLoadingProgress(viewpagerLoading)
                if (response.isSuccessful) {
                    team = response.body()!!.data
                    viewpager.adapter = TeamFixturesFragmentsAdapter(childFragmentManager, team!!, context!!)
                    tabLayout.setupWithViewPager(viewpager)

                    val colors = (context as TeamDetailActivity).intent.getSerializableExtra("colors") as TeamColor?
                    initializeTabs(colors)

//                    tabLayout.setBackgroundColor(resources.getColor(R.color.white))
//                    tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
//                    tabLayout.tabTextColors = ContextCompat.getColorStateList(context!!, R.color.primary)
                    viewpagerTabsLayout.visibility = View.VISIBLE

                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), viewpagerLoading)
                }
            }
        })
    }


    fun initializeTabs(colors: TeamColor?) {
        if (colors != null) {
            if (colors.color != null) {
                tabLayout.setBackgroundColor(Color.parseColor(colors.color))
                if (ColorUtils.calculateLuminance(Color.parseColor(colors.color)) < 0.5) {
                    tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.white))
                    tabLayout.tabTextColors = ContextCompat.getColorStateList(context!!, R.color.white)
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.white))
                } else {
                    tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                    tabLayout.tabTextColors = ContextCompat.getColorStateList(context!!, R.color.primary)
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context!!, R.color.primary))
                }
            } else {
                tabLayout.setBackgroundColor(resources.getColor(R.color.white))
                tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                tabLayout.tabTextColors = ContextCompat.getColorStateList(context!!, R.color.primary)
            }
        } else {
            tabLayout.setBackgroundColor(resources.getColor(R.color.white))
            tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
            tabLayout.tabTextColors = ContextCompat.getColorStateList(context!!, R.color.primary)
        }
    }

}
