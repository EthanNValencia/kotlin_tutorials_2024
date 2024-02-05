package com.nt.musicapp_013

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SubscriptionView() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Manage Subscription", modifier = Modifier.padding(4.dp))
        Card(modifier = Modifier
            .padding(horizontal = 8.dp),
            elevation = 4.dp) { // Using elevation instead of Modifier.shadow seems to be better.
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column() {
                        Text(text = "Musical")
                        Text(text = "Free Tier")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "See All Plans")
                        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "See Plans")

                    }
                }
                Divider(modifier = Modifier.padding(horizontal = 20.dp))
                Row(modifier = Modifier.padding(4.dp).padding(bottom = 10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_account), contentDescription = "My Music")
                    Text(text = "Get a Plan")
                }
            }
        }
    }
}