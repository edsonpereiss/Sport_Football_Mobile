package com.sportnow.bra.models.seasons

import com.sportnow.bra.api.callbacks.MatchesCallback


class SeasonFixtures(
        val id: Long,
        val name: Boolean,
        val league_id: Long,
        val is_current_season: Boolean,
        val current_round_id: Long,
        val current_stage_id: Long,
        val fixtures: MatchesCallback
)