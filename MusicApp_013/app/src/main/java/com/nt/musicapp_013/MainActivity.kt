package com.nt.musicapp_013

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nt.musicapp_013.ui.theme.MusicApp_013Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicApp_013Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

sealed class Screen(val title: String, val route: String, @DrawableRes val icon: Int) {
    data object Account: Screen(title = "Account", route = "account", icon = R.drawable.ic_account)
    data object Subscription: Screen(title = "Subscription", route = "subscribe", icon =  R.drawable.ic_subscribe)
    data object AddAccount: Screen(title = "Add Account", route = "add", icon = R.drawable.baseline_person_add_alt_1_24)


}

@Composable
fun TopBar(viewModel: MainViewModel, scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = viewModel.currentScreen.value.title) },
        backgroundColor = Color.LightGray,
        navigationIcon = { IconButton(onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(painter = painterResource(id = viewModel.currentScreen.value.icon), contentDescription = "Account")
        }})
}

@Composable
fun DrawerItem(selected: Boolean, item: Screen, onDrawerItemClicked: () -> Unit) {
    val background = if (selected) Color.LightGray else Color.Transparent
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 16.dp)
        .background(background)
        .clickable { onDrawerItemClicked() }
    ) {
        Icon(
            painter = painterResource(id = item.icon), 
            contentDescription = item.title,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(text = item.title, style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun MainView() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    val screensInDrawer = listOf(Screen.Account, Screen.Subscription, Screen.AddAccount)
    // Allow us to find the view we are currently in.
    val navController: NavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = remember{ viewModel.currentScreen.value }
    val dialogOpen = remember{ mutableStateOf(false) }
    val title = remember{ mutableStateOf(currentScreen.title) }

    Scaffold(
        topBar = { TopBar(viewModel = viewModel, scope = scope, scaffoldState = scaffoldState)},
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screensInDrawer) { item ->
                    DrawerItem(selected = currentRoute == item.route, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if(item.route == Screen.AddAccount.route) {
                            dialogOpen.value = true
                        } else {
                            viewModel.setCurrentScreen(item)
                            navController.navigate(item.route)
                            title.value = item.title
                        }
                    }
                }
            }
        }
        ) {
        // Text(text = "Test Composable", modifier = Modifier.padding(it))
        Navigation(navController = navController, viewModel = viewModel, pd = it)
        AccountDialogue(dialogOpen = dialogOpen)
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {
    NavHost(navController = navController as NavHostController, startDestination = viewModel.currentScreen.value.route, modifier = Modifier.padding(pd)) {
        composable(route = Screen.Account.route) {
            AccountView()
        }
        composable(route = Screen.Subscription.route) {
            SubscriptionView()
        }
        // With add account I want a pop up to open, that is why it is not here.
    }

}