package com.sportnow.bra.ui.teams.results

import com.sportnow.bra.models.fixture.Fixture
import com.sportnow.bra.models.team.Team
import com.sportnow.bra.ui.teams.TeamMatchesBaseFragment


class TeamHomeResultsFragment(private val team: Team) : TeamMatchesBaseFragment() {
    override fun getMatches(): ArrayList<Fixture> {
        return team.localResults.data
    }

}