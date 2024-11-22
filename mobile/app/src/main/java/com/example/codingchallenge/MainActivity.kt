package com.example.codingchallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.codingchallenge.presentation.NavigationHost
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOSMConfig()
//        Log.d("MainActivity", "onCreate: MainActivity")
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppScaffold(navController = navController)
        }
    }

    private fun setOSMConfig() {
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE))

    }
}

@Composable
fun AppScaffold(navController: NavHostController) {
    CodingChallengeTheme {
        NavigationHost(navController = navController)
    }
}