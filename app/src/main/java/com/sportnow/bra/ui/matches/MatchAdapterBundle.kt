package com.sportnow.bra.ui.matches

import android.content.Context
import com.sportnow.bra.models.leagues.CustomLeague
import com.sportnow.bra.utils.TranslatorUtil
import com.google.cloud.translate.Translate

class MatchAdapterBundle(
        val leagues: ArrayList<CustomLeague>,
        val context: Context,
        val shouldOpenTeamDetail: Boolean
) {
    var translator: Translate = TranslatorUtil.getTranslateService(context)!!
    var favoriteMatchesIds: Array<Long> = arrayOf()
}