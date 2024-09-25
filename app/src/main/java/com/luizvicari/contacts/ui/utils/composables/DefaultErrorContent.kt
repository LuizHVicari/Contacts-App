package com.luizvicari.contacts.ui.utils.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luizvicari.contacts.R
import com.luizvicari.contacts.ui.theme.ContactsTheme

@Composable
fun DefaultErrorContent(
    modifier: Modifier = Modifier,
    onTryAgainPressed: () -> Unit
) {
    val defaultColor = MaterialTheme.colorScheme.primary
    val textPadding = Modifier.padding(top=8.dp, start=8.dp, end=8.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
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

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    ContactsTheme {
        DefaultErrorContent(
            onTryAgainPressed = {}
        )
    }
}