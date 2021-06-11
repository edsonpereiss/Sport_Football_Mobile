package com.sportnow.bra.ui.teams.fixtures

import com.sportnow.bra.models.team.Team
import com.sportnow.bra.ui.teams.TeamMatchesBaseFragment


class TeamHomeFixturesFragment(private val team: Team) : TeamMatchesBaseFragment() {
    override fun getMatches() = team.localFixtures.data
}