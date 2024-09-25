package com.luizvicari.contacts.ui.utils.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luizvicari.contacts.R
import com.luizvicari.contacts.ui.enums.Sizes
import com.luizvicari.contacts.ui.theme.ContactsTheme

@Composable
fun DefaultLoadingContent(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.loading_your_contacts)
) {
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
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingContentPreview() {
    ContactsTheme {
        DefaultLoadingContent()
    }
}