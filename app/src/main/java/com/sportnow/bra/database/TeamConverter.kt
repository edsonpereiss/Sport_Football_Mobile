package com.sportnow.bra.database

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.team.TeamData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TeamConverter {
    @TypeConverter
    fun fromString(value: String): TeamData? {
        val listType = object : TypeToken<TeamData>() {}.type
        return Gson().fromJson<TeamData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: TeamData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}