package com.nt.mywishlist_013

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nt.mywishlist_013.database.Wish
import com.nt.mywishlist_013.database.WishViewModel
import com.nt.mywishlist_013.ui.theme.MyWishList_013Theme
import kotlinx.coroutines.launch

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
        composable(route = Screen.AddScreen.route + ("/{id}"), arguments = listOf(navArgument("id") {
            type = NavType.LongType
            defaultValue = 0L
            nullable = false
        })) {entry ->
            val providedId = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            AddEditScreen(id = providedId, wishViewModel = wishViewModel, navController = navHostController)
        }
    }
}

@Composable
fun AddEditScreen(
    id: Long,
    wishViewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if(id != 0L) { // Editing an entry
        val wish = wishViewModel.getWishById(id).collectAsState(initial = Wish(0L,"", ""))
        wishViewModel.wishTitleState = wish.value.title
        wishViewModel.wishDescriptionState = wish.value.description
    } else { // Creating a new entry
        wishViewModel.wishTitleState = ""
        wishViewModel.wishDescriptionState = ""
    }

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
            if(id != 0L) { // update wish
                wishViewModel.updateWish(
                    Wish(
                        id = id,
                        title = wishViewModel.wishTitleState.trim(),
                        description = wishViewModel.wishDescriptionState.trim())
                )
                snackMessage.value = "Wish is being updated. Please be patient."
            } else { // add wish
                wishViewModel.addWish(
                    Wish(
                        title = wishViewModel.wishTitleState.trim(),
                        description = wishViewModel.wishDescriptionState.trim())
                )
                snackMessage.value = "New wish has been created! Please be patient as it saves."
            }
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                navController.navigateUp()
            }
        } else {
            // wish needs fields to be filled
            snackMessage.value = "You need to enter text in the fields."
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {TopBarComponent(
            title = pickScaffoldTitle(),
            onBackNavClicked = {
      navController.navigateUp()
    })}) {
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

// colorResource(id = R.color.dark_green)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(wishViewModel: WishViewModel, navController: NavController) {
    val context = LocalContext.current
    var wishList by remember { mutableStateOf<List<Wish>>(emptyList()) }

    LaunchedEffect(Unit) {
        val flow = wishViewModel.getAllWishes()
        flow.collect { wishes ->
            wishList = wishes
        }
    }

    fun clickPlusButton() {
        Toast.makeText(context, "Let's create a new wish!", Toast.LENGTH_LONG).show()
        navController.navigate(Screen.AddScreen.route + "/0L")
    }



    Scaffold(topBar = { TopBarComponent(title = "WishList", onBackNavClicked = {
        navController.navigateUp()
    })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { clickPlusButton() },
            modifier = Modifier.padding(all = 20.dp),
            backgroundColor = colorResource(id = R.color.lime_green)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = colorResource(id = R.color.black)
            )
        }
    }) { it ->
        // This is such a mess...
        // I had to compose_version = "1.6.0-beta02" to get the SwipeToDismiss to work properly.
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            items(wishList, key = {wish -> wish.id}) {
                    wish ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            wishViewModel.deleteWish(wish)
                        }
                        true
                    }
                )
                SwipeToDismiss( // This doesn't work.
                    state = dismissState,
                    background = {
                                 val color by animateColorAsState(
                                     if(dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent,
                                     label = ""
                                 )
                        val alignment = Alignment.CenterEnd
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp), contentAlignment = alignment) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Wish", tint = Color.White)
                        }
                    },
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.25f)},
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    },
                    )
            }
        }
    }
}

@Composable
fun TopBarComponent(title: String, onBackNavClicked: () -> Unit) {
    val navigationIcon: (@Composable () -> Unit)? = {
        if(!title.contains("WishList")) {
            IconButton(onClick = { onBackNavClicked() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
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