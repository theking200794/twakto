package com.example.tawkto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tawkto.ui.UserList
import com.example.tawkto.ui.theme.PagingSampleTheme
import com.example.tawkto.viewmodel.UsersViewModel
import com.example.tawkto.viewmodel.UsersViewModelFactory
import com.google.gson.Gson


class MainActivity() : ComponentActivity() {
    lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PagingSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){

                    // here using global reference for accessing users Repository
                    val usersRepository = (application as UserApplication).usersRepository
                    val usersViewModelFactory = UsersViewModelFactory(usersRepository)

                    usersViewModel = ViewModelProvider(this, usersViewModelFactory)[UsersViewModel::class.java]

//                    UserList(viewModel = usersViewModel, this)
                    Navigation(viewModel = usersViewModel, lifecycleOwner = this)
                }
            }
        }
    }

    @Composable
    fun Navigation(viewModel: UsersViewModel, lifecycleOwner: LifecycleOwner) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                UserList(viewModel, lifecycleOwner, navController)
            }
            composable(
                "details/{user}",
                arguments = listOf(navArgument("user") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("user")?.let { user ->
                    DetailsScreen(user = user, navController)
                }
            }
        }
    }

}