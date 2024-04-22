package com.stu74525.lab3_74525

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stu74525.lab3_74525.MainActivity.Companion.TAG

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(isRegister: Int) {
    val navController = rememberNavController()

    var route = Routes.LoginScreen.route
    if (isRegister != 0) {

        val user = auth.currentUser
        val userId = user?.uid
        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val Actname = document.getString("name")
                        val Actsurname = document.getString("surname")
                        val Actemail = document.getString("email")
                        val Actphone = document.getString("phone")
                        val Actpassword = document.getString("password")
                        actualUser.name = Actname
                        actualUser.surname = Actsurname
                        actualUser.email = Actemail
                        actualUser.password = Actpassword
                        actualUser.phone = Actphone
                        actualUser.user_id = userId

                        navController.navigate(Routes.CategoriesScreen.route)
                    } else {
                        Log.e(TAG, "Les données de l'utilisateur n'existent pas dans Firestore\n\n\n")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Erreur lors de la récupération des données utilisateur\n\n\n", exception)
                }
        } else {
            Log.e(TAG, "L'UID de l'utilisateur est null\n\n\n")
        }

        route = Routes.CategoriesScreen.route
    }
    NavHost(
        navController = navController,
        startDestination = route,
    ) {
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Routes.RegisterScreen.route) {
            ShowForm(navController = navController)
        }

        
        

        composable(route = Routes.CategoriesScreen.route) {
            ProductCategoriesScreen(navController = navController)
        }

        composable(route = Routes.ProductsListScreen.route + "/{category}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("category")
            val category = categories.find { it.categoryName == categoryName }
            category?.let {
                ProductListScreen(navController = navController, category = it)
            } ?: run {
                Log.e(TAG, "CAN'T FIND CATEGORY " + {categoryName} + "\n\n\n\n")
            }
        }

        composable(route = Routes.ProductScreen.route + "/{product}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("product")
            var temp: Product? = null
            for (element in categories) {
                temp = element.products.find { it.name == productName }
                if (temp != null) break
            }
            val product = temp

            product?.let {
                ProductScreen(navController = navController, product = it)
            } ?: run {
                Log.e(TAG, "CAN'T FIND PRODUCT WITH ID $productName")
            }
        }
        
        
        
        
        composable(route = Routes.CartScreen.route) {
            CartScreen(navController = navController)
        }
        
        
        
        composable(route = Routes.OrdersHistoryScreen.route) {
            OrderHistoryScreen(navController = navController)
        }
        /*
        composable(route = Routes.ProductHistoryScreen.route + "/{productHist}") {backStackEntry ->
            val productHistName = backStackEntry.arguments?.getString("productHist")
            val productHist = history.products.find { it.name == productHistName }

            productHist?.let {
                ProductHistoryScreen(navController = navController, productHist = it)
            } ?: run {
                Log.e(TAG, "CAN'T FIND CATEGORY " + {productHistName} + "\n\n\n\n")
            }
        }

         */

        /*
        composable(route = Routes.ProductHistoryScreen.route + "/{products}") {backStackEntry ->
            val productsJson = backStackEntry.arguments?.getString("products")
            Log.d(TAG, "JSON : " + productsJson + "\n\n\n")
            val productsListType = object : TypeToken<List<ProductFirestore>>() {}.type
            val products = Gson().fromJson<List<ProductFirestore>>(productsJson, productsListType)

            if (products != null) {
                ProductHistoryScreen(navController = navController, productList = products.toList())
            } else {
                Log.e(TAG, "INVALID PRODUCTS: $productsJson")
            }
        }

         */
        composable(route = Routes.ProductsListHistoryScreen.route + "/{index}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("index")
            productId?.let { ProductListHistoryScreen(navController = navController, id = it.toInt()) }
        }

        
        
        
        
        composable(route = Routes.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }



        composable(route = Routes.ProductHistoryScreen.route + "/{product}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("product")
            var temp: Product? = null
            for (element in categories) {
                temp = element.products.find { it.name == productName }
                if (temp != null) break
            }
            val product = temp

            product?.let {
                ProductHistoryScreen(navController = navController, product = it)
            } ?: run {
                Log.e(TAG, "CAN'T FIND PRODUCT WITH ID $productName")
            }
        }
    }
}