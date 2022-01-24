package ru.skillbranch.devintensive.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR


fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"): String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int = 0, units: TimeUnits = TimeUnits.SECOND): Date {
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
fun Date.humanizeDiff(): String {
    val timestamp = System.currentTimeMillis()
    val lastVisitTimestamp = this.time
    val differenceTimestamp = timestamp - lastVisitTimestamp

    return if (differenceTimestamp in 1..59000) {
        val seconds = SimpleDateFormat("ss").format(differenceTimestamp)
        "${pluralCalc(seconds.toInt(), "секунду", "секунды", "секунд")} назад"
    }
    else if (differenceTimestamp in 60000..3599000) {
        val minutes = SimpleDateFormat("mm").format(differenceTimestamp)
        "${pluralCalc(minutes.toInt(), "минуту", "минуты", "минут")} назад"
    }
    else if (differenceTimestamp in 3600000..86399000) {
        val hours = SimpleDateFormat("HH").format(differenceTimestamp)
        "${pluralCalc(hours.toInt(), "час", "часа", "часов")} назад"
    }
    else if (differenceTimestamp in 86399000..604799999) "на этой неделе"
    else if (differenceTimestamp in 604800000..2419199999) "более недели назад"
    else if (differenceTimestamp in 2419200000..31691519999) "более месяца назад"
    else if (differenceTimestamp > 31691520000) "более года назад"
    else if (differenceTimestamp in -59000..-1) {
        val seconds = SimpleDateFormat("ss").format(differenceTimestamp)
        "через ${pluralCalc(seconds.toInt(), "секунду", "секунды", "секунд")}"
    }
    else if (differenceTimestamp in -3599000..-60000) {
        val minutes = SimpleDateFormat("mm").format(differenceTimestamp)
        "через ${pluralCalc(minutes.toInt(), "минуту", "минуты", "минут")}"
    }
    else if (differenceTimestamp in -86399000..-3600000) {
        val hours = SimpleDateFormat("HH").format(differenceTimestamp)
        "через ${pluralCalc(hours.toInt(), "час", "часа", "часов")}"
    }
    else if (differenceTimestamp in -604799999..-86399000) "на этой неделе"
    else if (differenceTimestamp in -2419199999..-604800000) "более, чем через неделю"
    else if (differenceTimestamp in -31691519999..-2419200000) "более, чем через месяц"
    else if (differenceTimestamp < -31691520000) "более, чем через год"
    else "только что"
}

private fun pluralCalc(num: Int, form1: String, form2: String, form3: String): String {
    val numTmp = abs(num) % 100
    val numTmpD = numTmp % 10

    val str: String = when {
        numTmp in 11..19 -> form3
        numTmpD in 2..4 -> form2
        numTmpD == 1 -> form1
        else -> form3
    }

    return "$num $str"
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            val dict: Map<Int, String> = mapOf(1 to "секунду", 2 to "секунды", 3 to "секунд")
            return pluralCalc(value, dict[1].toString(), dict[2].toString(), dict[3].toString())
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            val dict: Map<Int, String> = mapOf(1 to "минуту", 2 to "минуты", 3 to "минут")
            return pluralCalc(value, dict[1].toString(), dict[2].toString(), dict[3].toString())
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            val dict: Map<Int, String> = mapOf(1 to "час", 2 to "часа", 3 to "часов")
            return pluralCalc(value, dict[1].toString(), dict[2].toString(), dict[3].toString())
        }
    },
    DAY {
        override fun plural(value: Int): String {
            val dict: Map<Int, String> = mapOf(1 to "день", 2 to "дня", 3 to "дней")
            return pluralCalc(value, dict[1].toString(), dict[2].toString(), dict[3].toString())
        }
    };

    abstract fun plural(value: Int): String
}