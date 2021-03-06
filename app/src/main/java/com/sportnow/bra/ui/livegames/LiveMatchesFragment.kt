package com.sportnow.bra.ui.livegames

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sportnow.bra.AppExecutors
import com.sportnow.bra.database.SoccerDatabase
import com.sportnow.bra.utils.displayFixtures
import com.sportnow.bra.utils.loadMediumBannerAds
import com.sportnow.bra.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import java.util.*


class LiveMatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.sportnow.bra.R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                loadLiveMatchesFromDb()
            }
        }, 0, 1000 * 30)

        loadMediumBannerAds(context!!, advertLayout)
    }

    private fun loadLiveMatchesFromDb() {
        AppExecutors().diskIO().execute {
            val dbInstance = SoccerDatabase.getInstance(context!!)
            val liveMatches = dbInstance.fixtureDao().getLiveMatches()
            AppExecutors().mainThread().execute {
                val onlyLiveMatches = liveMatches.filter { it.time.status == "LIVE" || it.time.status == "HT" }.sortedBy { it.time.starting_at.timestamp }
                if (onlyLiveMatches.isNotEmpty()) {
                    for (match in onlyLiveMatches) {
                        Log.d("BugTracer", "match: " + match.league!!.data.name)
                    }
                    displayFixtures(onlyLiveMatches, baseRecyclerView, context!!, false)
                } else {
                    showMessageLayout("No live matches at the moment", baseNestedLayout)
                }
            }
        }
    }

}