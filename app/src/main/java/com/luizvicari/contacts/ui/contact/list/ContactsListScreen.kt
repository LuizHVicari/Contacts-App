package com.luizvicari.contacts.ui.contact.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luizvicari.contacts.R
import com.luizvicari.contacts.data.Contact
import com.luizvicari.contacts.data.ContactDataSource
import com.luizvicari.contacts.data.groupByInitial
import com.luizvicari.contacts.data.mockContacts
import com.luizvicari.contacts.ui.theme.ContactsTheme
import com.luizvicari.contacts.ui.utils.composables.ContactAvatar
import com.luizvicari.contacts.ui.utils.composables.DefaultErrorContent
import com.luizvicari.contacts.ui.utils.composables.DefaultLoadingContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onAddPressed: () -> Unit,
    onContactPressed: (Contact) -> Unit
    ) {
    var isInitialComposition: Boolean by rememberSaveable { mutableStateOf(false) }
    var uiState: ContactListUiState by remember { mutableStateOf(ContactListUiState())}

    val loadContacts: () -> Unit = {
        uiState = uiState.copy(isLoading = true, hasError = false)

        coroutineScope.launch {
            delay(500)
            uiState = uiState.copy(
                contacts = ContactDataSource.instance.findAllAsMap(),
                isLoading = false
            )
        }
    }

    if (isInitialComposition) {
        loadContacts()
        isInitialComposition = false
    }

    val toggleFavorite: (Contact) -> Unit = { contact ->
        val updatedContact = contact.copy(isFavorite = !contact.isFavorite)
        ContactDataSource.instance.save(updatedContact)
        uiState = uiState.copy(
            contacts = ContactDataSource.instance.findAllAsMap()
        )
    }

    Scaffold(
        topBar = { AppBar(onRefreshPressed = loadContacts) },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddPressed) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(stringResource(R.string.new_contact))
            }
        }
    ) {
        paddingValues ->
        val defaultModifier = Modifier.padding(paddingValues)
        if (uiState.isLoading) {
            DefaultLoadingContent()
        } else if (uiState.hasError) {
            DefaultErrorContent(
                modifier = defaultModifier,
                onTryAgainPressed = loadContacts
            )
        } else if (uiState.contacts.isEmpty()) {
            EmptyList()
        } else {
            List(
                modifier = defaultModifier,
                contacts = uiState.contacts,
                onFavoritePressed = toggleFavorite,
                onContactPressed = onContactPressed
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onRefreshPressed: () -> Unit
    ) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text= stringResource(R.string.my_contacts)) },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = onRefreshPressed) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
                
            }
        }
    )
}



@Composable
private fun EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_data),
            contentDescription = stringResource(R.string.no_data)
        )
        Text(
            text = stringResource(R.string.nothing_here),
            modifier = Modifier.padding(top=16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.not_contact_added),
            modifier = Modifier.padding(top=16.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun List(
    modifier: Modifier = Modifier,
    contacts: Map<String, List<Contact>>,
    onFavoritePressed: (Contact) -> Unit,
    onContactPressed: (Contact) -> Unit
    ) {
    LazyColumn(
        modifier = modifier
    ) {
        contacts.forEach { (initial, contacts) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer
                        )
                ) {
                    Text(
                        text = initial,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            items(contacts) {
                contact ->
                ContactListItem(
                    contact=contact,
                    onFavoritePressed = onFavoritePressed,
                    onContactPressed = onContactPressed)
            }
        }
    }
}

@Composable
private fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    onFavoritePressed: (Contact) -> Unit,
    onContactPressed: (Contact) -> Unit
) {
    ListItem(
        modifier = modifier.clickable { onContactPressed(contact) },
        headlineContent = {
            Text(text = contact.firstName + " " + contact.lastName)
        },
        leadingContent = {
            ContactAvatar(firstName = contact.firstName, lastName = contact.lastName )
        },
        trailingContent = {
            IconButton(onClick = { onFavoritePressed( contact )}) {
                Icon(
                    imageVector = if (contact.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (contact.isFavorite) {
                        Color.Red
                    } else {
                        LocalContentColor.current
                    }
                )

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ListPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        List(
            contacts = mockContacts().groupByInitial(),
            onFavoritePressed = {},
            onContactPressed = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
    ContactsTheme {
        EmptyList()
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        AppBar(
            onRefreshPressed = {}
        )
    }
}