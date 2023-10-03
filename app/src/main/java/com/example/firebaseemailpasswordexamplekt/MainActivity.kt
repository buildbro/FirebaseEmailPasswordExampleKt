package com.example.firebaseemailpasswordexamplekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseemailpasswordexamplekt.ui.theme.FirebaseEmailPasswordExampleKtTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseEmailPasswordExampleKtTheme {
                var navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login_screen") {
                    composable("login_screen") {
                        LoginScreen(navController, auth)
                    }
                    composable("signup_screen") {
                        SignupScreen(navController, auth)
                    }
                    composable("home_screen") {
                        HomeScreen(auth)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {

    var emailText by remember {
        mutableStateOf("")
    }
    var passordText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Firebase Auth Example App")
        Text(text = "Please login or register a new account to continue...")
        Box(modifier = Modifier.height(8.dp))
        Text(text = "Email:")
        OutlinedTextField(
            value = emailText.toString(),
            onValueChange = {
            text->
                emailText = text

            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(8.dp))
        Text(text = "Password")
        OutlinedTextField(
            value = passordText.toString(),
            onValueChange = {
            text->
                passordText = text
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(emailText, passordText)
                    .addOnCompleteListener {
                            task->
                        if (task.isSuccessful) {
                            // TODO Show toast that login was successful
                            navController.navigate("home_screen")
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Row {
            Text(text = "New user?", modifier = Modifier.padding(12.dp))
            TextButton(onClick = {
                navController.navigate("signup_screen")
            }) {
                Text(text = "SignUp")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, auth: FirebaseAuth) {

    var emailText by remember {
        mutableStateOf("")
    }
    var passordText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Firebase Auth Example App")
        Text(text = "Please login or register a new account to continue...")
        Box(modifier = Modifier.height(8.dp))
        Text(text = "Email:")
        OutlinedTextField(
            value = emailText.toString(),
            onValueChange = {
            text->
                emailText = text
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(8.dp))
        Text(text = "Password")
        OutlinedTextField(
            value = passordText.toString(),
            onValueChange = {
            text->
                passordText = text
            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(8.dp))
        Text(text = "Repeat Password")
        OutlinedTextField(
            value = "",
            onValueChange = {

            },
            modifier = Modifier.fillMaxWidth()
        )
        Box(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(emailText, passordText)
                    .addOnCompleteListener {
                        task->
                            if (task.isSuccessful) {
                                //TODO add toast to show registration was successful
                                navController.navigate("login_screen")
                            }




                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "SignUp")
        }

        TextButton(onClick = {
            navController.navigate("login_screen")
        }) {
            Text(text = "Back to Login")
        }
    }
}

@Composable
fun HomeScreen(auth: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hello")
        Text(text = auth.currentUser?.email!!)
    }
}
