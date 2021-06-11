package com.sportnow.bra.ui.matches.h2h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sportnow.bra.R
import com.sportnow.bra.api.RetrofitAdapter
import com.sportnow.bra.api.callbacks.MatchesCallback
import com.sportnow.bra.ui.matches.MatchDetailActivity
import com.sportnow.bra.utils.*
import kotlinx.android.synthetic.main.include_ads_layout.*
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class H2HFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        loadMediumBannerAds(context!!, advertLayout)
    }

    private fun loadData() {
        val match = (context as MatchDetailActivity).getMatchObject()
        showLoadingProgress(baseNestedLayout)
        val callback = RetrofitAdapter.createAPI().getH2H(match!!.localteam_id, match.visitorteam_id)
        callback.enqueue(object : Callback<MatchesCallback> {
            override fun onFailure(call: Call<MatchesCallback>, t: Throwable) {
                showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
            }

            override fun onResponse(call: Call<MatchesCallback>, response: Response<MatchesCallback>) {
                hideLoadingProgress(baseNestedLayout)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.data.isNotEmpty()) {
                            val adapter = H2HMatchesAdapter(response.body()!!.data, context!!)
                            baseRecyclerView.layoutManager = LinearLayoutManager(context)
                            baseRecyclerView.adapter = adapter
                            baseRecyclerView.isNestedScrollingEnabled = true
                        } else {
                            showMessageLayout("No H2H matches found", baseNestedLayout)
                        }
                    } else {
                        showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                    }

                } else {
                    showMessageLayout(context!!.getString(R.string.error_generic_message), baseNestedLayout)
                }
            }
        })
    }
}