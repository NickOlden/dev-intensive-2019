package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: Long,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = null,
    val isOnline: Boolean = false) {

    constructor(id: Long, firstName: String?, lastName: String?) : this (
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatar = null)

    constructor(id: Long): this(
            id,
            "John",
            "Snow $id")

    init {
        val introBit = "$firstName $lastName!!!"
        println("${if(firstName === "Margery") 
            "Hello, Margery " else "It is a $introBit"}\n")
    }

    data class Builder(
        var id: Long = 1L,
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = null,
        var isOnline: Boolean = false){

        fun id(id: Long) = apply { this.id = id}
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun avatar(avatar: String) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
        fun build() = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }

    companion object Factory {
        private var lastId: Long = -1L
        fun makeUser(fullName: String?) : User{
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = lastId, firstName = firstName,lastName = lastName)
        }
    }

}
