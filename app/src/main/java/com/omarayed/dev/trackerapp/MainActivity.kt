package com.omarayed.dev.trackerapp

import TrackerSDK
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.omarayed.dev.trackerapp.data.User
import com.omarayed.dev.trackerapp.network.NetworkManager

import com.omarayed.dev.trackerapp.ui.theme.TrackerAppTheme
import com.omarayed.dev.trackerapp.viewmodels.UserViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        TrackerSDK.measure("OnCreate_MainActivity") {

            setContent {
                TrackerAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Users", style = MaterialTheme.typography.headlineLarge)
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                            )
                        )
                    }) { innerPadding ->
                        UserList(
                            this,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }

        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserList(activity: Activity? = null, modifier: Modifier = Modifier) {

    val viewModel = UserViewModel()
    val users by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        items(users) { user ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(user, activity) }
            )

            Text(
                text = user.name,
                modifier = Modifier
                    .padding(2.dp),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = user.email,
                modifier = Modifier
                    .padding(2.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = user.username,
                modifier = Modifier
                    .padding(2.dp),
                style = MaterialTheme.typography.titleMedium
            )

            HorizontalDivider(Modifier.padding(8.dp))

        }
    }
}

fun onItemClick(user: User, activity: Activity? = null) {
    val intent = Intent(activity, PostListActivity::class.java)
    intent.putExtra("USER_ID", user.id)
    activity?.startActivity(intent)
}


@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    TrackerAppTheme {
        UserList()
    }
}