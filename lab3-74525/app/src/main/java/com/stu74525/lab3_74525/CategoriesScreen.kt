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
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
fun ProductCategoriesScreen(navController: NavController) {

    db.collection("orders-history").document(auth.currentUser!!.uid).set(
        hashMapOf(
            "unusued" to "unusued"
        )
    )
        .addOnSuccessListener {
            Log.d(MainActivity.TAG, "OUIIIIIIIIIIIIIIIIIIIIIIII\n\n")
        }
        .addOnFailureListener { Log.d(MainActivity.TAG, "NONNNNNNNNNN\n\n") }

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
                colors = topAppBarColors(
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
                        .padding(top = 56.dp, bottom = 90.dp, start = 15.dp, end = 15.dp)

                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Welcome!",
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 25.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 5.dp),
                        columns = GridCells.Adaptive(minSize = 90.dp),
                        horizontalArrangement = Arrangement.spacedBy(35.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(categories.size) { index ->

                            val category = categories[index]
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(pal_5_2)
                                        .padding(15.dp)
                                        .clickable {
                                            navController.navigate("${Routes.ProductsListScreen.route}/${category.categoryName}")
                                        },
                                ) {
                                    Image(
                                        painterResource(id = category.logo),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(70.dp)
                                    )
                                }
                                Text(
                                    text = category.categoryName,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(top = 10.dp, bottom = 20.dp),
                                    color = Color.DarkGray
                                )
db
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