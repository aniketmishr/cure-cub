package com.example.mhchatbot

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mhchatbot.ui.theme.MHChatbotTheme
import com.example.mhchatbot.ui.theme.backgroundColor
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        val userPreferences = StoreUserInfo(applicationContext)
        val chatViewModel = ChatViewModel(userPreferences)
        val audioPlayerViewModel = ViewModelProvider(this)[AudioPlayerViewModel::class.java]

        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = backStackEntry?.destination?.route ?: Screen.Welcome.route
            var selectedTab by remember { mutableStateOf("Chat") }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()


            MHChatbotTheme {
                Box(
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 32.dp,
                            bottom = 16.dp
                        )
                ) {
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = { NavigationDrawerContent() }
                    ) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {

                                    Box(modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFE6F4F7))) {
                                        if (currentScreen==Screen.Welcome.route||currentScreen==Screen.MindBoosterScreen.route||currentScreen==Screen.SelfHelp.route||currentScreen==Screen.JournalScreen.route) {
                                            Row(
                                                modifier = Modifier
                                                    .background(Color(0xFFE6F4F7))
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                //                       Spacer(modifier=Modifier.width(16.dp))
                                                Icon(
                                                    painter = painterResource(R.drawable.humburger_icon),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .clickable { coroutineScope.launch { if (drawerState.isOpen) drawerState.close() else drawerState.open() } }
                                                        .padding(16.dp)
                                                        .size(32.dp),
                                                    tint = Color.Black
                                                )
                                            }
                                        } else if (currentScreen==Screen.AudioScreen.route||currentScreen==Screen.VideoSelfHelp.route||currentScreen==Screen.Article.route){
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .clickable { navController.popBackStack() }
                                                    .padding(16.dp)
                                                    .size(32.dp),
                                                tint = Color.Black
                                            )
                                        }
                                        else if (currentScreen==Screen.Chat.route) {
                                            ChatTopBar(navController)
                                        }

                                        else{
                                            Box(modifier=Modifier
                                                .fillMaxWidth()
                                                .background(Color.Transparent))
                                        }


                                        // For text display at top bar
                                        if (currentScreen==Screen.Welcome.route) TopBarText("Chat", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.AudioScreen.route) TopBarText("Relaxing Audios", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.SelfHelp.route) TopBarText("Self-Help", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.VideoSelfHelp.route) TopBarText("Videos", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.MindBoosterScreen.route) TopBarText("Mind Booster", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.JournalScreen.route) TopBarText("My Journal", modifier = Modifier.align(Alignment.Center))
                                        else if (currentScreen==Screen.Article.route) TopBarText("Article", modifier = Modifier.align(Alignment.Center))

                                        else TopBarText("", modifier = Modifier.align(Alignment.Center))

                                }

                            },
                            bottomBar = {
                                if (currentScreen==Screen.Welcome.route||currentScreen==Screen.MindBoosterScreen.route||currentScreen==Screen.SelfHelp.route||currentScreen==Screen.JournalScreen.route) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFE6F4F7))
                                            .padding(8.dp)
                                            .clip(RoundedCornerShape(48f))
                                    ) {
                                        BottomNavigationBar(selectedTab = selectedTab) {
                                            selectedTab = it
                                            if (selectedTab=="Chat") {navController.navigate(Screen.Chat.route)}
                                            else if (selectedTab=="Mood Booster") {navController.navigate(Screen.MindBoosterScreen.route)}
                                            else if (selectedTab=="Self-Help") {navController.navigate(Screen.SelfHelp.route)}
                                            else if (selectedTab=="Journal") {navController.navigate(Screen.JournalScreen.route)} //TODO: Journal SCreen
                                            else {
                                                Log.d("hi", "nothing")}
                                        }
                                    }
                                } else{
                                    Box(modifier=Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent))
                                }
                            }
                        )
                        { innerPadding ->

                            NavHost(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController, startDestination = Screen.Welcome.route
                            ) {
                                composable(route = Screen.Welcome.route) {
                                    WelcomeScreen(
                                        navController = navController,
                                        onStartChatting = { navController.navigate(Screen.Chat.route) })
                                }
                                composable(route = Screen.Name.route) {
                                    NameScreen(
                                        userPreferences= userPreferences,
                                        navController = navController,
                                        onGetStarted = { navController.navigate(Screen.Welcome.route) })
                                }
                                composable(route = Screen.Chat.route) {
                                    ChatPage(Modifier, chatViewModel)
                                }
                                composable(route = Screen.AudioScreen.route) {
                                    RelaxingAudiosScreen(onBackClick = {}, audioPlayerViewModel)
                                }
                                composable(route = Screen.MeditationScreen.route) {
                                    MeditationScreen()
                                }
                                composable(route = Screen.MindBoosterScreen.route) {
                                    MindBoosterScreen()
                                }
                                composable(route = Screen.SelfHelp.route) {
                                    SelfHelpScreen(navController)
                                }
                                composable(route = Screen.VideoSelfHelp.route) {
                                    VideoSelfHelpScreen()
                                }
                                composable(route = Screen.JournalScreen.route) {
                                    JournalScreen()
                                }
                                composable(route = Screen.Article.route) {
                                    ArticlesScreen()
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun NavigationDrawerContent() {
    val sharedScreenViewModel = SharedScreenViewModel()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(300.dp),
        drawerContainerColor = Color(0xFFE6F4F7)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F4F7))) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(scrollState, true),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.bear_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "CureCub", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Text(text = "v1.0", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color= Color.DarkGray)
                }

                Text(text = "Check for Updates", fontSize = 16.sp, color = Color(0xFF2699F5),
                    modifier = Modifier.clickable { })

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.height(5.dp))
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(modifier = Modifier
                    ,label = { Text(text = "Report Bugs") },
                    selected = false,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.bug),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    },
                    onClick = {
                                            sharedScreenViewModel.openGmailApp("mailto:aniketmishra3476@gmail.com?subject=Connect: Report Bug",context)
                    })
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(label = { Text(text = "Suggestion") }, selected = false,
                    onClick = {
                                            sharedScreenViewModel.openGmailApp("mailto:aniketmishra3476@gmail.com?subject=Connect: Suggestion",context)
                    }, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.suggestion),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    })
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(label = { Text(text = "Star Github Repo") }, selected = false,
                    onClick = {
                                            sharedScreenViewModel.openBrowser(context,"https://github.com/aniketmishr/connect-app")
                    }, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    })
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(label = { Text(text = "Share App") }, selected = false,
                    onClick = {
                                            sharedScreenViewModel.shareApp(context, "https://github.com/aniketmishr/connect-app?tab=readme-ov-file#installation")
                    }, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.share),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    })
            }
        }
    }
}


@Composable
fun ChatTopBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4DC2B7))
            ) {
                // Using a placeholder for the bear icon
                // In a real app, you would use your actual bear drawable resource
                Text(
                    text = "🐻",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "CureCub",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(7.dp).height(7.dp).clip(CircleShape).background(Color.Green))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Active Now",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }

}

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    // Navigation items
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavItem(
            title = "Chat",
            icon = R.drawable.chat,
            selected = selectedTab == "Chat",
            onSelected = { onTabSelected("Chat")
                         },
            modifier = Modifier.weight(1f)
        )

        NavItem(
            title = "Self-Help",
            icon = R.drawable.selfhelp,
            selected = selectedTab == "Self-Help",
            onSelected = { onTabSelected("Self-Help") },
            modifier = Modifier.weight(1f)

        )

        NavItem(
            title = "Journal",
            icon = R.drawable.journal,
            selected = selectedTab == "Journal",
            onSelected = { onTabSelected("Journal") },
            modifier = Modifier.weight(1f)

        )

        NavItem(
            title = "Mood Booster",
            icon = R.drawable.mood_booster, // Replace with actual rocket icon
            selected = selectedTab == "Mood Booster",
            onSelected = { onTabSelected("Mood Booster") },
            modifier = Modifier.weight(1f)

        )
    }

}

@Composable
fun NavItem(
    title: String,
    icon: Int,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable { onSelected() }
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            tint = if (selected) Color.Black else Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            fontSize = 12.sp,
            color = if (selected) Color.Black else Color.Gray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(24.dp)
                    .height(2.dp)
                    .background(Color.Black)
            )
        }
    }
}

@Composable
fun TopBarText(text:String, modifier: Modifier) {
    Text(
        text=text,
        modifier = modifier,
        fontSize = 22.sp,
        color=Color.Black
    )
}