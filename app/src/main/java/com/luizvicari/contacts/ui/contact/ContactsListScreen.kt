package com.luizvicari.contacts.ui.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.luizvicari.contacts.ui.enums.Sizes
import com.luizvicari.contacts.ui.theme.ContactsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
    ) {
    val isInitialComposition = rememberSaveable {
        mutableStateOf(true)
    }
    val isLoading = rememberSaveable{ mutableStateOf(false) }
    val hasError = rememberSaveable{ mutableStateOf(false) }
    val contacts: MutableState<List<Contact>> = rememberSaveable { mutableStateOf(listOf()) }

    val loadContacts: () -> Unit = {
        isLoading.value = true
        hasError.value = false

        coroutineScope.launch {
            delay(2000)
            hasError.value = Random.nextBoolean()
            if (!hasError.value) {
                val isEmpty = Random.nextBoolean()
                contacts.value = if (isEmpty) {
                    listOf()
                } else {
                    mockContacts()
                }
            }
            isLoading.value = false
        }
    }

    if (isInitialComposition.value) {
        loadContacts()
        isInitialComposition.value = false
    }

    Scaffold(
        topBar = { AppBar(onRefreshPressed = loadContacts) },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                contacts.value = contacts.value.plus(
                    Contact(
                        firstName = "Teste",
                        lastName = "Teste",
                        isFavorite = false,
                        id = contacts.value.size + 1
                    )
                )
            }) {
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
        if (isLoading.value) {
            LoadingContent()
        } else if (hasError.value) {
            ErrorContent(
                modifier = defaultModifier,
                onTryAgainPressed = loadContacts
            )
        } else if (contacts.value.isEmpty()) {
            EmptyList()
        } else {
            List(
                modifier = defaultModifier,
                contacts = contacts.value
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
private fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(Sizes.CIRCULAR_PROGRESS_LARGE)
        )
        Spacer(modifier = Modifier.size(Sizes.CIRCULAR_PROGRESS_LARGE))
        Text(
            text = stringResource(R.string.loading_your_contacts),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    onTryAgainPressed: () -> Unit
) {
    val defaultColor = MaterialTheme.colorScheme.primary
    val textPadding = Modifier.padding(top=8.dp, start=8.dp, end=8.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.CloudOff,
            contentDescription = stringResource(R.string.an_error_happened)
        )
        Text(
            modifier = textPadding,
            text = stringResource(id = R.string.loading_error),
            style = MaterialTheme.typography.titleLarge,
            color = defaultColor
        )
        Text(
            modifier =  textPadding,
            text = stringResource(R.string.wait_and_try_again),
            style =  MaterialTheme.typography.titleSmall,
            color = defaultColor
        )
        ElevatedButton(
            onClick = onTryAgainPressed,
            modifier = Modifier.padding(top=16.dp)
        ) {
            Text(text = stringResource(R.string.try_again))
        }
    }
}

@Composable()
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

@Composable()
private fun List(
    modifier: Modifier = Modifier,
    contacts: List<Contact>
    ) {
    LazyColumn(
        modifier = modifier
    ) {
        items(contacts) {
            contact ->
            ContactListItem(contact=contact)
        }
    }
}

@Composable()
private fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact
) {
    val isFavorite: MutableState<Boolean> = rememberSaveable { mutableStateOf(contact.isFavorite) }
    ListItem(
        headlineContent = {
            Text(text = contact.firstName + " " + contact.lastName)
        },
        leadingContent = {},
        trailingContent = {
            IconButton(onClick = {
                isFavorite.value = !isFavorite.value
            }) {
                Icon(
                    imageVector = if (isFavorite.value) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (isFavorite.value) {
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
@Composable()
private fun ListPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        List(
            contacts = mockContacts()
        )
    }
}

private fun mockContacts(): List<Contact> {
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

@Preview(showBackground = true)
@Composable()
private fun EmptyListPreview() {
    ContactsTheme {
        EmptyList()
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    ContactsTheme {
        ErrorContent(
            onTryAgainPressed = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingContentPreview() {
    ContactsTheme {
        LoadingContent()
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