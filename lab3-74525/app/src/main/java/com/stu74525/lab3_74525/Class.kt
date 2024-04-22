package com.stu74525.lab3_74525

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class User(
    var name: String?,
    var surname: String?,
    var email: String?,
    var phone: String?,
    var password: String?,
    var user_id: String?
)

class Category(
    var categoryName: String,
    var logo: Int,
    var products: List<Product> = mutableListOf()
)

class Product(
    var name: String,
    var description: String,
    var image: Int,
    var price: Double,
    var categoryName: String
)

class ProductFirestore(
    var name: String?,
    var description: String?,
    var image: Int?,
    var price: Double?
)

class Cart(
    var date: String,
    var time: String,
    var amount: Double,
    var products: MutableList<Product> = mutableListOf()
)

var cart = Cart(
    date = "empty",
    time = "empty",
    amount = 0.0,
    products = mutableListOf()
)

class CartFirestore(
    var date: String?,
    var time: String?,
    var amount: Double?,
    var products: List<ProductFirestore> = mutableListOf()
)

var globalOrderHist: List<CartFirestore>? = null

val categories = listOf(
    Category(
        categoryName = "Trousers",
        logo = R.drawable.pantsbl,
        products = listOf(
            Product("High waist leggings", "Description du produit 1", R.drawable.pants_1, 14.90, "Trousers"),
            Product("High waist checked leggings", "Description du produit 2", R.drawable.pants_2, 18.90, "Trousers"),
            Product("Large cargo trousers", "Description du produit 3", R.drawable.pants_3, 9.90, "Trousers"),
            Product("Lounge jean", "Description du produit 4",R.drawable.pants_4, 24.90,"Trousers"),
            Product("Tapered linen and cotton trousers", "Description du produit 5", R.drawable.pants_5, 22.90,"Trousers"),
            Product("Cotton and linen trousers", "Description du produit 6", R.drawable.pants_6, 18.90,"Trousers"),
            Product("Cotton lounge trousers", "Description du produit 7", R.drawable.pants_7, 23.90,"Trousers"),
        )
    ),
    Category(
        categoryName = "Dress",
        logo = R.drawable.dressbl,
        products = listOf(
            Product("Sleeveless wrap-around dress", "Description du produit 1", R.drawable.dress_1, 34.90,"Dress"),
            Product("Gathered open back dress", "Description du produit 2", R.drawable.dress_2, 25.50,"Dress"),
            Product("Sleeveless 3D rib knit dress", "Description du produit 3", R.drawable.dress_3, 25.90,"Dress"),
            Product("Short-sleeved linen blend dress", "Description du produit 4",R.drawable.dress_4, 19.90,"Dress"),
            Product("Voluminous sleeves dress", "Description du produit 5", R.drawable.dress_5, 35.90,"Dress"),
            Product("Sleeveless dress with integrated bra", "Description du produit 6", R.drawable.dress_6, 23.50,"Dress"),
        )
    ),
    Category(
        categoryName = "Headphones",
        logo = R.drawable.headphonesbl,
        products = listOf(
            Product("AirPods Max", "Description du produit 1", R.drawable.headphone_1, 579.0,"Headphones"),
            Product("AirPods Pro (2nd gen.)", "Description du produit 2", R.drawable.headphone_2, 279.0,"Headphones"),
            Product("Sony LinkBuds", "Description du produit 3", R.drawable.headphone_3, 180.0,"Headphones"),
            Product("Sony Noise Cancelling Headphones", "Description du produit 4",R.drawable.headphone_4, 140.0,"Headphones"),
            Product("Bose Earbuds Ultra", "Description du produit 5", R.drawable.headphone_5, 349.95,"Headphones"),
            Product("Bose QuietComfort", "Description du produit 6", R.drawable.headphone_6, 299.95,"Headphones"),
            Product("Bose QuietComfort Ultra", "Description du produit 7", R.drawable.headphone_7, 499.95,"Headphones"),
        )
    ),
    Category(
        categoryName = "Smartphones",
        logo = R.drawable.mobilephonebl,
        products = listOf(
            Product("Iphone 15 Pro", "Description du produit 1", R.drawable.smartphone_1, 959.0,"Smartphones"),
            Product("Iphone 14 Pro Max", "Description du produit 2", R.drawable.smartphone_2, 879.0,"Smartphones"),
            Product("Samsung Galaxy S23", "Description du produit 3", R.drawable.smartphone_3, 719.0,"Smartphones"),
                Product("Samsung Galaxy A12", "Description du produit 4",R.drawable.smartphone_4, 112.99,"Smartphones"),
            Product("Samsung Galaxy A32", "Description du produit 5", R.drawable.smartphone_5, 149.99,"Smartphones"),
            Product("Google Pixel 5", "Description du produit 6", R.drawable.smartphone_6, 275.99,"Smartphones"),
            Product("Nokia 5", "Description du produit 7", R.drawable.smartphone_7, 116.0,"Smartphones"),
        )
    ),
    Category(
        categoryName = "Bedding",
        logo = R.drawable.pillowbl,
        products = listOf(
            Product("Duck feather pillow", "Description du produit 1", R.drawable.pillow_1, 19.90,"Bedding"),
            Product("Duck feather pillow with patterns", "Description du produit 2", R.drawable.pillow_2, 24.90,"Bedding"),
            Product("Fibre pillow", "Description du produit 3", R.drawable.pillow_3, 14.90,"Bedding"),
            Product("Soft fibre pillow", "Description du produit 4",R.drawable.pillow_4, 18.90,"Bedding"),
            Product("Fibre pillow", "Description du produit 5", R.drawable.pillow_5, 14.90,"Bedding"),
            Product("Fibre corner pillow", "Description du produit 6", R.drawable.pillow_6, 17.90,"Bedding"),
        )
    ),
    Category(
        categoryName = "Kitchen",
        logo = R.drawable.spatulabl,
        products = listOf(
            Product("4-piece kitchen steel utensil set", "Description du produit 1", R.drawable.kitchen_1, 24.90,"Kitchen"),
            Product("5-piece kitchen utensil set", "Description du produit 2", R.drawable.kitchen_2, 19.90,"Kitchen"),
            Product("Grater, stainless steel", "Description du produit 3", R.drawable.kitchen_3, 12.90,"Kitchen"),
            Product("Strainer, stainless steel, 20 cm", "Description du produit 4",R.drawable.kitchen_4, 13.90,"Kitchen"),
            Product("Serving bowl, stainless steel, 28 cm", "Description du produit 5", R.drawable.kitchen_5, 9.90,"Kitchen"),
            Product("Serving bowl, clear glass, 20 cm", "Description du produit 6", R.drawable.kitchen_6, 14.90,"Kitchen"),
            Product("Corkscrew, silver-colour/matt", "Description du produit 7", R.drawable.kitchen_7, 6.90,"Kitchen"),
        )
    ),
    Category(
        categoryName = "T-shirt",
        logo = R.drawable.tshirtbl,
        products = listOf   (
            Product("Cotton, short-sleeved T-Shirt", "Description du produit 1", R.drawable.tshirt_1, 13.90,"T-shirt"),
            Product("Cotton, short-sleeved T-Shirt", "Description du produit 2", R.drawable.tshirt_2, 13.90,"T-shirt"),
            Product(" Quick-dry short sleeves T-Shirt", "Description du produit 3", R.drawable.tshirt_3, 15.90,"T-shirt"),
            Product("3/4 sleeve T-Shirt", "Description du produit 4",R.drawable.tshirt_4, 12.90,"T-shirt"),
            Product("Round-neck T-Shirt", "Description du produit 5", R.drawable.tshirt_5, 13.90,"T-shirt"),
            Product("Cotton T-Shirt 3/4 sleeves", "Description du produit 6", R.drawable.tshirt_6, 18.90,"T-shirt"),
            Product("Short-sleeved T-Shirt", "Description du produit 7", R.drawable.tshirt_7, 15.90,"T-shirt"),
        )
    ),
    Category(
        categoryName = "Supplements",
        logo = R.drawable.wheybl,
        products = listOf(
            Product("Spirulina 60 Tablets 500mg", "Description du produit 1", R.drawable.whey_1, 25.00,"Supplements"),
            Product("Organic Turmeric 60 Capsules", "Description du produit 2", R.drawable.whey_2, 25.00,"Supplements"),
            Product("Organic Chlorella Powder 200g", "Description du produit 3", R.drawable.whey_3, 34.90,"Supplements"),
            Product("Informed Mass Gainer", "Description du produit 4",R.drawable.whey_4, 39.90,"Supplements"),
            Product("Zero Calorie Syrup", "Description du produit 5", R.drawable.whey_5, 29.90,"Supplements"),
            Product("Pure Whey Protein", "Description du produit 6", R.drawable.whey_6, 39.90,"Supplements"),
            Product("Creatine Monohydrate Tablets", "Description du produit 7", R.drawable.whey_7, 45.50,"Supplements"),
        )
    ),
    Category(
        categoryName = "Drawing",
        logo = R.drawable.feltpensbl,
        products = listOf(
            Product("Felt-tip pen, mixed colours", "Description du produit 1", R.drawable.drawing_1, 15.50,"Drawing"),
            Product("40 Felt Pens In Bucket", "Description du produit 2", R.drawable.drawing_2, 18.50,"Drawing"),
            Product("Pitt Artist Pen India ink pen", "Description du produit 3", R.drawable.drawing_3, 40.00,"Drawing"),
            Product("Connector felt tip pen set", "Description du produit 4",R.drawable.drawing_4, 30.00,"Drawing"),
            Product("Pitt Artist Pen Fineliner", "Description du produit 5", R.drawable.drawing_5, 14.90,"Drawing"),
        )
    )
)

fun SignOut(auth: FirebaseAuth, navController: NavController) {
    auth.signOut()
    navController.navigate(Routes.LoginScreen.route)
}


@Composable
fun newDisplayOrderHistory(): List<CartFirestore> {

    var newOrdersHistory by remember { mutableStateOf(emptyList<CartFirestore>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            actualUser.user_id?.let { it1 ->
                val querySnapshot = db.collection("orders-history")
                    .document(it1)
                    .collection("order")
                    .get()
                    .await()

                val updatedOrdersHistory = mutableListOf<CartFirestore>()

                for (document in querySnapshot) {
                    val amount = document.getDouble("amount")
                    val date = document.getString("date")
                    val time = document.getString("time")

                    val newCart = CartFirestore(
                        date = date,
                        amount = amount,
                        time = time
                    )

                    val productRef = document.reference.collection("product")

                    val prodQuerySnapshot = productRef.get().await()

                    for (productDoc in prodQuerySnapshot) {
                        val productName = productDoc.getString("name")
                        val productDescription = productDoc.getString("description")
                        val productPrice = productDoc.getDouble("price")
                        val productImage = productDoc.getDouble("image")

                        newCart.products += ProductFirestore(
                            name = productName,
                            description = productDescription,
                            price = productPrice,
                            image = productImage?.toInt()
                        )
                    }

                    updatedOrdersHistory += newCart
                }

                newOrdersHistory = updatedOrdersHistory
            }

        } catch (e: Exception) {
            Log.e(MainActivity.TAG, "Error fetching order history", e)
        }
    }
    return newOrdersHistory
}

@Composable
fun displayProductsFromCart(): List<ProductFirestore> {
    var newProductList by remember { mutableStateOf(emptyList<ProductFirestore>()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            actualUser.user_id?.let { it1 ->
                val querySnapshot = db.collection("cart")
                    .document(it1)
                    .collection("product")
                    .get()
                    .await()

                val updatedProductsOrder = mutableListOf<ProductFirestore>()

                for (document in querySnapshot) {
                    val name = document.getString("name")
                    val description = document.getString("description")
                    val price = document.getDouble("price")
                    val image = document.getDouble("image")

                    val newProduct = ProductFirestore(
                        name = name,
                        description = description,
                        price = price,
                        image = image?.toInt()
                    )

                    updatedProductsOrder += newProduct
                }

                newProductList = updatedProductsOrder
            }

        } catch (e: Exception) {
            Log.e(MainActivity.TAG, "Error fetching order history", e)
        }
    }
    return newProductList
}

@Composable
fun NumberInput(onValueChanged: (String) -> Unit) {

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    text = newText
                    onValueChanged(newText)
                }
            },
            label = { Text("Enter a number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.size(150.dp, 65.dp)
        )
    }
}

val pal_1_1: Color = Color(179,200,207)
val pal_1_2: Color = Color(190,215,220)
val pal_1_3: Color = Color(241,238,220)
val pal_1_4: Color = Color(229,221,197)

val pal_2_1: Color = Color(219,169,121)
val pal_2_2: Color = Color(236,202,156)
val pal_2_3: Color = Color(232,239,207)
val pal_2_4: Color = Color(175,209,152)

val pal_3_1: Color = Color(12,12,12)
val pal_3_2: Color = Color(72,30,20)
val pal_3_3: Color = Color(155,57, 34)
val pal_3_4: Color = Color(242,97,63)

val pal_4_1: Color = Color(45,50,80)
val pal_4_1_bis: Color = Color(58,65,103)
val pal_4_2: Color = Color(66,71,105)
val pal_4_3: Color = Color(112,119,161)
val pal_4_4: Color = Color(246,177, 122)

val pal_5_1: Color = Color(0xFFe4d5c6)
val pal_5_2: Color = Color(0xFFdbbe9f)
val pal_5_2_bis: Color = Color(0xFFc7ac90)

val pal_5_3: Color = Color(140, 120, 81)
val pal_5_3_bis: Color = Color(0xFFa08160)
val pal_5_4: Color = Color(113, 96, 64)
val pal_5_5: Color = Color(242, 80, 66)


val pal_test: Color = Color(84,110,122)

val pal_test_2: Color = Color(39, 53, 31)

val pal_button: Color = Color(0xFF074173)