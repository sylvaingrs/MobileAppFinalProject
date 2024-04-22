package com.stu74525.lab3_74525

import android.annotation.SuppressLint
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.stu74525.lab3_74525.MainActivity.Companion.TAG


@SuppressLint("StaticFieldLeak")
val db = FirebaseFirestore.getInstance()

@SuppressLint("UnrememberedMutableState")

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current


    val IsEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val IsPasswordValid by derivedStateOf {
        password.length > 7
    }

    var IsPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .background(pal_5_1)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            fontSize = 40.sp,
        )
        Image(
            painter = painterResource(id = R.drawable.shop),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                    .padding(30.dp)
        )
        Text(
            text = "Please login or signup for new members",
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color.Black)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier

                    .padding(10.dp)


            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text(text = "Email Address")},
                    placeholder = { Text(text = "abc@domain.com")},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down)}
                    ),
                    isError = !IsEmailValid,
                    trailingIcon = {
                        if (email.isNotBlank()) {
                            IconButton(onClick = { email = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear email"
                                )
                            }
                        }
                    }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password")},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus()}
                    ),
                    isError = !IsPasswordValid,
                    trailingIcon = {
                        IconButton(onClick = { IsPasswordVisible = !IsPasswordVisible }) {
                            
                            Icon(
                                imageVector = if(IsPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    visualTransformation = if (IsPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
                Button(
                    onClick = {
                              auth.signInWithEmailAndPassword(email, password)
                                  .addOnCompleteListener {task ->
                                      if (task.isSuccessful) {
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
                                      }
                                  }
                                  .addOnFailureListener {
                                      Log.e(TAG, "FAILURE TO LOG IN\n\n\n\n")
                                  }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(pal_4_2),
                    enabled = IsEmailValid && IsPasswordValid
                ) {
                    Text(
                        text = "Log in",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgotten password?",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }
        }
        Button(
            onClick = {navController.navigate(Routes.RegisterScreen.route)},
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(pal_4_3)
        ) {
            Text(
                text = "Register",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}