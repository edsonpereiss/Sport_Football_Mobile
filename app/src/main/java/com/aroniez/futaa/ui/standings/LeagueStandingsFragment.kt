package com.aroniez.futaa.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aroniez.futaa.R
import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.StandingsCallback
import com.aroniez.futaa.ui.season.LeagueDetailActivity
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LeagueStandingsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCompetitions()
    }


    private fun loadCompetitions() {
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI().standings((context as LeagueDetailActivity).getSeasonId())
        callback.enqueue(object : Callback<StandingsCallback> {
            override fun onFailure(call: Call<StandingsCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message) + "\n$t", baseNestedLayout)
            }

            override fun onResponse(call: Call<StandingsCallback>, response: Response<StandingsCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (response.body()!!.data.size > 0) {
                        val adapter = StandingsAdapter(response.body()!!.data, context!!)
                        if (baseRecyclerView != null) {
                            baseRecyclerView.layoutManager = LinearLayoutManager(context)
                            baseRecyclerView.adapter = adapter
                        }
                    } else {
                        showMessageLayout("Standings data not available", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }


}