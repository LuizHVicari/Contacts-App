package com.luizvicari.contacts.ui.contact.list

import com.luizvicari.contacts.data.Contact

data class ContactListUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val contacts: Map<String, List<Contact>> = mapOf()
)
