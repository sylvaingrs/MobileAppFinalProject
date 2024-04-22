package com.stu74525.lab3_74525
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.stu74525.lab3_74525.ui.theme.Lab374525Theme
import coil.compose.rememberImagePainter
lateinit var auth: FirebaseAuth

var currentUser: FirebaseUser? = null

val actualUser: User = User(
    name = "",
    surname = "",
    email = "",
    phone = "",
    password = "",
    user_id = ""
)


class MainActivity : ComponentActivity() {

    companion object {
        val TAG : String = MainActivity::class.java.simpleName
    }




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Lab374525Theme {

                //val ordersHist = displayOrderHistory()

                auth = FirebaseAuth.getInstance()
                currentUser = auth.currentUser
                if (currentUser != null)
                {
                    AppNavigation(1)
                }
                else {
                    AppNavigation(0)
                }

            }
        }
    }
}