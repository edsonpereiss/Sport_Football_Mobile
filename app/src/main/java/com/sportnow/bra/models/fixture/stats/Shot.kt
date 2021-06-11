package com.sportnow.bra.models.fixture.stats

import java.io.Serializable

data class Shot(
        val total: Int,
        val ongoal: Int,
        val offgoal: Int,
        val insidebox: Int,
        val outsidebox: Int
) : Serializable