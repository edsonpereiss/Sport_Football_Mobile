package com.sportnow.bra.database.converters.card

import androidx.room.TypeConverter
import com.sportnow.bra.models.fixture.cards.CardData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CardDataConverter {
    @TypeConverter
    fun fromString(value: String): CardData? {
        val listType = object : TypeToken<CardData>() {}.type
        return Gson().fromJson<CardData>(value, listType)
    }

    @TypeConverter
    fun fromObject(stringList: CardData?): String {
        val gson = Gson()
        return gson.toJson(stringList)
    }
}