package com.aroniez.futaa.ui.matches.fragments

import com.aroniez.futaa.api.RetrofitAdapter
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.ui.common.BaseMatchesFragment
import com.aroniez.futaa.utils.DateTimeUtil
import retrofit2.Call


class YesterdayMatchesFragment : BaseMatchesFragment() {
    override fun getMatchesApiCall(): Call<MatchesCallback> {
        return RetrofitAdapter.createAPI().fixturesForDate(DateTimeUtil.getCustomDate(-1))
    }

}