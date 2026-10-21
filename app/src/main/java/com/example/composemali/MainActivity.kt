package com.example.composemali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.animation.core.tween
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Button
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.animation.togetherWith
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border as border
import androidx.compose.foundation.clickable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.ui.tooling.preview.Preview
import com.example.composemali.ui.theme.ComposeMaliTheme

val OrbitronFont = FontFamily(
    Font(
        R.font.orbitron_bold,
        weight = FontWeight.Bold
    )
)
// Maliwise colors
private val DarkGreen = Color(0xFF006600)
private val BrightGreen = Color(0xFF69E36D)
private val LightGreen = Color(0xFFE8F7E9)
private val DarkText = Color(0xFF202820)
private val MutedText = Color(0xFF777F78)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMaliTheme {
                MaliwiseApp()
            }
        }
    }
}

data class Message(val author: String, val body: String)
enum class MaliwiseScreen {
    SPLASH,
    WELCOME,
    LOGIN,
    SIGNUP,
    HOME,
    ACTIVITY,
    BUDGET,
    PROFILE,
    ACCOUNTS,
    PAYMENTS,
    ADD_ACCOUNT
}



@Composable
fun MaliwiseApp() {
    var currentScreen by remember {
        mutableStateOf(MaliwiseScreen.SPLASH)
    }

    // Simple navigation history for the system Back button.
    val backStack = remember {
        mutableStateListOf<MaliwiseScreen>()
    }

    fun navigateTo(destination: MaliwiseScreen) {
        if (destination == currentScreen) return

        backStack.add(currentScreen)
        currentScreen = destination
    }

    fun goBack() {
        if (backStack.isNotEmpty()) {
            currentScreen = backStack.removeAt(backStack.lastIndex)
        } else {
            when (currentScreen) {
                MaliwiseScreen.ADD_ACCOUNT ->
                    currentScreen = MaliwiseScreen.ACCOUNTS

                MaliwiseScreen.LOGIN,
                MaliwiseScreen.SIGNUP ->
                    currentScreen = MaliwiseScreen.WELCOME

                MaliwiseScreen.WELCOME ->
                    currentScreen = MaliwiseScreen.WELCOME

                else -> currentScreen = MaliwiseScreen.HOME
            }
        }
    }

    // Automatically move from Splash to Welcome.
    LaunchedEffect(Unit) {
        delay(3000)

        if (currentScreen == MaliwiseScreen.SPLASH) {
            currentScreen = MaliwiseScreen.WELCOME
        }
    }

    // Handle the Android system Back button.
    androidx.activity.compose.BackHandler(
        enabled = currentScreen != MaliwiseScreen.SPLASH
    ) {
        goBack()
    }

    when (currentScreen) {
        MaliwiseScreen.SPLASH -> SplashScreen()

        MaliwiseScreen.WELCOME -> WelcomeScreen(
            onLogin = {
                navigateTo(MaliwiseScreen.LOGIN)
            },
            onSignUp = {
                navigateTo(MaliwiseScreen.SIGNUP)
            }
        )

        MaliwiseScreen.LOGIN -> LoginScreen(
            onLoginClick = { _, _, _ ->
                // Temporary navigation until authentication is implemented.
                navigateTo(MaliwiseScreen.HOME)
            }
        )

        MaliwiseScreen.SIGNUP -> SignupScreen(
            onSignUp = {
                // Temporary navigation until registration is implemented.
                navigateTo(MaliwiseScreen.HOME)
            },
            onLogin = {
                navigateTo(MaliwiseScreen.LOGIN)
            }
        )

        MaliwiseScreen.HOME -> HomeScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.ACCOUNTS -> AccountsOverviewScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.ADD_ACCOUNT -> AddAccountScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.ACTIVITY -> TransactionsListScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.BUDGET -> AddBudgetScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.PROFILE -> ProfileScreen(
            onNavigate = ::navigateTo
        )

        MaliwiseScreen.PAYMENTS -> TransactionsScreen(
            onNavigate = ::navigateTo
        )
    }
}



// SCREEN 1: SPLASH SCREEN
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Text displayed over the image
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MaliWise",
                fontSize = 60.sp,
                fontFamily = OrbitronFont,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                letterSpacing = 3.sp,
                modifier = Modifier.padding(
                    start = 14.dp,
                    bottom = 44.dp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// Welcome screen
@Composable
fun WelcomeScreen(
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.money_backgorund
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xD9061A0D))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MaliWise",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = OrbitronFont,
                color = BrightGreen
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Manage your money wisely.",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightGreen
                )
            ) {
                Text(
                    "LOGIN",
                    fontFamily = OrbitronFont,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onSignUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    "CREATE ACCOUNT",
                    fontFamily = OrbitronFont,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}



@Composable
fun SignupScreen(
    onSignUp: () -> Unit,
    onLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }

    val validEmail = android.util.Patterns.EMAIL_ADDRESS
        .matcher(email.trim())
        .matches()

    val passwordsMatch =
        password.isNotEmpty() && password == confirmPassword

    val formValid =
        name.isNotBlank() &&
                validEmail &&
                password.length >= 8 &&
                passwordsMatch

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4FAF4))
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.money_backgorund
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "MaliWise",
            fontSize = 50.sp,
            fontFamily = OrbitronFont,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Start managing your finances with MaliWise",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = submitted && name.isBlank()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = submitted && !validEmail
        )

        if (submitted && !validEmail) {
            Text(
                text = "Enter a valid email address.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Text(if (passwordVisible) "Hide" else "Show")
                }
            },
            isError = submitted && password.length < 4
        )

        if (submitted && password.length < 4) {
            Text(
                text = "Password must contain at least 8 characters.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        confirmPasswordVisible = !confirmPasswordVisible
                    }
                ) {
                    Text(if (confirmPasswordVisible) "Hide" else "Show")
                }
            },
            isError = submitted && !passwordsMatch
        )

        if (submitted && !passwordsMatch) {
            Text(
                text = "Passwords do not match.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                submitted = true

                if (formValid) {
                    onSignUp()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF19B638)
            )
        ) {
            Text(
                text = "CREATE ACCOUNT",
                fontWeight = FontWeight.Bold,
                fontFamily = OrbitronFont,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))


        TextButton(onClick = onLogin) {
            Text(
                text = "Already have an account? ",
                color = MutedText
            )
            Text(
                text = "Login",
                color = Color.Green,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}
}




@Composable
fun QuickAction(
    icon: String,
    title: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .size(width = 75.dp, height = 70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(LightGreen)
            .border(
                width = 1.dp,
                color = BrightGreen.copy(alpha = 0.35f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = title,
            fontSize = 12.sp,
            color = DarkGreen,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}


// SCREEN 2: HOME DASHBOARD
@Composable
fun HomeScreen(onNavigate: (MaliwiseScreen) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf("Home") }
    var balanceVisible by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xFF10271C),
    bottomBar = {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            val tabs = listOf(
                "Home", "Transacts", "Add", "Accounts", "Profile"
            )

            val symbols = listOf("⌂", "📄", "+", "📊", "👤")

            tabs.forEachIndexed { index, tab ->
                val isSelected = selectedTab == tab
                val isAddButton = tab == "Add"

                NavigationBarItem(
                    selected = isSelected,

                    onClick = {
                        selectedTab = tab

                        when (tab) {
                            "Home" -> onNavigate(MaliwiseScreen.HOME)
                            "Transacts" -> onNavigate(MaliwiseScreen.ACTIVITY)
                            "Add" -> onNavigate(MaliwiseScreen.BUDGET)
                            "Accounts" -> onNavigate(MaliwiseScreen.ACCOUNTS)
                            "Profile" -> onNavigate(MaliwiseScreen.PROFILE)
                        }
                    },

                    icon = {
                        if (isAddButton) {
                            Surface(
                                shape = CircleShape,
                                color = DarkText,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrightGreen
                                    )
                                }
                            }
                        } else {
                            Surface(
                                shape = if (isSelected) {
                                    RoundedCornerShape(16.dp)
                                } else {
                                    CircleShape
                                },
                                color = if (isSelected) {
                                    LightGreen
                                } else {
                                    Color.Transparent
                                },
                                modifier = Modifier.size(
                                    if (isSelected) 36.dp else 28.dp
                                )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = symbols[index],
                                        fontSize = 18.sp,
                                        color = if (isSelected) {
                                            DarkGreen
                                        } else {
                                            MutedText
                                        }
                                    )
                                }
                            }
                        }
                    },

                    label = {
                        Text(
                            text = tab,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            },
                            color = if (isSelected) {
                                DarkGreen
                            } else {
                                MutedText
                            }
                        )
                    },

                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Background image
            Image(
                painter = painterResource(
                    id = R.drawable.money_backgorund
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Dark overlay for readable text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xD910271C))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 20.dp)
            ) {
                Spacer(Modifier.height(22.dp))

                // Greeting and profile avatar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Good morning,",
                            color = Color.LightGray,
                            fontSize = 12.sp
                        )

                        Text(
                            text = "Denzel Omondi",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Surface(
                        modifier = Modifier.size(42.dp),
                        shape = CircleShape,
                        color = LightGreen
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "DO",
                                color = DarkGreen,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // Total balance card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF426B24)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(17.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total balance",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )

                            Text(
                                text = if (balanceVisible) "◉" else "⊘",
                                color = Color.White,
                                fontSize = 18.sp,
                                modifier = Modifier.clickable {
                                    balanceVisible = !balanceVisible
                                }
                            )
                        }

                        Spacer(Modifier.height(3.dp))

                        Text(
                            text = if (balanceVisible)
                                "Kshs.124,680.42"
                            else
                                "Kshs.••••••••",
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )

                        Spacer(Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "↗ +8.4% this month",// show monthly rate of use
                                color = BrightGreen,
                                fontSize = 10.sp
                            )

                            Text(
                                "3 accounts",// show number of accounts registered
                                color = Color.LightGray,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(15.dp))

                // Quick actions

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickAction("↗", "Send") {
                        onNavigate(MaliwiseScreen.PAYMENTS)
                    }

                    QuickAction("＋", "Add") {
                        onNavigate(MaliwiseScreen.ADD_ACCOUNT)
                    }

                    QuickAction("↔", "Transactions") {
                        onNavigate(MaliwiseScreen.ACTIVITY)
                    }

                    
                }


                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Spending Summary",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "September · 18 days left",
                    fontSize = 11.sp,
                    color = Color.LightGray
                )

                Spacer(Modifier.height(12.dp))

                // Spending summary card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            "SPENT THIS MONTH",
                            color = MutedText,
                            fontSize = 9.sp,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(Modifier.height(3.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Kshs.2,948.20",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Medium,
                                color = DarkText
                            )

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = LightGreen
                            ) {
                                Text(
                                    "↓ 8.4% vs Aug",
                                    modifier = Modifier.padding(
                                        horizontal = 7.dp,
                                        vertical = 5.dp
                                    ),
                                    fontSize = 9.sp,
                                    color = DarkGreen
                                )
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        SpendingRow(
                            category = "Housing",
                            percentage = "42%",
                            progress = 0.42f,
                            amount = "Kshs.1,240",
                            barColor = Color(0xFF193A2C)
                        )

                        SpendingRow(
                            category = "Food & dining",
                            percentage = "22%",
                            progress = 0.22f,
                            amount = "Kshs.638",
                            barColor = Color(0xFFFF927B)
                        )

                        SpendingRow(
                            category = "Transport",
                            percentage = "13%",
                            progress = 0.13f,
                            amount = "Kshs.392",
                            barColor = Color(0xFFC5B4F5)
                        )

                        Spacer(Modifier.height(4.dp))

                        // Remaining budget section
                        Surface(
                            shape = RoundedCornerShape(13.dp),
                            color = Color(0xFF172D23),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = BrightGreen,
                                    modifier = Modifier.size(26.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "▣",
                                            color = DarkGreen,
                                            fontSize = 13.sp
                                        )
                                    }
                                }

                                Spacer(Modifier.width(8.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        "Kshs.1,051.80 remaining",
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )

                                    Text(
                                        "of your Kshs.4,000.00 monthly budget",
                                        color = Color.LightGray,
                                        fontSize = 8.sp
                                    )
                                }

                                Text(
                                    "74%",
                                    color = BrightGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun QuickAction(symbol: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(65.dp)
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(13.dp),
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = symbol,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = label,
            fontSize = 9.sp,
            color = Color.White,
            maxLines = 1
        )
    }
}


@Composable
fun SpendingRow(
    category: String,
    percentage: String,
    progress: Float,
    amount: String,
    barColor: Color
) {
    Column(modifier = Modifier.padding(bottom = 9.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(barColor, CircleShape)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = category,
                    color = DarkText,
                    fontSize = 10.sp
                )

                Spacer(Modifier.width(5.dp))

                Text(
                    text = percentage,
                    color = MutedText,
                    fontSize = 9.sp
                )
            }

            Text(
                text = amount,
                color = DarkText,
                fontSize = 10.sp
            )
        }

        Spacer(Modifier.height(5.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
            color = barColor,
            trackColor = Color(0xFFE2E7E2),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}



// ==========================================
// 1. PROFILE SCREEN
// ==========================================
@Composable
fun ProfileScreen(
    onNavigate: (MaliwiseScreen) -> Unit = {}
) {
    var notifs by remember { mutableStateOf(true) }
    var biometric by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent.copy(alpha = 0.9f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Profile Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = DarkGreen)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f),
                        modifier = Modifier.size(70.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "DO",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Denzel Omondi",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "denzelomondi23@gmail.com",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "Edit Profile", color = Color.White, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Budget Setup", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Spacer(modifier = Modifier.height(8.dp))

            // Budget Setup Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Weekly Budget", fontSize = 13.sp, color = DarkText)
                        Text(text = "KSh 4,000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.45f },
                        modifier = Modifier.fillMaxWidth().height(6.dp),
                        color = DarkGreen,
                        trackColor = LightGreen,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "45% used this week", fontSize = 10.sp, color = MutedText)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Monthly Budget", fontSize = 13.sp, color = DarkText)
                        Text(text = "KSh 16,000", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Manage Budget", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Account Details", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Spacer(modifier = Modifier.height(8.dp))

            // Account Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileDetailRow("Phone", "+254 7XX XXX XXX")
                    Spacer(modifier = Modifier.height(10.dp))
                    ProfileDetailRow("Account Type", "Personal")
                    Spacer(modifier = Modifier.height(10.dp))
                    ProfileDetailRow("Member Since", "Jan 2024")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Quick Settings", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Spacer(modifier = Modifier.height(8.dp))

            // Quick Settings Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Transaction Notifications", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkText)
                            Text(text = "Get alerts for every transaction", fontSize = 11.sp, color = MutedText)
                        }
                        Switch(
                            checked = notifs,
                            onCheckedChange = { notifs = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = DarkGreen)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Biometric Login", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DarkText)
                            Text(text = "Use Face ID or fingerprint", fontSize = 11.sp, color = MutedText)
                        }
                        Switch(
                            checked = biometric,
                            onCheckedChange = { biometric = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = DarkGreen)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Dark Mode",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (darkMode) Color.White else DarkText
                            )

                            Text(
                                text = "Switch app theme",
                                fontSize = 11.sp,
                                color = if (darkMode) Color.LightGray else MutedText
                            )
                        }

                        Switch(
                            checked = darkMode,
                            onCheckedChange = { darkMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = DarkGreen
                            )
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun ProfileDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 12.sp, color = MutedText)
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkText)
    }
}
@Composable
fun PaymentCard(symbol: String, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .width(102.dp)
            .height(103.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF164734))

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = symbol, fontSize = 22.sp, color = DarkGreen)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center)
            Text(text = subtitle, fontSize = 9.sp, color = MutedText , textAlign = TextAlign.Center)
        }
    }
}
@Composable
fun AccountTypeOption(
    title: String,
    desc: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (selected) 1.5.dp else 1.dp,
                color = if (selected) {
                    BrightGreen
                } else {
                    Color(0xFF315444)
                },
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                Color(0xFF173D2E)
            } else {
                Color(0xFF10271C)
            }
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = desc,
                    fontSize = 12.sp,
                    color = Color(0xFFB7C8BE)
                )
            }

            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = BrightGreen,
                    unselectedColor = Color.LightGray
                )
            )
        }
    }
}
// ==========================================
// 2. TRANSACTIONS / PAYMENT OPTIONS SCREEN
// ==========================================

@Composable
fun TransactionsScreen(
    onNavigate: (MaliwiseScreen) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("Home") }
    var balanceVisible by remember { mutableStateOf(true) }
    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val tabs = listOf(
                    "Home", "Transacts", "Add", "Accounts", "Profile"
                )
                val symbols = listOf("⌂", "📄", "+", "📊", "👤")

                tabs.forEachIndexed { index, tab ->
                    val isSelected = selectedTab == tab
                    val isAddButton = tab == "Add"
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedTab = tab

                            when (tab) {
                                "Home" -> onNavigate(MaliwiseScreen.HOME)
                                "Transacts" -> onNavigate(MaliwiseScreen.ACTIVITY)
                                "Add" -> onNavigate(MaliwiseScreen.ADD_ACCOUNT)
                                "Accounts" -> onNavigate(MaliwiseScreen.ACCOUNTS)
                                "Profile" -> onNavigate(MaliwiseScreen.PROFILE)
                            }
                        },
                        icon = {
                            if (isAddButton) {
                                Surface(
                                    shape = CircleShape,
                                    color = DarkText,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "+",
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BrightGreen
                                        )
                                    }
                                }
                            } else {
                                Surface(
                                    shape = if (isSelected) {
                                        RoundedCornerShape(16.dp)
                                    } else {
                                        CircleShape
                                    },
                                    color = if (isSelected) {
                                        LightGreen
                                    } else {
                                        Color.Transparent
                                    },
                                    modifier = Modifier.size(
                                        if (isSelected) 36.dp else 28.dp
                                    )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = symbols[index],
                                            fontSize = 18.sp,
                                            color = if (isSelected) {
                                                DarkGreen
                                            } else {
                                                MutedText
                                            }
                                        )
                                    }
                                }
                            }
                        },
                        label = {
                            Text(tab, fontSize = 10.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DarkGreen,
                            selectedTextColor = DarkGreen,
                            indicatorColor = LightGreen,
                            unselectedIconColor = MutedText,
                            unselectedTextColor = MutedText
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Payment Options",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search paybill or service...", color = MutedText) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "PAY BILLS", fontSize = 12.sp, color = BrightGreen, fontWeight = FontWeight.Bold, fontFamily = OrbitronFont)
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                PaymentCard("👥", "M-Pesa Agent", "Withdraw Cash")
                PaymentCard("🛍", "Till Number", "Buy Goods")
                PaymentCard("💳", "Pochi la Biashara", "Business Wallet")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                PaymentCard("↗", "Send to PayPal", "PayPal Topup")
                PaymentCard("🌐", "International", "Global Remit")
                PaymentCard("↙", "Request Money", "Ask Friend")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "SEND/TRANSACT", fontSize = 12.sp, color = BrightGreen, fontWeight = FontWeight.Bold, fontFamily = OrbitronFont )
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                PaymentCard("👤", "M-Pesa User", "Phone Number")
                PaymentCard("🔄", "Other Network", "Airtel, T-Kash")
                PaymentCard("🏢", "Bank Account", "To Bank")
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
}

enum class AccountType(
    val title: String,
    val description: String
) {
    PERSONAL(
        title = "Personal Account",
        description = "Manage your personal spending and budget"
    ),
    BUSINESS(
        title = "Business Account",
        description = "Track business income and expenses"
    )
}

// ==========================================
// 3. ADD ACCOUNT SCREEN
// ==========================================
@Composable
fun AddAccountScreen(
    onNavigate: (MaliwiseScreen) -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("ACCOUNTS") }
    var fullName by remember { mutableStateOf("John Kamau Mwangi") }
    var phone by remember { mutableStateOf("+254 7XX XXX XXX") }
    var idNumber by remember { mutableStateOf("12345678") }
    var dob by remember { mutableStateOf("DD / MM / YYYY") }
    var email by remember { mutableStateOf("example@email.com") }
    var selectedType by remember { mutableStateOf("Personal Account") }
    var agreed by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onNavigate(MaliwiseScreen.ACCOUNTS)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to accounts",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Set Up Account",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                // Balances the back arrow so the title stays centered
                Spacer(modifier = Modifier.width(48.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "SELECT ACCOUNT TYPE", fontSize = 11.sp, color = BrightGreen, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            AccountType.entries.forEach { type ->
                AccountTypeOption(
                    title = type.title,
                    desc = type.description,
                    selected = selectedType == type.title,
                    onClick = { selectedType = type.title }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "ACCOUNT DETAILS", fontSize = 11.sp, color = BrightGreen, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            FormInputLabel("Full Name (as per ID)")
            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))

            Spacer(modifier = Modifier.height(8.dp))
            FormInputLabel("Phone Number")
            OutlinedTextField(value = phone, onValueChange = { phone = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))

            Spacer(modifier = Modifier.height(8.dp))
            FormInputLabel("National ID / Passport Number")
            OutlinedTextField(value = idNumber, onValueChange = { idNumber = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))

            Spacer(modifier = Modifier.height(8.dp))
            FormInputLabel("Date of Birth")
            OutlinedTextField(value = dob, onValueChange = { dob = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))

            Spacer(modifier = Modifier.height(8.dp))
            FormInputLabel("Email Address (Optional)")
            OutlinedTextField(value = email, onValueChange = { email = it }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White))

            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreed, onCheckedChange = { agreed = it })
                Text(text = "I agree to the Terms & Conditions and Privacy Policy", fontSize = 11.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val isFormValid =
                        fullName.isNotBlank() &&
                                phone.isNotBlank() &&
                                idNumber.isNotBlank() &&
                                dob.isNotBlank() &&
                                !dob.contains("DD") &&
                                agreed

                    if (isFormValid) {
                        // Temporary navigation until we implement account saving.
                        onNavigate(MaliwiseScreen.ACCOUNTS)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = agreed,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrightGreen,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.DarkGray
                )
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}


// ==========================================
// 1. ADD BUDGET / ADD TRANSACTION SCREEN
// ==========================================
@Composable
fun AddBudgetScreen(
    onNavigate: (MaliwiseScreen) -> Unit = {}
) {
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var timeSet by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ADD   BUDGET",
                fontSize = 24.sp,
                fontFamily = OrbitronFont,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))


            val budgetCategories = listOf(
                "Food & Groceries",
                "Transport",
                "Rent & Housing",
                "Education",
                "Airtime & Internet",
                "Personal Care",
                "Shopping & Clothing",
                "Health & Medical",
                "Entertainment",
                "Savings",
                "Family & Giving",
                "Other"
            )

            var categoryExpanded by remember { mutableStateOf(false) }

            // Category Field
            Text(
                text = "Category:",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select category") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Choose category"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { categoryExpanded = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFD9D9D9),
                        unfocusedContainerColor = Color(0xFFD9D9D9),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                // Makes the whole field open the dropdown
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { categoryExpanded = true }
                )

                DropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false },
                    modifier = Modifier.background(Color(0xFFD9D9D9))
                ) {
                    budgetCategories.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item, color = Color(0xFF202820)) },
                            onClick = {
                                category = item
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            // Amount Field
            Text(text = "Amount:", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9),
                    unfocusedContainerColor = Color(0xFFD9D9D9),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Time Set Field
            Text(text = "Time set:", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = timeSet,
                onValueChange = { timeSet = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9),
                    unfocusedContainerColor = Color(0xFFD9D9D9),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Date Field
            Text(text = "Date:", fontSize = 20.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9),
                    unfocusedContainerColor = Color(0xFFD9D9D9),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // SET Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7CAE69))
            ) {
                Text(
                    text = "SET",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = OrbitronFont,
                    color = Color.Black
                )
            }
        }
    }
}
@Composable
fun TransactionItem(icon: String, title: String, time: String, amount: String, isIncome: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = LightGreen,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = icon, fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Text(text = time, fontSize = 11.sp, color = MutedText)
            }
        }
        Text(
            text = amount,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = if (isIncome) DarkGreen else Color(0xFFC84B31)
        )
    }
}


@Composable
fun FilterChipItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (selected) Color(0xFFFFEBE8) else Color(0xFF2E4030)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color(0xFFC84B31) else Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

// ==========================================
// 2. TRANSACTIONS ACTIVITY LIST SCREEN
// ==========================================
@Composable
fun TransactionsListScreen(
    onNavigate: (MaliwiseScreen) -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedTab by remember { mutableStateOf("Transacts")}
    Scaffold(
    bottomBar = {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 8.dp
        ) {
            val tabs = listOf("Home", "Transacts", "Add", "Accounts", "Profile")
            val symbols = listOf("⌂", "📄", "+", "📊", "👤")

            tabs.forEach { tab ->
                val isSelected = selectedTab == tab
                val isAddButton = tab == "Add"

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        selectedTab = tab
                        when (tab) {
                            "Home" -> onNavigate(MaliwiseScreen.HOME)
                            "Transacts" -> onNavigate(MaliwiseScreen.ACTIVITY)
                            "Add" -> onNavigate(MaliwiseScreen.BUDGET)
                            "Accounts" -> onNavigate(MaliwiseScreen.ACCOUNTS)
                            "Profile" -> onNavigate(MaliwiseScreen.PROFILE)
                        }
                    },
                    icon = {
                        if (isAddButton) {
                            Surface(
                                shape = CircleShape,
                                color = DarkText,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrightGreen
                                    )
                                }
                            }
                        } else {
                            Surface(
                                shape = if (isSelected) RoundedCornerShape(16.dp) else CircleShape,
                                color = if (isSelected) LightGreen else Color.Transparent,
                                modifier = Modifier.size(if (isSelected) 36.dp else 28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = symbols[tabs.indexOf(tab)],
                                        fontSize = 18.sp,
                                        color = if (isSelected) DarkGreen else MutedText
                                    )
                                }
                            }
                        }
                    },
                    label = {
                        Text(
                            text = tab,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) DarkGreen else MutedText
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.money_backgorund),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.75f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Transactions",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🔍", fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Filter Chips Row
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChipItem("All", selectedFilter == "All") { selectedFilter = "All" }
                    FilterChipItem("Income", selectedFilter == "Income") { selectedFilter = "Income" }
                    FilterChipItem("Expenses", selectedFilter == "Expenses") { selectedFilter = "Expenses" }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Transactions Card List
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Recent activity", fontSize = 12.sp, color = MutedText, fontWeight = FontWeight.Bold)
                            Text(text = "This month", fontSize = 12.sp, color = MutedText)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        // room for realsitic transactions
                        TransactionItem("💼", "Salary", "Today · 09:12 AM", "+Kshs. 45,000.00", isIncome = true)
                        TransactionItem("🛒", "Groceries", "Yesterday · 06:45 PM", "-Kshs. 2,480.00", isIncome = false)
                        TransactionItem("🚗", "Uber ride", "Yesterday · 08:20 AM", "-Kshs. 1,250.00", isIncome = false)
                        TransactionItem("🏠", "Rent", "Mon · 10:05 AM", "-Kshs. 18,000.00", isIncome = false)
                        TransactionItem("💼", "Freelance payment", "Sun · 04:30 PM", "+Kshs. 12,500.00", isIncome = true)
                        TransactionItem("▶", "Netflix subscription", "Sat · 11:40 AM", "-Kshs. 1,200.00", isIncome = false)
                        TransactionItem("⚡", "Electricity bill", "Fri · 02:15 PM", "-Kshs. 3,850.00", isIncome = false)
                        TransactionItem("↗", "M-Pesa transfer", "Thu · 09:00 AM", "-Kshs. 5,000.00", isIncome = false)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountCardRow(
        icon: String,
        title: String,
        subtitle: String,
        amount: String,
        isNegative: Boolean
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = LightGreen,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = icon, fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Text(text = subtitle, fontSize = 11.sp, color = MutedText)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = amount,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isNegative) Color(0xFFC84B31) else DarkGreen
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "→", fontSize = 14.sp, color = MutedText)
                }
            }
        }
    }




// ==========================================
// 3. ACCOUNTS OVERVIEW SCREEN
// ==========================================
@Composable
fun AccountsOverviewScreen(onNavigate: (MaliwiseScreen) -> Unit = {}) {
    var selectedTab by remember { mutableStateOf("Accounts") }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val tabs = listOf(
                    "Home", "Transacts", "Add", "Accounts", "Profile"
                )
                val symbols = listOf("⌂", "📄", "+", "📊", "👤")

                tabs.forEachIndexed { index, tab ->
                    val isSelected = selectedTab == tab
                    val isAddButton = tab == "Add"
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedTab = tab
                            when (tab) {
                                "Home" ->
                                    onNavigate(MaliwiseScreen.HOME)

                                "Transacts" ->
                                    onNavigate(MaliwiseScreen.ACTIVITY)

                                "Add" ->
                                    onNavigate(MaliwiseScreen.ADD_ACCOUNT)

                                "Accounts" ->
                                    onNavigate(MaliwiseScreen.ACCOUNTS)

                                "Profile" ->
                                    onNavigate(MaliwiseScreen.PROFILE)
                            }
                        },
                        icon = {
                            if (isAddButton) {
                                Surface(
                                    shape = CircleShape,
                                    color = DarkText,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "+",
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BrightGreen
                                        )
                                    }
                                }
                            } else {
                                Surface(
                                    shape = if (isSelected) {
                                        RoundedCornerShape(16.dp)
                                    } else {
                                        CircleShape
                                    },
                                    color = if (isSelected) {
                                        LightGreen
                                    } else {
                                        Color.Transparent
                                    },
                                    modifier = Modifier.size(
                                        if (isSelected) 36.dp else 28.dp
                                    )
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = symbols[index],
                                            fontSize = 18.sp,
                                            color = if (isSelected) {
                                                DarkGreen
                                            } else {
                                                MutedText
                                            }
                                        )
                                    }
                                }
                            }
                        },
                        label = {
                            Text(tab, fontSize = 10.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DarkGreen,
                            selectedTextColor = DarkGreen,
                            indicatorColor = LightGreen,
                            unselectedIconColor = MutedText,
                            unselectedTextColor = MutedText
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.money_backgorund),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Accounts",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )


                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier
                            .size(38.dp)
                            .clickable {
                                onNavigate(MaliwiseScreen.ADD_ACCOUNT)
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "+",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = DarkText
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Total Balance Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkGreen)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Total balance", fontSize = 12.sp, color = Color.LightGray)
                            Text(text = "👁", fontSize = 14.sp, color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Kshs. 124,680.42", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "↗ +8.4% this month", fontSize = 11.sp, color = BrightGreen)
                            Text(text = "5 accounts", fontSize = 11.sp, color = Color.LightGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Accounts Item Cards
                AccountCardRow("📱", "M-Pesa", "Safaricom", "Kshs. 24,680.42", isNegative = false)
                Spacer(modifier = Modifier.height(10.dp))
                AccountCardRow("🏦", "Savings Account", "KCB Bank", "Kshs. 68,500.00", isNegative = false)
                Spacer(modifier = Modifier.height(10.dp))
                AccountCardRow("🏢", "Current Account", "Equity Bank", "Kshs. 15,200.00", isNegative = false)
                Spacer(modifier = Modifier.height(10.dp))
                AccountCardRow("💵", "Cash", "Personal funds", "Kshs. 3,800.00", isNegative = false)
                Spacer(modifier = Modifier.height(10.dp))
                AccountCardRow("💳", "Credit Card", "NCBA", "-Kshs. 12,500.00", isNegative = true)

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


@Composable
fun LoginInput(
        label: String,
        value: String,
        onValueChange: (String) -> Unit,
        keyboardType: KeyboardType = KeyboardType.Text,
        password: Boolean = false
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 23.sp,
                color = Color.White,
                fontFamily = FontFamily.Serif
            )

            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.78f)
                    .height(55.dp),
                shape = RoundedCornerShape(6.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (password) {
                        KeyboardType.Password
                    } else {
                        keyboardType
                    }
                ),
                visualTransformation = if (password) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFD9D9D9),
                    unfocusedContainerColor = Color(0xFFD9D9D9),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }

@Composable
fun LoginScreen(
    onLoginClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF061A0D))
    ) {
        Image(
            painter = painterResource(id = R.drawable.money_backgorund),
            contentDescription = "Money background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark overlay so the text remains readable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE6091A0D))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "MaliWise",
                fontSize = 46.sp,
                fontFamily = OrbitronFont,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF315D36),
                letterSpacing = 3.sp,
                modifier = Modifier.padding(
                    start = 14.dp,
                    bottom = 44.dp
                )
            )

            LoginInput(
                label = "Name:",
                value = name,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(60.dp))

            LoginInput(
                label = "Email:",
                value = email,
                onValueChange = { email = it },
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(60.dp))

            LoginInput(
                label = "Password:",
                value = password,
                onValueChange = { password = it },
                password = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onLoginClick(name, email, password)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.78f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF19B638)
                )
            ) {
                Text(
                    text = "LOGIN",
                    fontSize = 27.sp,
                    fontFamily = OrbitronFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}



// ==========================================
// PREVIEWS FOR NEW SCREENS
// ==========================================



@Composable
fun FormInputLabel(text: String) {
    Text(text = text, fontSize = 11.sp, color = Color.White, modifier = Modifier.padding(bottom = 4.dp))
}



@Preview(
    name = "MaliWise Splash",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewSplashScreen() {
    ComposeMaliTheme {
        SplashScreen()
    }
}

@Preview (
    name =  "Sign Up Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewSignUpScreen(){
    ComposeMaliTheme {
        SignupScreen(onSignUp = {}, onLogin = {})
    }
}

@Preview(
    name = "MaliWise Home",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewHomeScreen() {
    ComposeMaliTheme {
        HomeScreen()
    }
}

// ==========================================
// PREVIEWS FOR NEW SCREENS
// ==========================================

@Preview(
    name = "MaliWise Login Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewLoginScreen() {
    ComposeMaliTheme {
        LoginScreen()
    }
}

@Preview(
    name = "Add Budget Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewAddBudgetScreen() {
    ComposeMaliTheme {
        AddBudgetScreen()
    }
}

@Preview(
    name = "Transactions List Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewTransactionsListScreen() {
    ComposeMaliTheme {
        TransactionsListScreen()
    }
}

@Preview(
    name = "Accounts Overview Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewAccountsOverviewScreen() {
    ComposeMaliTheme {
        AccountsOverviewScreen()
    }
}

@Preview(
    name = "Profile Screen Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewProfileScreen() {
    ComposeMaliTheme {
        ProfileScreen()
    }
}

@Preview(
    name = "Transactions / Payment Options Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewTransactionsScreen() {
    ComposeMaliTheme {
        TransactionsScreen()
    }
}

@Preview(
    name = "Add Account Screen Preview",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewAddAccountScreen() {
    ComposeMaliTheme {
        AddAccountScreen()
    }
}

