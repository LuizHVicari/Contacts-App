package com.luizvicari.contacts.ui.utils.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.luizvicari.contacts.R
import com.luizvicari.contacts.ui.theme.ContactsTheme

@Composable
fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    onFavoritePressed: () -> Unit,
    isFavorite: Boolean = false
) {
    IconButton(onClick = onFavoritePressed) {
        Icon(
            imageVector = if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Filled.FavoriteBorder
            },
            contentDescription = stringResource(R.string.favorite),
            tint = if (isFavorite) {
                Color.Red
            } else {
                LocalContentColor.current
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteIconButtonPreview() {
    ContactsTheme {
        FavoriteIconButton(onFavoritePressed = { }, isFavorite = true)
    }
}