package com.example.tawkto.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.tawkto.R
import com.example.tawkto.model.UsersItem
import com.example.tawkto.viewmodel.UsersViewModel


@Composable
fun UserList(
    viewModel: UsersViewModel,
    lifecycleOwner: LifecycleOwner,
) {
    val scrollState = rememberLazyListState()
    val userList = viewModel.usersPager.collectAsLazyPagingItems()
    var filteredCountries: LiveData<List<UsersItem>>


    Column {
        SearchableListItem(viewModel.query)
        Divider(color = Color.Gray, thickness = 1.dp)
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = 8.dp,
            ) {
                LazyColumn(state = scrollState) {
                    val searchedText = viewModel.query.value
                    if (searchedText.isEmpty()) {
                        items(userList.itemCount) { index ->
                            userList[index]?.let {
                                UserCard(user = it, index)
//                            DefaultPreview(user = it)
                            }
                        }

                    } else {
                        // loading data on query from local db
                        viewModel.searchLocalDatabase(viewModel.query.value)
                            .observe(lifecycleOwner, { users ->
                                users?.let {
                                    items(it.size) { index ->
                                        users[index]?.let {
                                            UserCard(
                                                user = it,
                                                index,
                                            )
                                        }
                                    }
                                }

//                                Log.d("zavi", "onCfreate: ${users.toString()}")
//                                users.iterator().forEach { user ->
//
//                                    Log.d("zavi", "id: ${user.id}\n image: ${user.url}")
//                                }
                            })
                    }
                    // for loading item and error handling
                    when (userList.loadState.append) {
                        is LoadState.NotLoading -> Unit
                        LoadState.Loading -> {
                            item {
                                LoadingItem()
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                ErrorItem(message = userList.loadState.append.toString())
                            }
                        }
                    }

                    when (userList.loadState.refresh) {
                        is LoadState.NotLoading -> Unit
                        LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                ErrorItem(message = userList.loadState.refresh.toString())
                            }
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun SearchableListItem(state: MutableState<String>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp),
                value = state.value,
                onValueChange = {
                    state.value = it.lowercase()
                    Log.d("zavi", state.value)
                },
                label = { Text("Search") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),

                leadingIcon = {
                    Icon(Icons.Filled.Search, "")
                },

                )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun UserCard(user: UsersItem, index: Int) {
    val rainbowColorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFF9575CD),
                Color(0xFFBA68C8),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFF9575CD)
            )
        )
    }

    val colorMatrix = floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    Card(

        elevation = 4.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar_url)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp)
                    .border(
                        BorderStroke(1.dp, rainbowColorsBrush),
                        CircleShape
                    )
                    .onFocusChanged { focus ->
                        focus.isFocused.let { keyboardController?.hide() }
                    },
                colorFilter = if ((index + 1) % 4 == 0) ColorFilter.colorMatrix(
                    ColorMatrix(
                        colorMatrix
                    )
                ) else null
            )

            Column(
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Text(
                    text = user.login,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 4.dp)

                )
                Text(
                    text = "details",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(start = 12.dp)
                )

            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(), contentAlignment = Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(42.dp)
                .height(42.dp)
                .padding(8.dp), strokeWidth = 5.dp
        )
    }
}

@Composable
fun ErrorItem(message: String) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            horizontalArrangement = Arrangement.Center

        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp)
                    .padding(top = 12.dp),
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(
                color = Color.White,
                text = message,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
            )
        }
    }
}

class PreviewProvider() : PreviewParameterProvider<UsersItem> {
    override val values = sequenceOf(
        UsersItem(
            0,
            "https://avatars.githubusercontent.com/u/1?v=4",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            true,
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd"
        ),
        UsersItem(
            0,
            "https://avatars.githubusercontent.com/u/1?v=4",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            true,
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
            "adfasdfsd",
        )
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview(@PreviewParameter(PreviewProvider::class) user: UsersItem) {
//    UserCard(user, 0, navController = )
//}

//@Preview
//@Composable
//fun DefaultPreview() {
//    Text(text = "Hello!")
//}