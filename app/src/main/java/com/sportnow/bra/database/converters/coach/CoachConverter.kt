package com.sportnow.bra.database.converters.coach

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.Coach
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CoachConverter {
    @TypeConverter
    fun fromString(value: String): Coach? {
        val listType = object : TypeToken<Coach>() {}.type
        return Gson().fromJson<Coach>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Coach?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}