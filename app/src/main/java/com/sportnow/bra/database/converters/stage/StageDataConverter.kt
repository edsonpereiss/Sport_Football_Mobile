package com.sportnow.bra.database.converters.stage

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.stage.StageData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StageDataConverter {
    @TypeConverter
    fun fromString(value: String): StageData? {
        val listType = object : TypeToken<StageData>() {}.type
        return Gson().fromJson<StageData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: StageData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}