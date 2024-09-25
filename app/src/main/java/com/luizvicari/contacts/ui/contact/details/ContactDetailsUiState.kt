package com.luizvicari.contacts.ui.contact.details

import com.luizvicari.contacts.data.Contact

data class ContactDetailsUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val contact: Contact = Contact(),
    val showConfirmationDialog: Boolean = false
)
