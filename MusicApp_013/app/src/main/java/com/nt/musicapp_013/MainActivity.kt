package com.nt.musicapp_013

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nt.musicapp_013.Screens.DrawerScreen.Account.screensInDrawer
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

sealed class Screens(val title: String, val route: String) {
    sealed class DrawerScreen(val drawerTitle: String, val drawerRoute: String, @DrawableRes val icon: Int): Screens(drawerTitle, drawerRoute) {
        object Account: DrawerScreen("Account", "account", R.drawable.ic_account)
        object Subscription: DrawerScreen("Subscription", "subscribe", R.drawable.ic_subscribe)
        object AddAccount: DrawerScreen("Add Account", "add", R.drawable.baseline_person_add_alt_1_24)
    }

    val screensInDrawer = listOf(DrawerScreen.Account, DrawerScreen.Subscription, DrawerScreen.AddAccount)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = "Home") }, 
        navigationIcon = { IconButton(onClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
        }})
}

@Composable
fun DrawerItem(selected: Boolean, item: Screens.DrawerScreen, onDrawerItemClicked: () -> Unit) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 16.dp)
        .background(background)
        .clickable { onDrawerItemClicked() }
    ) {
        Icon(
            painter = painterResource(id = item.icon), 
            contentDescription = item.drawerTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(text = item.drawerTitle, style = MaterialTheme.typography.headlineLarge)
    }
}

@Composable
fun MainView() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    // Allow us to find the view we are currently in.
    val navController: NavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = remember{ mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState)},
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(screensInDrawer) {item ->
                    DrawerItem(selected = currentRoute == item.drawerRoute, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if(item.drawerRoute == "add_account") {

                        } else {
                            navController.navigate(item.drawerRoute)
                            title.value = item.drawerTitle
                        }
                    }
                }
            }
        }
        ) {
        Text(text = "Test Composable", modifier = Modifier.padding(it))
    }
}