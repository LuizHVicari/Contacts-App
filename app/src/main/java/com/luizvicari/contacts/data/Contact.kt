package com.luizvicari.contacts.data

import java.time.LocalDateTime
import java.util.Date

data class Contact(
    val id: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isFavorite: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    val fullName get(): String = "$firstName $lastName"
}

fun List<Contact>.groupByInitial(): Map<String, List<Contact>> {
    return sortedBy { it.fullName }.groupBy { it.firstName.take(1) }
}
