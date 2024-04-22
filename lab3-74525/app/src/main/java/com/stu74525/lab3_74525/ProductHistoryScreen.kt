package com.stu74525.lab3_74525

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductHistoryScreen(navController: NavController, product: Product) {

    var numberInputValue by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.popBackStack()
                                }
                                .padding(5.dp)
                        )
                        Text(
                            text = product.categoryName,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    navController.navigate(Routes.ProfileScreen.route)
                                }
                                .padding(10.dp)
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = pal_4_2,
                    titleContentColor = Color.LightGray
                ),
            )
        },

        content = {
            Column(
                modifier = Modifier.background(pal_5_1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 70.dp, bottom = 90.dp, start = 15.dp, end = 15.dp)

                ) {
                    Text(
                        text = product.name,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = product.image),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .padding(bottom = 0.dp)
                                .size(350.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.price.toString() + "€",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                    }
                    Text(
                        text = product.description + "\n",
                        fontSize = 20.sp
                    )
                }
            }
        },

        bottomBar = {
            BottomAppBar(
                containerColor = pal_4_2,
                contentColor = Color.Blue,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.category),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    navController.navigate(Routes.CategoriesScreen.route)
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.grocerystore),
                            contentDescription = null,
                            modifier = Modifier
                                .size(55.dp)
                                .clickable {
                                    navController.navigate(Routes.CartScreen.route)
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.history),
                            contentDescription = null,
                            modifier = Modifier
                                .size(55.dp)
                                .clickable {
                                    navController.navigate(Routes.OrdersHistoryScreen.route)
                                }
                        )
                    }
                }
            )
        },
    )
}