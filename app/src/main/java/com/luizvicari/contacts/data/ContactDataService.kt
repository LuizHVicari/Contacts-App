package com.luizvicari.contacts.data

import kotlin.random.Random

class ContactDataSource private constructor() {
    companion object {
        val instance: ContactDataSource by lazy {
            ContactDataSource()
        }
    }

    private val contacts: MutableList<Contact> = mutableListOf()

    init {
        contacts.addAll(mockContacts())
    }

    fun findAll(): List<Contact> = contacts.toList()

    fun findAllAsMap() = findAll().groupByInitial()

    fun findById(id: Int): Contact? = contacts.firstOrNull { it.id == id }

    fun save(contact: Contact): Contact = if (contact.id > 0) {
        val index = contacts.indexOfFirst { it.id == contact.id }
        contact.also { contacts[index] = it }
    } else {
        val maxId = contacts.maxBy { it.id }.id
        contact.copy(id = maxId + 1).also { contacts.add(it) }
    }

    fun delete(contact: Contact) {
        if (contact.id > 0) {
            contacts.removeIf { it.id == contact.id }
        }
    }
}

fun mockContacts(): List<Contact> {
    val firstNames = listOf("João", "José", "Joana", "Everton", "Marcos", "André", "Anderson", "Antônio")
    val lastNames = listOf("Silva", "Oliveira", "Cardoso", "Brasil", "Santos", "Cordeiro")
    val contacts: MutableList<Contact> = mutableListOf()
    for (i in 0 .. 19) {
        var generatedContact = false
        while (!generatedContact) {
            val isFavorite: Boolean = Random.nextInt(2) >= 1
            val firstNameIndex = Random.nextInt(firstNames.size)
            val lastNameIndex = Random.nextInt(lastNames.size)
            val newContact = Contact(
                i+1,
                firstNames[firstNameIndex],
                lastNames[lastNameIndex],
                isFavorite = isFavorite
            )
            if (!contacts.any { it.firstName == newContact.firstName && it.lastName == newContact.lastName}) {
                contacts.add(newContact)
                generatedContact = true
            }
        }
    }
    return contacts
}