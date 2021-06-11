package com.sportnow.bra.ui.teams.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sportnow.bra.R
import com.sportnow.bra.ui.teams.TeamDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_team_overview.*


class TeamOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeTeamOverview()
    }

    private fun initializeTeamOverview() {
        val teamOverview = (context!! as TeamDetailActivity).getTeamOverview()
        if (teamOverview != null) {
            val country = teamOverview.country.data
            Picasso.get().load(country.image_path).into(countryFlag)
            countryName.text = country.name

            val venue = teamOverview.venue
            if (venue != null) {
                Picasso.get().load(venue.data.image_path).into(venueImage)
                venueName.text = venue.data.name
            } else {
                venueLayout.visibility = View.GONE
            }

            val uefaRankingData = teamOverview.uefaranking
            if (uefaRankingData != null) {
                uefaRanking.text = uefaRankingData.data.position.toString()
            } else {
                uefaRankingLayout.visibility = View.GONE
            }

            val coach = teamOverview.coach.data
            Picasso.get().load(coach.image_path).into(coachImage)
            coachName.text = coach.common_name
        }


    }


}