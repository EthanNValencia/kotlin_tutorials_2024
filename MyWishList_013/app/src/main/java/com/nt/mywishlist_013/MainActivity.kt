package com.nt.mywishlist_013

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nt.mywishlist_013.data.DummyWishes
import com.nt.mywishlist_013.data.Wish
import com.nt.mywishlist_013.data.WishViewModel
import com.nt.mywishlist_013.ui.theme.MyWishList_013Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWishList_013Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddScreen: Screen("add_screen")
}

@Composable
fun Navigation(wishViewModel: WishViewModel = viewModel(), navHostController: NavHostController = rememberNavController()) {
    NavHost(navController = navHostController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(wishViewModel = wishViewModel, navController = navHostController)
        }
        composable(route = Screen.AddScreen.route) {
            AddEditScreen(id = 0L, wishViewModel = wishViewModel, navController = navHostController)
        }
    }
}

@Composable
fun AddEditScreen(
    id: Long,
    wishViewModel: WishViewModel,
    navController: NavController
) {

    @Composable
    fun pickScaffoldTitle(): String {
        if(id != 0L) {
            return stringResource(id = R.string.update_wish)
        } else {
            return stringResource(id = R.string.add_wish)
        }
    }

    fun onClick() {
        if(wishViewModel.wishTitleState.isNotEmpty() && wishViewModel.wishDescriptionState.isNotEmpty()) {
            // update wish
        } else {
            // add wish
        }
    }

    Scaffold(topBar = {TopBarComponent(title = pickScaffoldTitle()){} }) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.height(10.dp))
            WishTextField(label = "Title", value = wishViewModel.wishTitleState, onValueChanged = {
                wishViewModel.onWishTitleChange(it)
            })
            WishTextField(label = "Description", value = wishViewModel.wishDescriptionState, onValueChanged = {
                wishViewModel.onWishDescriptionChange(it)
            })
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                onClick()
            }) {
                Text(text = pickScaffoldTitle())
            }
        }
    }
}

@Composable
fun WishTextField(label: String, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.dark_green),
            unfocusedBorderColor = colorResource(id = R.color.dark_green),
            cursorColor = colorResource(id = R.color.dark_green),
            focusedLabelColor = colorResource(id = R.color.dark_green),
            unfocusedLabelColor = colorResource(id = R.color.dark_green)
        ),
        label = {
        Text(
            text = label,
            color = Color.Black
        )
    })
}

@Preview
@Composable
fun Preview() {
    WishTextField(label = "Test", value = "Test", onValueChanged = {})
}

// colorResource(id = R.color.dark_green)
@Composable
fun HomeScreen(wishViewModel: WishViewModel, navController: NavController) {
    val context = LocalContext.current

    fun clickBackButton() {
        Toast.makeText(context, "Button was clicked!", Toast.LENGTH_LONG).show()
        navController.navigate(Screen.AddScreen.route)
    }

    Scaffold(topBar = { TopBarComponent(title = "WishList", onBackNavClicked = {
        clickBackButton()
    })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { clickBackButton() },
            modifier = Modifier.padding(all = 20.dp),
            backgroundColor = colorResource(id = R.color.lime_green)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = colorResource(id = R.color.black)
            )
        }
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(DummyWishes.wishList) {
                    wish -> WishItem(wish = wish) { }
            }
        }
    }
}

@Composable
fun TopBarComponent(title: String, onBackNavClicked: () -> Unit) {
    val navigationIcon: (@Composable () -> Unit)? = {
        if(title.contains("WishList")) {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = colorResource(id = R.color.black),
                    contentDescription = null
                )
            }
        } else {
            null
        }
    }

    TopAppBar(title = {
        Text(text = title, color = colorResource(id = R.color.black), fontWeight = FontWeight.Bold, modifier = Modifier
            .padding(1.dp)
            .heightIn(max = 24.dp))
    },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.lime_green),
        navigationIcon = navigationIcon
    )
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
        elevation = 10.dp,
        backgroundColor = Color.White
        ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}