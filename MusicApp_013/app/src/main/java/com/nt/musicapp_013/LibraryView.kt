package com.nt.musicapp_013

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun LibraryView(viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.libraryCategories) { cat ->
            LibraryItem(cat)
            Divider()
        }
    }
}

@Composable
fun LibraryItem(cat: LibraryCategory) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
        Row {
            Image(painter = painterResource(id = cat.icon), contentDescription = cat.name)
            Text(text = cat.name)
        }
        TextButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.DarkGray
            )
        }
    }
}