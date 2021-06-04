package com.aroniez.futaa.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.api.callbacks.MatchesCallback
import com.aroniez.futaa.utils.displayFixtures
import com.aroniez.futaa.utils.hideLoadingProgress
import com.aroniez.futaa.utils.showLoadingProgress
import com.aroniez.futaa.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class BaseMatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadMatchesFromServer()
    }

    abstract fun getMatchesApiCall(): Call<MatchesCallback>

    private fun loadMatchesFromServer() {
        showLoadingProgress(baseNestedLayout)
        getMatchesApiCall().enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                hideLoadingProgress(baseNestedLayout)
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<MatchesCallback>, response: Response<MatchesCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    val matches = response.body()!!
                    if (matches.data.isNotEmpty()) {
                        val nonLiveMatches = matches.data.filter { it.time.status != "LIVE" }
                        displayFixtures(nonLiveMatches, baseRecyclerView, context!!, false)
                    } else {
                        showMessageLayout("No favorite matches found", baseNestedLayout)
                    }
                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }

}
