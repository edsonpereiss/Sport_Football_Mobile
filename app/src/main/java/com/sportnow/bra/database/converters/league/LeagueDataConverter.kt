package com.sportnow.bra.database.converters.league

import androidx.room.TypeConverter
import com.sportnow.bra.models.competitions.CompetitionData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class LeagueDataConverter {
    @TypeConverter
    fun fromString(value: String): CompetitionData? {
        val listType = object : TypeToken<CompetitionData>() {}.type
        return Gson().fromJson<CompetitionData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CompetitionData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}