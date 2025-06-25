package com.app.stuteacher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.app.stuteacher.navigation.AppNavGraph
import com.app.stuteacher.ui.theme.StuTeacherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StuTeacherTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
