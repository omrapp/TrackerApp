package com.omarayed.dev.trackerapp

import TrackerSDK
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omarayed.dev.trackerapp.data.Post

import com.omarayed.dev.trackerapp.ui.theme.TrackerAppTheme
import com.omarayed.dev.trackerapp.viewmodels.PostViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
class PostListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userId = intent.getIntExtra("USER_ID", 0)

        TrackerSDK.measure("OnCreate_PostListActivity") {

            setContent {
                TrackerAppTheme {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Posts",  style = MaterialTheme.typography.headlineLarge)
                            },
                            navigationIcon = {
                                IconButton(onClick = {onBackPressed()}) {
                                    Icon(Icons.Filled.
                                    ArrowBack, "backIcon")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                        ))
                    }) { innerPadding ->
                        PostsList(userId, this,
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
fun PostsList(userId: Int, activity: Activity? = null, modifier: Modifier = Modifier) {

    val viewModel = PostViewModel()
    val posts by viewModel.uiState.collectAsState()

    LazyColumn (modifier = modifier
        .fillMaxWidth().padding(16.dp)) {

        items(posts.filter { it.userId == userId }) { post ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemClick(post, activity) }
            )

                Text(
                    text = post.title,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = post.body,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            HorizontalDivider(Modifier.padding(8.dp))

        }
    }
}

fun onItemClick(post: Post, activity: Activity? = null) {
    val intent = Intent(activity, PostActivity::class.java)
    intent.putExtra("POST", post)
    activity?.startActivity(intent)
}


@Preview(showBackground = true)
@Composable
fun PostsListPreview() {
    TrackerAppTheme {
        PostsList(1)
    }
}