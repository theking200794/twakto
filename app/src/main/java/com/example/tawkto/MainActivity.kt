package com.example.tawkto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.tawkto.ui.UserList
import com.example.tawkto.ui.theme.PagingSampleTheme
import com.example.tawkto.viewmodel.UsersViewModel
import com.example.tawkto.viewmodel.UsersViewModelFactory


class MainActivity : ComponentActivity() {
    lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PagingSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    // here using global reference for accessing usersRepsotory
                    val usersRepository = (application as UserApplication).usersRepository
                    val usersViewModelFactory = UsersViewModelFactory(usersRepository)

                    usersViewModel = ViewModelProvider(this, usersViewModelFactory).get(UsersViewModel::class.java)

                    UserList(viewModel = usersViewModel)
                }
            }
        }


//        usersViewModel.users.observe(this, {users ->
//            Log.d("zavi", "onCfreate: ${it.toString()}")
//            users.iterator().forEach{user ->
//
//                Log.d("zavi", "id: ${user.id}\n image: ${user.url}")
//            }
//        })

    }

}