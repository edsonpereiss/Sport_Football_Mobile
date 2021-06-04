package com.aroniez.futaa.ui.matches

import android.content.Context
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.models.leagues.CustomLeague
import com.aroniez.futaa.models.leagues.League
import com.aroniez.futaa.utils.TranslatorUtil
import com.google.cloud.translate.Translate

class MatchAdapterBundle(
        val leagues: ArrayList<CustomLeague>,
        val context: Context,
        val shouldOpenTeamDetail: Boolean
) {
    var translator: Translate = TranslatorUtil.getTranslateService(context)!!
    var favoriteMatchesIds: Array<Long> = arrayOf()
}