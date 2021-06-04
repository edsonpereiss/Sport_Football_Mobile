package com.aroniez.futaa.ui.teams

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.aroniez.futaa.R
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.TeamCallback
import com.aroniez.futaa.models.fixture.color.TeamColor
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.loadInterstialAds
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.StartAppSDK
import kotlinx.android.synthetic.main.activity_league_detail1.*
import kotlinx.android.synthetic.main.include_viewpager_tabs_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamDetailActivity : AppCompatActivity() {

    private var teamOverview: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.primary)
        }
        StartAppSDK.init(this, "204013588", false)
        StartAppAd.disableSplash()
        //StartAppAd.showAd(this)
        setContentView(R.layout.activity_league_detail1)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        loadInterstialAds(this)

        val team = intent.getSerializableExtra("team") as Team
        loadData(team.id)
        val colors = intent.getSerializableExtra("colors") as TeamColor?
        if (colors != null) {
            if (colors.color != null) {
                appBarLayout.setBackgroundColor(Color.parseColor(colors.color))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.parseColor(colors.color)
                }
                if (ColorUtils.calculateLuminance(Color.parseColor(colors.color)) < 0.5) {
                    //this is dark color
                    league_name.setTextColor(resources.getColor(R.color.white))
                } else {
                    //this is light color
                    league_name.setTextColor(resources.getColor(R.color.black))
                }
            }
        }

        Picasso.get().load(team.logo_path).placeholder(R.drawable.goals).into(leagueImage)
        league_name.text = team.name


    }

    private fun loadData(teamId: Long) {
        showLoadingProgress(fixtureLayout)
        val callback = RetrofitAdapter.createAPI().teamOverviewById(teamId)
        callback.enqueue(object : Callback<TeamCallback> {
            override fun onFailure(call: Call<TeamCallback>, t: Throwable) {
                Log.d("BugTracer", "TeamDetailActivity: error: $t")
                showMessageLayout(getString(R.string.error_generic_message), fixtureLayout)
                Toast.makeText(this@TeamDetailActivity, getString(R.string.error_generic_message), Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onResponse(call: Call<TeamCallback>, response: Response<TeamCallback>) {
                hideLoadingProgress(fixtureLayout)
                if (response.isSuccessful) {
                    teamOverview = response.body()!!.data
                    viewpager.adapter = TeamDetailsFragmentsAdapter(supportFragmentManager)
                    tabLayout.setupWithViewPager(viewpager)
                    viewpager.offscreenPageLimit = 4
                    tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

                    val colors = intent.getSerializableExtra("colors") as TeamColor?
                    initializeTabs(colors)

                    //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));
                    viewpagerTabsLayout.visibility = View.VISIBLE
                    //viewpager.cur rentItem = 0

                } else {
                    showMessageLayout(getString(R.string.error_generic_message), fixtureLayout)
                    Toast.makeText(this@TeamDetailActivity, getString(R.string.error_generic_message), Toast.LENGTH_LONG).show()
                    finish()
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
                    tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.white)
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@TeamDetailActivity, R.color.white))
                } else {
                    tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                    tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
                    tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@TeamDetailActivity, R.color.primary))
                }
            } else {
                tabLayout.setBackgroundColor(resources.getColor(R.color.white))
                tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
                tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
            }
        } else {
            tabLayout.setBackgroundColor(resources.getColor(R.color.white))
            tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.primary))
            tabLayout.tabTextColors = ContextCompat.getColorStateList(this@TeamDetailActivity, R.color.primary)
        }
    }


    fun getTeam() = intent.getSerializableExtra("team") as Team

    fun getTeamOverview() = teamOverview

}
