package com.stu74525.lab3_74525

sealed class Routes(val route: String) {
    object LoginScreen: Routes("login_screen")
    object RegisterScreen: Routes("register_screen")
    object CategoriesScreen: Routes("categories_screen")
    object ProductsListScreen: Routes("products_list_screen")
    object ProductScreen:Routes("product_screen")
    object CartScreen: Routes("cart_screen")
    object OrdersHistoryScreen: Routes("orders_history_screen")
    object ProductsListHistoryScreen: Routes("product_list_history_screen")
    object ProductHistoryScreen: Routes("product_history_screen")
    object ProfileScreen: Routes("profile_screen")
}