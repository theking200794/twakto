package com.example.tawkto

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tawkto.model.UsersItem
import com.example.tawkto.ui.UserCard
import com.example.tawkto.ui.UserList
import com.example.tawkto.ui.theme.PagingSampleTheme
import com.example.tawkto.viewmodel.UsersViewModel
import com.example.tawkto.viewmodel.UsersViewModelFactory


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

                    usersViewModel = ViewModelProvider(this, usersViewModelFactory).get(UsersViewModel::class.java)

                    UserList(viewModel = usersViewModel, this, )

                }
            }
        }

    }

}