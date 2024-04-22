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
fun ProductListHistoryScreen(navController: NavController, id: Int) {
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
                                    navController.navigate(Routes.OrdersHistoryScreen.route)
                                }
                                .padding(5.dp)

                        )
                        var temp = "empty"
                        if (globalOrderHist?.isEmpty() == false) {
                            temp = (globalOrderHist?.get(id)?.products?.size).toString()
                            if (temp == "1"){
                                temp += " Product"
                            }
                            else {
                                temp += " Products"
                            }
                        }

                        Text(
                            text = temp,
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
                modifier = Modifier
                    .background(pal_5_1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp, bottom = 80.dp, start = 15.dp, end = 15.dp)
                ) {
                    var productList: List<ProductFirestore>? = null

                    if (globalOrderHist?.isEmpty() == false) {
                        productList = globalOrderHist?.get(id)?.products
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(bottom = 5.dp),
                            columns = GridCells.Adaptive(minSize = 110.dp),
                            horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                            productList?.let { it1 ->
                                items(it1.size) { index ->

                                    val product = productList.get(index)
                                    Column(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(pal_5_2)
                                            .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 20.dp)
                                            .size(200.dp, 300.dp),
                                        verticalArrangement = Arrangement.SpaceAround,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                ,
                                            verticalAlignment = Alignment.Top,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            val ID = product.image?.let { it1 -> painterResource(id = it1) }

                                            ID?.let { it1 ->
                                                Image(
                                                    it1,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(130.dp)
                                                        .clip(RoundedCornerShape(20.dp))
                                                        .clickable {
                                                            navController.navigate("${Routes.ProductHistoryScreen.route}/${product.name}")
                                                        }
                                                        /*
                                                        .clickable {
                                                            navController.navigate("${Routes.ProductScreen.route}/${product.name}")
                                                        }

                                                         */
                                                )
                                            }
                                        }

                                        Row(
                                            modifier = Modifier
                                                .size(160.dp, 150.dp),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            product.name?.let { it1 ->
                                                Text(
                                                    text = it1,
                                                    modifier = Modifier
                                                        .padding(10.dp),
                                                    fontSize = 20.sp
                                                )
                                            }
                                        }

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