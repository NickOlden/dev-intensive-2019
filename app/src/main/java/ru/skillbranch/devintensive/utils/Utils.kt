package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullNameIn: String?): Pair<String?, String?>{
        if (fullNameIn.isNullOrBlank())
            return Pair(null, null)

        val fullName = fullNameIn.replace(" {2,}".toRegex(), " ")
        val parts: List<String> = fullName.split(" ")

        val firstName = parts.getOrNull(0)
        val lastName = "${if (parts.getOrNull(1) !== null) parts.getOrNull(1)
            else null}"

        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val fNameTmp = payload.split(" ")
        val fNameTmpTransliterated: List<String> = emptyList()
        fNameTmp.forEach { word ->
            fNameTmpTransliterated + transliterate(word)
        }

        return fNameTmp.joinToString(separator = divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String {
        val lastNameFirstLet = if(firstName?.trim() != "") firstName?.get(0) else null
        val firstNameFirstLet = if(lastName?.trim() != "") lastName?.get(0) else null
        return if(lastNameFirstLet != null && firstNameFirstLet != null) "$lastNameFirstLet$firstNameFirstLet".uppercase()
        else if(lastNameFirstLet != null && firstNameFirstLet == null) "$lastNameFirstLet".uppercase()
        else if(lastNameFirstLet == null && firstNameFirstLet != null) "$firstNameFirstLet".uppercase()
        else null.toString()
    }

    private fun transliterate(word: String?): String {
        val transliterationDictionary = mapOf(
            "А" to "A",
            "Б" to "B",
            "В" to "V",
            "Г" to "G",
            "Д" to "D",
            "Е" to "E",
            "Ё" to "JE",
            "Ж" to "ZH",
            "З" to "Z",
            "И" to "I",
            "Й" to "Y",
            "К" to "K",
            "Л" to "L",
            "М" to "M",
            "Н" to "N",
            "О" to "O",
            "П" to "P",
            "Р" to "R",
            "С" to "S",
            "Т" to "T",
            "У" to "U",
            "Ф" to "F",
            "Х" to "KH",
            "Ц" to "C",
            "Ч" to "CH",
            "Ш" to "SH",
            "Щ" to "JSH",
            "Ъ" to "HH",
            "Ы" to "IH",
            "Ь" to "JH",
            "Э" to "EH",
            "Ю" to "JU",
            "Я" to "JA"
        )

        val sb = StringBuilder()
        word?.uppercase()?.forEach { char ->
            val ch: String =
                if (transliterationDictionary[char.toString()] === null) char.toString()
                else transliterationDictionary[char.toString()].toString()
            sb.append(ch)
        }
        return sb.toString()[0] + sb.toString()[1,].lowercase()
    }
}