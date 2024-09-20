package com.luizvicari.contacts.ui.contact

import android.provider.ContactsContract.Contacts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luizvicari.contacts.R
import com.luizvicari.contacts.ui.enums.Sizes
import com.luizvicari.contacts.ui.theme.ContactsTheme

@Composable
fun ContactsListScreen(modifier: Modifier = Modifier) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text(text= stringResource(R.string.my_contacts)) }
    )
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
    contacts: List<Contacts>
    ) {
    Column(
        modifier = modifier
    ) {
        contacts.forEach{ contact ->
            ListItem(
                headlineContent = {

                })

        }
    }

}

@Preview(showBackground = true)
@Composable()
private fun ListPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        List()
    }
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
        AppBar()
    }
}