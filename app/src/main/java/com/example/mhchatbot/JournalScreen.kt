package com.example.mhchatbot

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mhchatbot.ui.theme.backgroundColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Data class for journal entries
@RequiresApi(Build.VERSION_CODES.O)
data class JournalEntry constructor(
    val id: Int,
    val date: LocalDate = LocalDate.now(),
    var content: String
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalScreen() {
    // In-memory storage for journal entries (will be destroyed when app is closed)
    val entries = remember { mutableStateListOf<JournalEntry>(
        JournalEntry(1, LocalDate.of(2025, 2, 27),
            "I Was feeling really down, but after watching some videos and chatting, I felt so much better."),
        JournalEntry(2, LocalDate.of(2025, 2, 28),
            "I Was feeling really good, after watching some videos and chatting to curecub, I felt so much better.")
    ) }

    var nextId = remember { mutableStateOf(3) }
    var showAddDialog = remember { mutableStateOf(false) }
    var editingEntry = remember { mutableStateOf<JournalEntry?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // App Bar


            // Journal content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color(0xFFF0F8FF), // Light blue background
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
            ) {
                // Journal entries
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val groupedEntries = entries.groupBy { it.date }

                    groupedEntries.forEach { (date, entriesList) ->
                        item {
                            Text(
                                text = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                                color = Color(0xFF2196F3),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(entriesList) { entry ->
                            JournalEntryCard(
                                entry = entry,
                                onEditClick = { editingEntry.value = entry }
                            )
                        }
                    }
                }

                // Add button
                FloatingActionButton(
                    onClick = { showAddDialog.value = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Journal Entry")
                }
            }
        }

        // Add journal entry dialog
        if (showAddDialog.value) {
            AddJournalEntryDialog(
                onDismiss = { showAddDialog.value = false },
                onSave = { content ->
                    entries.add(JournalEntry(nextId.value, LocalDate.now(), content))
                    nextId.value++
                    showAddDialog.value = false
                }
            )
        }

        // Edit journal entry dialog
        editingEntry.value?.let { entry ->
            EditJournalEntryDialog(
                entry = entry,
                onDismiss = { editingEntry.value = null },
                onSave = { updatedContent ->
                    val index = entries.indexOfFirst { it.id == entry.id }
                    if (index != -1) {
                        entries[index] = entry.copy(content = updatedContent)
                    }
                    editingEntry.value = null
                }
            )
        }
    }
}

@Composable
fun JournalEntryCard(entry: JournalEntry, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clickable { onEditClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = entry.content,
            modifier = Modifier.padding(16.dp),
            color = Color.DarkGray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalEntryDialog(onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "New Journal Entry",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(color = Color.Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("How are you feeling today?", color = Color.DarkGray) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    TextButton(
                        onClick = { onSave(text) },
                        enabled = text.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditJournalEntryDialog(entry: JournalEntry, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf(entry.content) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Journal Entry",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = entry.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    color = Color(0xFF2196F3),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    TextButton(
                        onClick = { onSave(text) },
                        enabled = text.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}



@Composable
fun BottomNavigationItem(icon: String, label: String, selected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(64.dp)
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = if (selected) Color(0xFF2196F3) else Color.Gray,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(2.dp)
                    .background(Color(0xFF2196F3))
            )
        }
    }
}