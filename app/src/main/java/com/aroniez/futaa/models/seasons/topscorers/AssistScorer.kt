package com.aroniez.futaa.models.seasons.topscorers

import com.aroniez.futaa.models.fixture.lineup.PlayerData


class AssistScorer(
        val position: Int,
        val season_id: Long,
        val player_id: Long,
        val team_id: Long,
        val stage_id: Long,
        val assists: Int,
        val type: String,
        val player: PlayerData
)