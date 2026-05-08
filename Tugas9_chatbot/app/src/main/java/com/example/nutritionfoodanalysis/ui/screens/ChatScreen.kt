package com.example.nutritionfoodanalysis.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nutritionfoodanalysis.R
import com.example.nutritionfoodanalysis.data.local.ChatMessage
import com.example.nutritionfoodanalysis.logic.ChatViewModel
import com.example.nutritionfoodanalysis.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    var inputText by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = LiquidDarkTeal,
                drawerContentColor = Color.White
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "History Percakapan",
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = LiquidCyan
                )
                HorizontalDivider(color = GlassBorder)
                NavigationDrawerItem(
                    label = { Text("Hapus Semua Riwayat") },
                    selected = false,
                    onClick = {
                        viewModel.clearHistory()
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                        unselectedTextColor = Color.White
                    )
                )
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    ),
                    title = { 
                        Column {
                            Text("CHATBOT", fontSize = 10.sp, color = LiquidCyan, fontWeight = FontWeight.Bold)
                            Text("APPLICATION", fontSize = 10.sp, color = Color.White)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "History", tint = Color.White)
                        }
                    }
                )
            },
            bottomBar = {
                Column {
                    ChatInputBar(
                        text = inputText,
                        onTextChange = { inputText = it },
                        onSend = {
                            viewModel.sendMessage(inputText)
                            inputText = ""
                        },
                        enabled = !isLoading
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(LiquidDarkTeal, LiquidBlack)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Hello, I'm Noaii",
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(messages) { message ->
                            ChatBubble(message)
                        }
                        
                        if (isLoading) {
                            item {
                                TypingIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isUser) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LiquidGlassBot),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Bot",
                    tint = LiquidCyan,
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            color = if (message.isUser) LiquidGlassUser else LiquidGlassBot,
            shape = RoundedCornerShape(
                topStart = 28.dp,
                topEnd = 28.dp,
                bottomStart = if (message.isUser) 28.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 28.dp
            ),
            border = BorderStroke(1.dp, GlassBorder),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(14.dp),
                color = Color.White,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun TypingIndicator() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(LiquidGlassBot),
            contentAlignment = Alignment.Center
        ) {
             Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), 
                contentDescription = "Bot",
                tint = LiquidCyan,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = LiquidGlassBot,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 4.dp),
            border = BorderStroke(1.dp, GlassBorder),
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Noaii sedang berpikir...",
                modifier = Modifier.padding(12.dp),
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    enabled: Boolean = true
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = LiquidGlassBot,
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, GlassBorder)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                enabled = enabled,
                placeholder = { Text("Type your message here...", color = Color.White.copy(alpha = 0.4f)) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = LiquidCyan,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White.copy(alpha = 0.3f)
                )
            )
            IconButton(onClick = onSend, enabled = enabled && text.isNotBlank()) {
                Icon(
                    Icons.Default.Send, 
                    contentDescription = "Send", 
                    tint = if (enabled && text.isNotBlank()) LiquidCyan else LiquidCyan.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    NutritionFoodAnalysisTheme {
        Scaffold(
            containerColor = LiquidBlack,
            topBar = {
                Surface(shadowElevation = 4.dp, color = LiquidDarkTeal) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("CHATBOT", fontSize = 10.sp, color = LiquidCyan, fontWeight = FontWeight.Bold)
                            Text("APPLICATION", fontSize = 10.sp, color = Color.White)
                        }
                        Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                    }
                }
            },
            bottomBar = {
                ChatInputBar(text = "", onTextChange = {}, onSend = {})
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().background(LiquidBlack)) {
                Column(
                    modifier = Modifier.padding(paddingValues).fillMaxSize().padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Hello, I'm Noaii",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            ChatBubble(ChatMessage(text = "Halo! Saya Noaii. Ada yang bisa saya bantu?", isUser = false))
                        }
                        item {
                            TypingIndicator()
                        }
                    }
                }
            }
        }
    }
}
