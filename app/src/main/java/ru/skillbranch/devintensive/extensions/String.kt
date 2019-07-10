package ru.skillbranch.devintensive.extensions

fun truncate(strTmp: String?, len: Int = 16): String {
    val str: String? = strTmp?.trim()
    return if(str?.length!! >= len) "${str.substring(0, len)}..." else str
}