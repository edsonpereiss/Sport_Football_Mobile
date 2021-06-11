package com.sportnow.bra.ui.matches.fragments

import com.sportnow.bra.api.RetrofitAdapter
import com.sportnow.bra.api.callbacks.MatchesCallback
import com.sportnow.bra.ui.common.BaseMatchesFragment
import com.sportnow.bra.utils.DateTimeUtil
import retrofit2.Call


class YesterdayMatchesFragment : BaseMatchesFragment() {
    override fun getMatchesApiCall(): Call<MatchesCallback> {
        return RetrofitAdapter.createAPI().fixturesForDate(DateTimeUtil.getCustomDate(-1))
    }

}