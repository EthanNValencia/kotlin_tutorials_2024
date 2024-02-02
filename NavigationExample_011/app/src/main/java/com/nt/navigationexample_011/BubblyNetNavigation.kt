package com.nt.navigationexample_011

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Example question that was given to me. Moved everything into one file to make it easier.
// TODO - come back and make it look better.
data class User(@DrawableRes val img: Int, val username: String, val email: String, val name: String)

val UserList = listOf(
    User(img = R.drawable.baseline_person_24, username = "Username1", email = "email1@yahoo.com", name = "User1"),
    User(img = R.drawable.baseline_person_24, username = "Username2", email = "email1@yahoo.com", name = "User 2"),
    User(img = R.drawable.baseline_person_24, username = "Username3", email = "email1@yahoo.com", name = "User 3"),
    User(img = R.drawable.baseline_person_24, username = "Username4", email = "email1@yahoo.com", name = "User 4"),
    User(img = R.drawable.baseline_person_24, username = "Username5", email = "email1@yahoo.com", name = "User 5"),
    User(img = R.drawable.baseline_person_24, username = "Username6", email = "email1@yahoo.com", name = "User 6"),
    User(img = R.drawable.baseline_person_24, username = "Username7", email = "email1@yahoo.com", name = "User 7"),
    User(img = R.drawable.baseline_person_24, username = "Username8", email = "email1@yahoo.com", name = "User 8"),
    User(img = R.drawable.baseline_person_24, username = "Username9", email = "email1@yahoo.com", name = "User 9"),
    User(img = R.drawable.baseline_person_24, username = "Username10", email = "email1@yahoo.com", name = "User 10"),
)

class UserViewModel: ViewModel() {
    private val _userList = mutableStateOf<List<User>>(UserList)
    var userList: State<List<User>> = _userList
    private val _selectedUser = mutableStateOf<User?>(null)
    var selectedUser: State<User?> = _selectedUser

    fun setSelectedUser(user: User) {
        _selectedUser.value = user
    }


}


@Composable
fun ScreenOne(title: String, viewModel: UserViewModel, navigateToUserInformationScreen: (User) -> Unit) {
    val usersList by viewModel.userList

    (Text(text = title))
    Column {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(usersList) {user ->
                UserView(viewModel = viewModel, user = user, navigateToScreenTwo = { navigateToUserInformationScreen(user) })
            }
        }
    }
}



@Composable
fun UserView(viewModel: UserViewModel, user: User, navigateToScreenTwo: (user: User) -> Unit) {

    fun setSelectedUser() {
        viewModel.setSelectedUser(user)
    }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(id = user.img),
                contentDescription = "Image",
                Modifier.padding(end = 8.dp, top = 4.dp)
            )
            Column {
                Text(text = "Username: ${ user.username }")
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Button(onClick = {
                setSelectedUser()
                navigateToScreenTwo(user)
            }) {
                Text(text = "Select")
            }
        }
    }
}

@Composable
fun ScreenTwo(title: String, viewModel: UserViewModel,
                 goBack: () -> Unit
) {
    val selectedUser by viewModel.selectedUser
    
    if(selectedUser != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = title)
            Row {
                Text(text = "Name: ${selectedUser!!.name}")
                Text(text = "Email: ${selectedUser!!.email}")
            }
            Row {
                Icon(
                    painter = painterResource(id = selectedUser!!.img),
                    contentDescription = "Image",
                    Modifier.padding(end = 8.dp, top = 4.dp)
                )
                Text(text = "Username: ${selectedUser!!.username}")
            }
            Button(onClick = { goBack() }) {
                Text(text = "Go back")
            }
        }
    } else {
        Text(text = title)
        Text(text = "Hmm. Something went wrong.")
        Button(onClick = { goBack() }) {
            Text(text = "Go back")
        }
    }

}

sealed class Screen(val title: String, val route: String) {
    object Users: Screen("Users", "user")
    object UserInformation: Screen("User Information", "userinformation")
}

@Composable
fun NavigationWithViewModel() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Users.route) {
        composable(route =  Screen.Users.route) {
            ScreenOne(
                title = Screen.Users.title,
                viewModel,
            ) { user ->
                navController.navigate(Screen.UserInformation.route) }
        }
        composable(route = Screen.UserInformation.route) {
            ScreenTwo(
                title = Screen.UserInformation.title,
                viewModel = viewModel,
            ) { navController.navigate(Screen.Users.route) }
        }

    }}