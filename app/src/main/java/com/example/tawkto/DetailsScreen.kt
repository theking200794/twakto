package com.example.tawkto

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun DetailsScreen(
    user: String, navigator: NavHostController,
) {
    Log.d("zavi", user)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = rememberAsyncImagePainter(R.drawable.back_arrow),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(.08f)
                    .clickable {
                        Log.d("zavi", "Clicked")
                        navigator.navigate("main")
                    }
                    .fillMaxHeight(.05f),
                contentScale = ContentScale.Fit,
            )

            Text(
                color = Color.Black,
                text = "Name",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth()
            )
        }



        Image(
            painter = rememberAsyncImagePainter("https://avatars.githubusercontent.com/u/1?v=4"),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.45f),
            contentScale = ContentScale.Crop,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .border(
                    border = BorderStroke(1.dp,Color.Gray),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(bottom = 5.dp, top = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround

        ) {
            Text(
                color = Color.Black,
                text = "followers: ",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                color = Color.Black,
                text = "following: ",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            )
        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .border(
                    border = BorderStroke(1.dp,Color.Gray),
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(bottom = 5.dp, top = 5.dp)

        ) {
            Text(
                color = Color.Black,
                text = "name: ",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            Text(
                color = Color.Black,
                text = "company: ",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            Text(
                color = Color.Black,
                text = "blog: ",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        }

        Text(
            text = "Notes: ",
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 5.dp, bottom = 5.dp),
            textAlign = TextAlign.Left,
            fontSize = 18.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White, RoundedCornerShape(5.dp)),
            shape = RoundedCornerShape(5.dp),
            value = "Enter your notes here...",
            onValueChange = { },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 3,
            textStyle = MaterialTheme.typography.caption
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                //your onclick code here
            }) {
                Text(text = "Save")
            }
        }


    }
}
