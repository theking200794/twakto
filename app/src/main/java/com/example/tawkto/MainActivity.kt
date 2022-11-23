package com.example.tawkto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.tawkto.data.api.ApiInterface
import com.example.tawkto.data.api.ApiUtilities
import com.example.tawkto.data.repository.UsersRepository
import com.example.tawkto.ui.UserList
import com.example.tawkto.ui.theme.PagingSampleTheme
import com.example.tawkto.viewmodel.UsersViewModel
import com.example.tawkto.viewmodel.UsersViewModelFactory


public class MainActivity : ComponentActivity() {
    lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)


        setContent {
            PagingSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)

                    val usersRepository = UsersRepository(apiInterface)

                    usersViewModel = ViewModelProvider(this, UsersViewModelFactory(usersRepository)).get(UsersViewModel::class.java)
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