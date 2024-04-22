package com.stu74525.lab3_74525

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(navController: NavController) {

    var cartProducts by remember { mutableStateOf(emptyList<ProductFirestore>()) }

    cartProducts = displayProductsFromCart()

    var amount by remember { mutableStateOf(0.0) }

    db.collection("cart")
        .document(actualUser.user_id.toString())
        .get()
        .addOnSuccessListener { document ->
            amount = document.getDouble("amount")!!
        }

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
                modifier = Modifier.background(pal_5_1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp, bottom = 80.dp, start = 15.dp, end = 15.dp)

                ) {
                    /*
                    if (cart.products.size == 0) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your cart is empty for now",
                                fontSize = 30.sp
                            )
                        }

                    }
                    */

                    if (cartProducts.isEmpty()) {
                        Log.w(MainActivity.TAG, "EMPTY\n\n")
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your cart is empty for now",
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
                                text = "Your cart",
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(bottom = 5.dp, top = 5.dp),
                                columns = GridCells.Adaptive(minSize = 150.dp),
                                horizontalArrangement = Arrangement.spacedBy(110.dp),
                                verticalArrangement = Arrangement.spacedBy(30.dp)
                            ) {
                                items(cartProducts.size) { index ->
                                    val product = cartProducts[index]
                                    Row(
                                        modifier = Modifier
                                            //.weight(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .fillMaxWidth()
                                            .background(pal_5_2)
                                            .padding(15.dp)
                                            .clickable {
                                                navController.navigate("${Routes.ProductScreen.route}/${product.name}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = product.image!!),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .size(150.dp)
                                                    .padding(5.dp)
                                            )
                                        }

                                        Column(
                                            verticalArrangement = Arrangement.SpaceAround,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.End,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.delete),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(30.dp, 30.dp)
                                                        .clickable {
                                                            db.collection("cart")
                                                                .document(actualUser.user_id.toString())
                                                                .collection("product")
                                                                .whereEqualTo("name", product.name)
                                                                .get()
                                                                .addOnSuccessListener { querySnapshot ->
                                                                    if (!querySnapshot.isEmpty) {
                                                                        val document =
                                                                            querySnapshot.documents[0]
                                                                        document.reference
                                                                            .delete()
                                                                            .addOnSuccessListener {
                                                                                Log.d(
                                                                                    MainActivity.TAG,
                                                                                    "Document successfully deleted!\n\n\n"
                                                                                )
                                                                            }
                                                                            .addOnFailureListener { e ->
                                                                                Log.e(
                                                                                    MainActivity.TAG,
                                                                                    "Error deleting document",
                                                                                    e
                                                                                )
                                                                            }
                                                                    } else {
                                                                        Log.w(
                                                                            MainActivity.TAG,
                                                                            "No document found with the specified name.\n\n\n"
                                                                        )
                                                                    }

                                                                }
                                                            db.collection("cart")
                                                                .document(actualUser.user_id.toString())
                                                                .update(
                                                                    hashMapOf(
                                                                        "amount" to (amount - cartProducts[index].price!!)
                                                                    ) as Map<String, Any>
                                                                )
                                                            //navController.navigate(Routes.CartScreen.route)
                                                        }
                                                )
                                            }

                                            Text(
                                                text = product.name.toString(),
                                                fontSize = 20.sp
                                            )
                                            Text(
                                                text = "Price : " + product.price,
                                                fontSize = 20.sp
                                            )

                                        }
                                    }
                                }
                            }
                        }


                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total amount : " + amount.toFloat() + "€",
                                fontSize = 20.sp
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(pal_4_3)
                                        .padding(15.dp)

                                ) {

                                    Text(
                                        text = "Proceed to payment",
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            
                                            .clickable {
                                                val currentDateTime = LocalDateTime.now()

                                                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")


                                                //cart.date = currentDateTime.format(dateFormatter)
                                                //cart.time = currentDateTime.format(timeFormatter)



                                                var tempAmount = 0.0
                                                var tempProductSize = 0
                                                actualUser.user_id?.let { userId ->

                                                    val docCart = db.collection("cart").document(userId)

                                                    docCart.get()
                                                        .addOnSuccessListener { documentSnap ->
                                                            tempAmount = documentSnap.getDouble("amount")!!
                                                        }

                                                    docCart.collection("product").get()
                                                        .addOnSuccessListener {document ->
                                                            tempProductSize = document.size()
                                                        }
                                                        .addOnFailureListener {e ->
                                                            Log.e(MainActivity.TAG, "Erreur lors de la récupération des documents de la collection", e)
                                                        }




                                                    val docRef = db.collection("orders-history")
                                                        .document(userId)
                                                        .collection("order")
                                                        .document()
                                                    docRef
                                                        .set(
                                                            hashMapOf(
                                                                "amount" to amount,
                                                                "date" to currentDateTime.format(dateFormatter),
                                                                "time" to currentDateTime.format(timeFormatter)
                                                            )
                                                        )
                                                        .addOnSuccessListener {

                                                            Log.d(MainActivity.TAG, "Successfully added empty history\n\n")

                                                            for (product in cartProducts) {
                                                                docRef
                                                                    .collection("product").document()
                                                                    .set(
                                                                        hashMapOf(
                                                                            "name" to product.name,
                                                                            "description" to product.description,
                                                                            "price" to product.price,
                                                                            "image" to product.image
                                                                        )
                                                                    )
                                                                    .addOnSuccessListener {
                                                                        Log.d(MainActivity.TAG, "Successfully added product to order\n\n")
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Log.e(MainActivity.TAG, "Error adding product to order", e)
                                                                    }
                                                            }

                                                            //cart.products = mutableListOf()
                                                            navController.navigate(Routes.CartScreen.route)

                                                        }
                                                        .addOnFailureListener { e ->
                                                            Log.e(MainActivity.TAG,"erreur lors de la création du document\n\n", e)
                                                        }
                                                    db.collection("cart").document(userId).update("amount", 0.0)
                                                        .addOnSuccessListener {
                                                            Log.d(MainActivity.TAG, "Amount successfully updated to 0!\n\n\n")
                                                        }
                                                        .addOnFailureListener { e ->
                                                            Log.e(MainActivity.TAG, "Error updating amount to 0", e)
                                                        }

                                                    db.collection("cart")
                                                        .document(userId)
                                                        .collection("product")
                                                        .get()
                                                        .addOnSuccessListener {querySnapshot ->
                                                            for (document in querySnapshot) {
                                                                document.reference.delete()
                                                                    .addOnSuccessListener {
                                                                        Log.d(MainActivity.TAG, "Document successfully deleted from product collection!\n\n\n")
                                                                    }
                                                                    .addOnFailureListener { e ->
                                                                        Log.e(MainActivity.TAG, "Error deleting document from product collection", e)
                                                                    }
                                                            }
                                                        }
                                                        .addOnFailureListener {e ->
                                                            Log.e(MainActivity.TAG, "Error getting documents from product collection", e)
                                                        }
                                                }
                                                navController.navigate(Routes.CartScreen.route)

                                            }
                                    )
                                    Spacer(modifier = Modifier.padding(10.dp))
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