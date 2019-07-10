package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts: List<String>? = fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return Pair(firstName, lastName)
    }

    fun transliteration(payload: String, devider: String = " "): String {
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

        val fNameTmp = payload.split(" ")
        val sb = StringBuilder(payload.length * 2)

        fNameTmp.forEachIndexed{indexP, fName ->
            val upper = fName.toUpperCase()
            upper.forEachIndexed{index, chTmp ->
                val chU = chTmp.toString()
                var ch = ""
                ch =
                    if(index != 0 && transliterationDictionary[chU] != null)
                    transliterationDictionary[chU]?.toLowerCase()?: String()
                    else if(index == 0 && transliterationDictionary[chU] != null)
                    transliterationDictionary[chU]?: String()
                    else if(index != 0) chTmp.toString().toLowerCase() else chTmp.toString()
                sb.append(ch)
            }
            if(indexP != fNameTmp.lastIndex) sb.append(devider)
        }
        return sb.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String {
        val lastNameFirstLet = if(firstName?.trim() != "") firstName?.get(0) else null
        val firstNameFirstLet = if(lastName?.trim() != "") lastName?.get(0) else null
        if(lastNameFirstLet != null && firstNameFirstLet != null) return "$lastNameFirstLet$firstNameFirstLet".toUpperCase()
        else if(lastNameFirstLet != null && firstNameFirstLet == null) return "$lastNameFirstLet".toUpperCase()
        else if(lastNameFirstLet == null && firstNameFirstLet != null) return "$firstNameFirstLet".toUpperCase()
        else return null.toString()
    }
}