package com.glowsoft.nextquest.data

import androidx.room.TypeConverter
import java.time.Duration
import java.time.Instant
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? =
        if (value != null) Instant.ofEpochMilli(value) else { null }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun fromLocalTimeToSeconds(localTime: LocalTime?): Int? = localTime?.toSecondOfDay()

    @TypeConverter
    fun fromSecondsToLocalTime(secondOfDay: Int?): LocalTime? =
        secondOfDay?.let { LocalTime.ofSecondOfDay(it.toLong()) }

    @TypeConverter
    fun toDuration(seconds: Long?): Duration? = seconds?.let { Duration.ofSeconds(it) }

    @TypeConverter
    fun toSeconds(duration: Duration?): Long? = duration?.seconds
}