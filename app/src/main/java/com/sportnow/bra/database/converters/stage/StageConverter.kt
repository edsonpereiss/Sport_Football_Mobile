package com.sportnow.bra.database.converters.stage

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.stage.Stage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StageConverter {
    @TypeConverter
    fun fromString(value: String): Stage? {
        val listType = object : TypeToken<Stage>() {}.type
        return Gson().fromJson<Stage>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Stage?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}