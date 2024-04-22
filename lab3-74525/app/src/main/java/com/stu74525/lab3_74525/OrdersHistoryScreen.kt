package com.stu74525.lab3_74525

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(navController: NavController) {

    var ordersHist by remember { mutableStateOf(emptyList<CartFirestore>()) }

    ordersHist = newDisplayOrderHistory()

    globalOrderHist = ordersHist


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(60.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .padding(5.dp)
                        )
                        Text(
                            text = "Shop App",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    navController.navigate(Routes.ProfileScreen.route)
                                }
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
                modifier = Modifier
                    .background(pal_5_1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp, bottom = 80.dp, start = 15.dp, end = 15.dp)
                ) {
                    if (ordersHist.isEmpty()) {

                        Log.w(MainActivity.TAG, "TEST\n\n")
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your order history is empty for now",
                                fontSize = 30.sp
                            )
                        }
                    }
                    else {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your orders history",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(bottom = 5.dp, top = 5.dp),
                            columns = GridCells.Adaptive(minSize = 150.dp),
                            horizontalArrangement = Arrangement.spacedBy(100.dp),
                            verticalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            items(ordersHist.size) { index ->
                                val oldCartFirestore = ordersHist[index]
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .fillMaxWidth()
                                        .background(pal_5_2)
                                        .padding(15.dp)
                                        .clickable {
                                            navController.navigate("${Routes.ProductsListHistoryScreen.route}/$index")
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(
                                        //Modifier.size(130.dp, 70.dp)
                                    ) {
                                        oldCartFirestore.date?.let { it1 ->
                                            Text(
                                                text = it1,
                                                fontSize = 23.sp
                                            )
                                        }
                                        oldCartFirestore.time?.let { it1 ->
                                            Text(
                                                text = it1,
                                                fontSize = 23.sp
                                            )
                                        }
                                    }
                                    Column(

                                    ) {
                                        Text(
                                            text = "Amount : " + oldCartFirestore.amount + "€",
                                            fontSize = 20.sp
                                        )
                                        Text(
                                            text = "Products : " + oldCartFirestore.products.size,
                                            fontSize = 20.sp
                                        )
                                    }

                                }
                            }
                        }
                    }


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

