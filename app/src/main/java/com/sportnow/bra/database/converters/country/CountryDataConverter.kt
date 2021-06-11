package com.sportnow.bra.database.converters.country

import androidx.room.TypeConverter
import com.sportnow.bra.models.country.CountryData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CountryDataConverter {
    @TypeConverter
    fun fromString(value: String): CountryData? {
        val listType = object : TypeToken<CountryData>() {}.type
        return Gson().fromJson<CountryData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CountryData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}