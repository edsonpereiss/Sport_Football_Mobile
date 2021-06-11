package com.sportnow.bra.ui.matches

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sportnow.bra.AppExecutors
import com.sportnow.bra.R
import com.sportnow.bra.database.SoccerDatabase
import com.sportnow.bra.models.fixture.Fixture
import com.sportnow.bra.models.leagues.CustomLeague
import com.sportnow.bra.ui.season.LeagueDetailActivity
import com.sportnow.bra.utils.MatchUtil
import com.sportnow.bra.utils.loadRandomBannerAd
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.competition_title_item.view.*
import kotlinx.android.synthetic.main.include_match_layout.view.*


class MatchesAdapter(val matchAdapterBundle: MatchAdapterBundle)
    : ExpandableRecyclerAdapter<CustomLeague, Fixture, MatchesAdapter.LeagueViewHolder, MatchesAdapter.FixtureViewHolder>(matchAdapterBundle.leagues) {
    override fun onCreateParentViewHolder(parentViewGroup: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LayoutInflater.from(parentViewGroup.context!!).inflate(R.layout.competition_title_item, parentViewGroup, false))
    }

    override fun onBindChildViewHolder(childViewHolder: FixtureViewHolder, parentPosition: Int, childPosition: Int, child: Fixture) {
        childViewHolder.bindData(child)
    }

    override fun onBindParentViewHolder(parentViewHolder: LeagueViewHolder, parentPosition: Int, parent: CustomLeague) {
        parentViewHolder.bindData(parent, parentPosition)
    }

    override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(LayoutInflater.from(childViewGroup.context!!).inflate(R.layout.include_match_layout, childViewGroup, false))
    }

    init {
        AppExecutors().diskIO().execute {
            val favoritesDao = SoccerDatabase.getInstance(matchAdapterBundle.context).favoritesDao()
            val favoriteMatchesIds = favoritesDao.getFavoriteMatchesIds()
            matchAdapterBundle.favoriteMatchesIds = favoriteMatchesIds
        }
    }

    inner class FixtureViewHolder(itemView: View) : ChildViewHolder<Fixture>(itemView) {
        fun bindData(fixture: Fixture) {
            itemView.fixtureLayout.setOnClickListener {
                val intent = Intent(matchAdapterBundle.context, MatchDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("match", fixture)
                matchAdapterBundle.context.startActivity(intent)
            }
            MatchUtil.initializeMatch(fixture, itemView, matchAdapterBundle)

        }
    }

    inner class LeagueViewHolder(itemView: View) : ParentViewHolder<CustomLeague, Fixture>(itemView) {
        fun bindData(league: CustomLeague, position: Int) {
            itemView.matchLayout.setOnClickListener {
                val intent = Intent(matchAdapterBundle.context, LeagueDetailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                intent.putExtra("season_id", league.current_season_id)
                intent.putExtra("live_standings", league.live_standings)
                intent.putExtra("logo_path", league.logo_path)
                intent.putExtra("country_name", league.country.data.name)
                intent.putExtra("league_name", league.name)
                intent.putExtra("coverage", league.coverage)
                matchAdapterBundle.context.startActivity(intent)
            }
            if (league.round != null) {
                itemView.competitionTitle.text = league.country.data.name + " " + league.name + " • Round " + league.round!!.name.toString()
            } else {
                itemView.competitionTitle.text = league.country.data.name + " " + league.name
            }
            if (position != 0 && parent.fixtures!!.size > 2) {
                loadRandomBannerAd(matchAdapterBundle.context, itemView.bannerAdsLayout)
            }
            Picasso.get().load(league.logo_path).into(itemView.countryFlag)
            //MatchUtil.initializeMatch(league, itemView, matchAdapterBundle)

        }
    }

}