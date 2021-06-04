package com.aroniez.futaa.models.seasons.topscorers

import com.aroniez.futaa.models.fixture.lineup.PlayerData


class GoalScorer(
        val position: Int,
        val season_id: Long,
        val player_id: Long,
        val team_id: Long,
        val stage_id: Long,
        val goals: Int,
        val penalty_goals: Int,
        val player: PlayerData
)