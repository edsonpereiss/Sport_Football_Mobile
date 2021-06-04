package com.aroniez.futaa.utils

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.leagues.CustomLeague
import com.aroniez.futaa.models.leagues.League
import com.aroniez.futaa.ui.matches.MatchAdapterBundle
import com.aroniez.futaa.ui.matches.MatchesAdapter
import kotlinx.android.synthetic.main.include_message_layout.view.*

fun displayFixtures(fixtures: List<Fixture>, recyclerView: RecyclerView, context: Context, shouldOpenTeamDetails: Boolean) {
    //group fixtures first by league
    val groupedId = fixtures.groupBy { it.league_id }

    val leagues: ArrayList<CustomLeague>? = arrayListOf()
    groupedId.forEach { (_, fixtures) ->
        val league = fixtures[0].league!!.data
        if (fixtures[0].round != null) {
            league.round = fixtures[0].round!!.data
        }
        league.fixtures = fixtures.sortedByDescending { it.id }
        leagues!!.add(league)
    }

    val matchAdapterBundle = MatchAdapterBundle(leagues!!, context, shouldOpenTeamDetails)
    val adapter = MatchesAdapter(matchAdapterBundle)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = adapter
}