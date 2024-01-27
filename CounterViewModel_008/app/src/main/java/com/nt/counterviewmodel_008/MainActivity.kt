package com.nt.counterviewmodel_008

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nt.counterviewmodel_008.ui.theme.CounterViewModel_008Theme
import com.nt.shoppinglistapp_007.Counter
import com.nt.shoppinglistapp_007.CounterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: CounterViewModel by viewModels()
            CounterViewModel_008Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Counter(viewModel)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CounterViewModel_008Theme {
        // Counter(viewModel)
    }
}