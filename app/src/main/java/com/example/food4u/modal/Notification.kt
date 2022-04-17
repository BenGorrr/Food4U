package com.example.food4u.modal

data class Notification(
    val id: String = "",
    val title: String = "",
    val msg: String = "",
    val date: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "msg" to msg,
            "date" to date
        )
    }
}
