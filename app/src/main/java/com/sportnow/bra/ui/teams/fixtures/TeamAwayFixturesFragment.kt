package com.sportnow.bra.ui.teams.fixtures

import com.sportnow.bra.models.team.Team
import com.sportnow.bra.ui.teams.TeamMatchesBaseFragment


class TeamAwayFixturesFragment(private val team: Team) : TeamMatchesBaseFragment() {
    override fun getMatches() = team.visitorFixtures.data
}