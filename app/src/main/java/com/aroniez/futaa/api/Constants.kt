package com.aroniez.futaa.api

//Sportsmonk API keys
const val ayoubApiKey = "g5vXzMKpaRUAHcsTFvhd2gyMGPaJfqKw0i2JK9StQyRY2eUvEa0WXaqPHVsV"
const val myApiKey = "4LhKIgp3NkNAxHfzGECE7SJwGCp5CUpCrb4LmjNYhWn2MClZhXSzXCcwRhZA"

const val API_KEY = ayoubApiKey
const val googleTranslateKey = "AIzaSyBXmalWbO2HYdu9m2FL8vfJPaQ8Kg2KKPg"

const val arabicLang = "ar"
const val englishLang = "en"
const val selectedLang = englishLang

const val BASE_URL = "https://soccer.sportmonks.com/api/v2.0/"

const val YESTERDAY_MATCHES = "yesterday"
const val TODAY_MATCHES = "today"
const val TOMORROW_MATCHES = "tomorrow"

const val RESULTS_MATCHES = "results"
const val CURRENT_MATCHES = "current"
const val UPCOMING_MATCHES = "upcoming"

const val LIVE_MATCHES = "live"
const val FAVORITE_MATCHES = "favorites"

private const val queryLimit = 10
private const val playersQueryLimit = 20
const val limit = ":limit($queryLimit|1)"

const val playerLimit = ":limit($playersQueryLimit|1)"

const val chatsBatchCount = 20

val dirtyWords = arrayOf(
        "poop",
        "crap",
        "ass",
        "dumbass",
        "fucker",
        "witch",
        "dickhead",
        "bitch",
        "whore",
        "fuck",
        "cunt",
        "bellnd",
        "shit",
        "horny",
        "sex",
        "bastard",
        "fuck"
)