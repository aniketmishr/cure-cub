package com.example.mhchatbot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch


@Composable
fun WelcomeScreen(
    navController: NavController,
    onStartChatting: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf("Chat") }
    Box(modifier = Modifier.background(Color(0xFFE6F4F7))) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { NavigationDrawerContent() },
        ) {
            Box(modifier = Modifier.background(Color(0xFFE6F4F7))) {
                Scaffold(
                    modifier = Modifier.background(Color(0xFFE6F4F7)),
                    topBar = {
                        Row(
                            modifier = Modifier
                                .background(Color(0xFFE6F4F7))
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            //                       Spacer(modifier=Modifier.width(16.dp))
                            Icon(
                                painter = painterResource(R.drawable.menu),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { coroutineScope.launch {  if (drawerState.isOpen) drawerState.close() else drawerState.open()} }
                                    .padding(16.dp)
                                    .size(32.dp),
                                tint = Color.Black
                            )
                        }
                    },
                    bottomBar = {
                        Box(
                            modifier=Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE6F4F7))
                                .padding(8.dp)
                                .clip(RoundedCornerShape(48f))
                        ) {
                            BottomNavigationBar(selectedTab = selectedTab) {
                                selectedTab = it
                            }
                        }
                    }
                ) { contentPadding ->
                    Box(
                        modifier = Modifier
                            .padding(contentPadding)
                            .background(Color(0xFFE6F4F7))
                    ) {
                        WelcomeScreenBody(
                            navController = navController,
                            onStartChatting = onStartChatting
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreenBody(
    onStartChatting: () -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F4F7))
    ) {
        // Hamburger menu icon

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Bear avatar
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF49BEB8)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bear_image),
                    contentDescription = "CureCub Avatar",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Welcome text
            Text(
                text = "Hey! I'm CureCub",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Terms text
            Text(
                text = "By continuing you are agree to our terms and conditions",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Start Chatting button
            Button(
                onClick = onStartChatting,
                modifier = Modifier
                    .width(240.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB2E5E7)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Start Chatting",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    // Chat icon
                    Box(
                        modifier = Modifier
                            .size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Simple chat bubble icon
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Chat",
                            tint = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NavigationDrawerContent() {
    val scrollState = rememberScrollState()
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
                        modifier = Modifier.size(120.dp).clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "CureCub", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Text(text = "v1.0", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color=Color.DarkGray)
                }

                Text(text = "Check for Updates", fontSize = 16.sp, color = Color(0xFF2699F5),
                    modifier = Modifier.clickable { })

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(modifier = Modifier.height(5.dp))
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(label = { Text(text = "Report Bugs") },
                    selected = false,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.bug),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    },
                    onClick = {
                        //                    openGmailApp("mailto:aniketmishra3476@gmail.com?subject=Connect: Report Bug",context)
                    })
                Spacer(modifier = Modifier.height(5.dp))
                NavigationDrawerItem(label = { Text(text = "Suggestion") }, selected = false,
                    onClick = {
                        //                    openGmailApp("mailto:aniketmishra3476@gmail.com?subject=Connect: Suggestion",context)
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
                        //                    openBrowser(context,"https://github.com/aniketmishr/connect-app")
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
                        //                    shareApp(context, "https://github.com/aniketmishr/connect-app?tab=readme-ov-file#installation")
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
                icon = Icons.Outlined.Star,
                selected = selectedTab == "Chat",
                onSelected = { onTabSelected("Chat") },
                modifier = Modifier.weight(1f)
            )

            NavItem(
                title = "Self-Help",
                icon = Icons.Outlined.Star,
                selected = selectedTab == "Self-Help",
                onSelected = { onTabSelected("Self-Help") },
                modifier = Modifier.weight(1f)

            )

            NavItem(
                title = "Journal",
                icon = Icons.Outlined.Star,
                selected = selectedTab == "Journal",
                onSelected = { onTabSelected("Journal") },
                modifier = Modifier.weight(1f)

            )

            NavItem(
                title = "Mood Booster",
                icon = Icons.Outlined.Star, // Replace with actual rocket icon
                selected = selectedTab == "Mood Booster",
                onSelected = { onTabSelected("Mood Booster") },
                modifier = Modifier.weight(1f)

            )
        }

}

@Composable
fun NavItem(
    title: String,
    icon: ImageVector,
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
            imageVector = icon,
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