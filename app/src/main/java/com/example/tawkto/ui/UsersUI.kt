package com.example.tawkto.ui

import com.example.tawkto.model.UsersItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import coil.request.ImageRequest
import com.example.tawkto.R
import com.example.tawkto.viewmodel.UsersViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import coil.compose.SubcomposeAsyncImage


@Composable
fun UserList(viewModel: UsersViewModel) {
    val scrollState = rememberLazyListState()
    val userList = viewModel.usersPager.collectAsLazyPagingItems()

    LazyColumn(state = scrollState, ) {
        items(userList.itemCount) { index ->
            userList[index]?.let { UserCard(user = it)
            DefaultPreview(user = it)
            }
        }

        when(userList.loadState.append){
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    LoadingItem()
                }
            }
            is LoadState.Error -> {
                item {
                    ErrorItem(message  = userList.loadState.append.toString())
                }
            }
        }

        when(userList.loadState.refresh){
            is LoadState.NotLoading -> Unit
            LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Center
                    ){
                        CircularProgressIndicator()
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    ErrorItem(message  = userList.loadState.refresh.toString())
                }
            }
        }
    }
}

@Composable
fun UserCard(user: UsersItem) {
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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(42.dp)
                    .height(42.dp)
            )

            Column(modifier = Modifier
                .padding(6.dp)) {
                Text(
                    text = user.login,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 4.dp,)

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
                contentDescription = "Mango",
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
    override val values = sequenceOf(UsersItem(0, "https://avatars.githubusercontent.com/u/1?v=4", "adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd",true,"adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd"), UsersItem(0, "https://avatars.githubusercontent.com/u/1?v=4", "adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd",true,"adfasdfsd","adfasdfsd","adfasdfsd","adfasdfsd",  ))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(@PreviewParameter(PreviewProvider::class)user: UsersItem) {
    UserCard(user)
}

//@Preview
//@Composable
//fun DefaultPreview() {
//    Text(text = "Hello!")
//}