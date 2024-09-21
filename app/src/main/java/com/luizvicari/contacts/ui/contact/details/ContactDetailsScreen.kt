package com.luizvicari.contacts.ui.contact.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luizvicari.contacts.R
import com.luizvicari.contacts.data.Contact
import com.luizvicari.contacts.ui.theme.ContactsTheme
import com.luizvicari.contacts.ui.utils.composables.ContactAvatar
import com.luizvicari.contacts.ui.utils.composables.FavoriteIconButton
import java.time.format.DateTimeFormatter

@Composable
fun ContactDetailScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar(
                isDeleting = false,
                contact = Contact(),
                onBackPressed= {},
                onDeletePressed= {},
                onEditPressed= {},
                onFavoritePressed= {}

            )
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    modifier: Modifier = Modifier,
    isDeleting: Boolean = false,
    contact: Contact = Contact(),
    onBackPressed: () -> Unit,
    onDeletePressed: () -> Unit,
    onEditPressed: () -> Unit,
    onFavoritePressed: () -> Unit
    ) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text("")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.return_screen)
                )
            }
        },
        actions = {
            if (isDeleting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(all = 16.dp),
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(onClick = onEditPressed) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
                FavoriteIconButton(
                    isFavorite = contact.isFavorite,
                    onFavoritePressed=onFavoritePressed,
                )
                IconButton(onClick = onDeletePressed) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete)
                    )
                }
            }
        }
    )
}


@Composable
private fun ContactDetails(
    modifier: Modifier = Modifier,
    contact: Contact,
    isDeleting: Boolean = false
    ) {
    Column(
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(
            firstName = contact.firstName,
            lastName = contact.lastName,
            size = 150.dp,
            textStyle = MaterialTheme.typography.displayLarge
        )
        Spacer(Modifier.size(20.dp))
        Text(
            text = contact.fullName,
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuickAction(
                imageVector = Icons.Filled.Phone,
                text = stringResource(R.string.call),
                onPressed = { },
                enabled = contact.phoneNumber.isNotBlank()
            )
            QuickAction(
                imageVector = Icons.Filled.Sms,
                text = stringResource(R.string.send_sms),
                onPressed = { },
                enabled = contact.phoneNumber.isNotBlank()
            )
            QuickAction(
                imageVector = Icons.Filled.Videocam,
                text = stringResource(R.string.video_call),
                onPressed = { },
                enabled = contact.phoneNumber.isNotBlank()
            )
            QuickAction(
                imageVector = Icons.Filled.Email,
                text = stringResource(R.string.send_email),
                onPressed = { },
                enabled = contact.email.isNotBlank()
            )
        }
        Card(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(all=16.dp),
                text = stringResource (R.string.contact_info),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            ContactInfo(
                imageVector = Icons.Outlined.Phone,
                value = contact.phoneNumber.ifBlank { stringResource(R.string.add_phone) },
                enabled = contact.phoneNumber.isNotBlank() && !isDeleting,
                onPressed = {}
            )
            ContactInfo(
                imageVector = Icons.Outlined.Mail,
                value = contact.phoneNumber.ifBlank { stringResource(R.string.add_mail) },
                enabled = contact.email.isNotBlank() && !isDeleting,
                onPressed = {}
            )
            Spacer(Modifier.size(8.dp))
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical=8.dp)
        )
        val formattedDateTime = contact.createdAt.format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        )
        Text(
            text = stringResource(R.string.created_at_date, formattedDateTime),
            style = MaterialTheme.typography.labelSmall
        )


    }
}

@Composable
private fun QuickAction(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit,
    imageVector: ImageVector,
    text: String,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            enabled = enabled,
            onClick = onPressed
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = text,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun ContactInfo(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    value: String,
    enabled: Boolean,
    onPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onPressed
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = value
        )
        Spacer(Modifier.size(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactInfoPreview() {
    ContactsTheme {
        ContactInfo(
            imageVector = Icons.Filled.Edit,
            value = "Teste",
            enabled = true,
            onPressed = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuickActionPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        QuickAction(
            onPressed = {},
            imageVector = Icons.Filled.Edit,
            text = "Test"
        )
    }
}



@Preview(showBackground = true)

@Composable
private fun ContactDetailPreview(modifier: Modifier = Modifier) {
    ContactsTheme {
        ContactDetails(
            contact = Contact()
        )
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    ContactsTheme {
        AppBar(
            onBackPressed= {  },
            onDeletePressed= {  },
            onEditPressed= {  },
            onFavoritePressed= {  }
        )
    }
}