package com.nt.musicapp_013

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.GridCells

@Composable
fun BrowseView(viewModel: MainViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(viewModel.categories) { cat ->
            BrowserItem(cat = cat, drawable = R.drawable.baseline_apps_24)
        }
    }
}

@Composable
fun BrowseViewFirstAttempt(viewModel: MainViewModel) {
    /*
    This works but the above BrowseView is a lot easier.
     */
    LazyColumn {
        itemsIndexed(viewModel.categories) { index, cat ->
            if(index % 2 == 0) {
                Row {
                    val indexPlusOne = index + 1
                    BrowserItem(cat = cat, drawable = R.drawable.baseline_apps_24, index)
                    if(viewModel.categories.size > indexPlusOne) {
                        BrowserItem(cat = viewModel.categories[indexPlusOne], drawable = R.drawable.baseline_apps_24, indexPlusOne)
                    }
                }
            }

        }
    }
}



@Composable
fun BrowserItem(cat: String, drawable: Int, index: Int) {
    Card(modifier = Modifier
        .padding(16.dp)
        .size(175.dp), border = BorderStroke(3.dp, color = Color.DarkGray)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = cat, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
            Image(painter = painterResource(id = drawable), contentDescription = cat)
            Text("#$index")
        }
    }
}
