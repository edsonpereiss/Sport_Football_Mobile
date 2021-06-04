package com.aroniez.futaa.ui.teams.results

import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.teams.TeamMatchesBaseFragment


class TeamHomeResultsFragment(private val team: Team) : TeamMatchesBaseFragment() {
    override fun getMatches(): ArrayList<Fixture> {
        return team.localResults.data
    }

}