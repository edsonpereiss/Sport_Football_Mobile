package com.sportnow.bra.ui.season.topscorers

import androidx.recyclerview.widget.LinearLayoutManager
import com.sportnow.bra.models.seasons.topscorers.TopScorer
import com.sportnow.bra.ui.topscorers.TopCardScorersAdapter
import com.sportnow.bra.utils.showMessageLayout
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*
import kotlinx.android.synthetic.main.include_recyclerview_progressbar_layout.*


class SeasonCardScorersFragment(private val topScorers: TopScorer) : SeasonTopScorersBaseFragment() {
    override fun displayTopScorers() {
        if (topScorers.cardscorers.data.size > 0) {
            val adapter = TopCardScorersAdapter(topScorers.cardscorers.data, context!!)
            baseRecyclerView.layoutManager = LinearLayoutManager(context)
            baseRecyclerView.adapter = adapter
        } else {
            showMessageLayout("No card scorers at the moment", baseNestedLayout)
        }
    }

}