package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String {
    return if(this.trim().length >= len) "${this.trim().take(len).trim()}..." else this.trim()
}

fun String.stripHtml(): String {
    return this
        .replace(" {2,}".toRegex(), " ")
        .replace("\\<.*?>".toRegex(),"")
        .trim()
}