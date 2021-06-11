package com.sportnow.bra.database

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.substitution.SubstitutionData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SubstitutionDataConverter {
    @TypeConverter
    fun fromString(value: String): SubstitutionData? {
        val listType = object : TypeToken<SubstitutionData>() {}.type
        return Gson().fromJson<SubstitutionData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: SubstitutionData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}