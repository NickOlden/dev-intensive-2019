package ru.skillbranch.devintensive.models

class Chat(
    val id: String,
    val memders: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {

}