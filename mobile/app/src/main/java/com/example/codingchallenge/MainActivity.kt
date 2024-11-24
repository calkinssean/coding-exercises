package com.example.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.codingchallenge.ui.theme.CodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOSMConfig()
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