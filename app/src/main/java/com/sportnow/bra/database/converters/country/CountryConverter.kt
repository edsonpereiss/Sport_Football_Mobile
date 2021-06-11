package com.sportnow.bra.database.converters.country

import androidx.room.TypeConverter
import com.sportnow.bra.models.country.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CountryConverter {
    @TypeConverter
    fun fromString(value: String): Country? {
        val listType = object : TypeToken<Country>() {}.type
        return Gson().fromJson<Country>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: Country?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}