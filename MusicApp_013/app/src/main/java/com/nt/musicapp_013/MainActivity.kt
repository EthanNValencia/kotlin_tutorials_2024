package com.nt.musicapp_013

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
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
import androidx.compose.ui.unit.sp
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

@Deprecated(message = "Working version but changing to the inline version.")
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
            Icon(painter = painterResource(id = viewModel.currentScreen.value.icon), contentDescription = viewModel.currentScreen.value.title)
        }})
}


@Composable
fun DrawerItem(selected: Boolean, item: Screen, onDrawerItemClicked: () -> Unit) {
    val background = if (selected) Color.LightGray else Color.Transparent
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 16.dp)
        .background(background, shape = RoundedCornerShape(8.dp))
        .clickable { onDrawerItemClicked() }
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                Modifier.padding(end = 8.dp, top = 4.dp)
            )
            Text(text = item.title, style = MaterialTheme.typography.headlineLarge, fontSize = 24.sp)
        }
    }
}

@Composable
fun MainView() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    // Allow us to find the view we are currently in.
    val navController: NavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = remember{ viewModel.currentScreen.value }
    val dialogOpen = remember{ mutableStateOf(false) }
    val title = remember{ mutableStateOf(currentScreen.title) }

    // Okay, lets start making Kotlin look like js.
    val topBar: @Composable () -> Unit = {
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

    val navigateBottomBar: (Screen) -> Unit = { screen ->
        viewModel.setCurrentScreen(screen)
        navController.navigate(screen.route)
        // Navigation is happen in two different places in the app. (Check DrawerItem)
        // Which is fine because the changes are synchronized in the view model.
    }

    val bottomBar: @Composable () -> Unit = {
        if(currentScreen.type == ScreenType.DRAWER || currentScreen == Screen.Home) {
            BottomNavigation(modifier = Modifier.wrapContentSize(), backgroundColor = Color.LightGray) {
                bottomScreens.forEach { item ->
                    BottomNavigationItem(selected = currentRoute == item.route,
                        onClick = { navigateBottomBar(item) }, icon = {
                            Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                        },
                        label = { Text(text = item.title)},
                        selectedContentColor = Color.Transparent,
                        unselectedContentColor = Color.Black,
                    )
                }
            }
        }
    }

    Scaffold(
        bottomBar = bottomBar,
        topBar = topBar,
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(drawerScreens) { item ->
                    DrawerItem(selected = currentRoute == item.route, item = item) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        if(item.route == Screen.AddAccount.route) {
                            dialogOpen.value = true
                        } else {
                            navigateBottomBar(item)
                            title.value = item.title
                        }
                    }
                }
            }
        }
        ) {
        // Text(text = "Test Composable", modifier = Modifier.padding(it))
        Navigation(navController = navController, viewModel = viewModel, pd = it)
        AddAccountDialogue(dialogOpen = dialogOpen)
    }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {
    NavHost(navController = navController as NavHostController, startDestination = viewModel.currentScreen.value.route, modifier = Modifier.padding(pd)) {
        // With add account I want a pop up to open, that is why it is not here.
        composable(route = Screen.Account.route) {
            AccountView()
        }
        composable(route = Screen.Subscription.route) {
            SubscriptionView()
        }
       composable(route = Screen.Home.route) {
           HomeView(viewModel)
       }
        composable(route = Screen.Browse.route) {
            BrowseView(viewModel)
        // BrowseViewFirstAttempt(viewModel)
        }
        composable(route = Screen.Library.route) {
            LibraryView(viewModel = viewModel)
        }
    }

}
