package ru.skillbranch.devintensive.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"):String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

@SuppressLint("SimpleDateFormat")
fun Date.humanizeDiff(date: Date = Date()): String {
    val timestamp = System.currentTimeMillis()
    val lastVisitTimestamp = date.time
    val differenceTimestamp = timestamp - lastVisitTimestamp

    return if (differenceTimestamp in 1..59000) {
        val seconds = SimpleDateFormat("ss").format(differenceTimestamp)
        "${plural(seconds.toInt(), "секунду", "секунды", "секунд")} назад"
    }
    else if (differenceTimestamp in 60000..3599000) {
        val minutes = SimpleDateFormat("mm").format(differenceTimestamp)
        "${plural(minutes.toInt(), "минуту", "минуты", "минут")} назад"
    }
    else if (differenceTimestamp in 3600000..86399000) {
        val hours = SimpleDateFormat("HH").format(differenceTimestamp)
        "${plural(hours.toInt(), "час", "часа", "часов")} назад"
    }
    else if (differenceTimestamp in 86399000..604799999) "на этой неделе"
    else if (differenceTimestamp in 604800000..2419199999) "больше недели назад"
    else if (differenceTimestamp in 2419200000..31691519999) "больше месяца назад"
    else if (differenceTimestamp > 31691520000) "больше года назад"
    else if (differenceTimestamp in -59000..-1) {
        val seconds = SimpleDateFormat("ss").format(differenceTimestamp)
        "через ${plural(seconds.toInt(), "секунду", "секунды", "секунд")}"
    }
    else if (differenceTimestamp in -3599000..-60000) {
        val minutes = SimpleDateFormat("mm").format(differenceTimestamp)
        "через ${plural(minutes.toInt(), "минуту", "минуты", "минут")}"
    }
    else if (differenceTimestamp in -86399000..-3600000) {
        val hours = SimpleDateFormat("HH").format(differenceTimestamp)
        "через ${plural(hours.toInt(), "час", "часа", "часов")}"
    }
    else if (differenceTimestamp in -604799999..-86399000) "на этой неделе"
    else if (differenceTimestamp in -2419199999..-604800000) "больше, чем через неделю"
    else if (differenceTimestamp in -31691519999..-2419200000) "больше, чем через месяц"
    else if (differenceTimestamp < -31691520000) "больше, чем через год"
    else "только что"
}

private fun plural(num: Int, form1: String, form2: String, form3: String): String {
    val numTmp = abs(num) % 100
    val numTmpD = numTmp % 10
    val str: String

    str = when {
        numTmp in 11..19 -> form3
        numTmpD in 2..4 -> form2
        numTmpD == 1 -> form1
        else -> form3
    }

    return "$num $str"
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}