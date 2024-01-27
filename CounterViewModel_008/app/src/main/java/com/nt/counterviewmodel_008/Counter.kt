package com.nt.shoppinglistapp_007

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Counter(counterViewModel: CounterViewModel) {

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Count: ${counterViewModel.count.value}", modifier = Modifier.padding(10.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.padding(4.dp)) {
            Button(onClick = { counterViewModel.increment() }) {
                Text(text = "Increment +")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = { counterViewModel.decrement() }) {
                Text(text = "Decrement -")
            }
        }
    }

}