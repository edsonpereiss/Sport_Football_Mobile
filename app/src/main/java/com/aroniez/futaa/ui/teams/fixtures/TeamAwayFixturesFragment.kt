package com.aroniez.futaa.ui.teams.fixtures

import com.aroniez.futaa.models.team.Team
import com.aroniez.futaa.ui.teams.TeamMatchesBaseFragment


class TeamAwayFixturesFragment(private val team: Team) : TeamMatchesBaseFragment() {
    override fun getMatches() = team.visitorFixtures.data
}