package tn.yassin.prayertimes

data class PrayJson(
    val code: Int,
    val `data`: Data,
    val status: String
)

data class Data(
    val timings: Timings
)

data class Timings(
    val Fajr: String,
    val Asr: String,
    val Dhuhr: String,
    val Maghrib: String,
    val Isha: String,

    val Imsak: String,
    val Midnight: String,
    val Sunrise: String,
    val Sunset: String
)
