package edu.ucne.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroestudiantes.presentation.navigation.RegistroNavHost
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroEstudiantesTheme {
                val navHostController = rememberNavController()
                RegistroNavHost(navHostController = navHostController)
            }
        }
    }
}