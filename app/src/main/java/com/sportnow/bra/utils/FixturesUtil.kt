package com.sportnow.bra.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sportnow.bra.models.fixture.Fixture
import com.sportnow.bra.models.leagues.CustomLeague
import com.sportnow.bra.ui.matches.MatchAdapterBundle
import com.sportnow.bra.ui.matches.MatchesAdapter

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