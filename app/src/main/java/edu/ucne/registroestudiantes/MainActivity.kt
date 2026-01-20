package edu.ucne.registroestudiantes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registroestudiantes.Presentation.Estudiante.Edit.EditEstudianteScreen
import edu.ucne.registroestudiantes.Presentation.Estudiante.List.EstudianteListScreen
import edu.ucne.registroestudiantes.ui.theme.RegistroEstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroEstudiantesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "estudianteList"
                ) {
                    composable("estudianteList") {
                        EstudianteListScreen(
                            onNavigateToEdit = { id ->
                                navController.navigate("editEstudiante/$id")
                            },
                            onNavigateToCreate = {
                                navController.navigate("editEstudiante/0")
                            }
                        )
                    }
                    composable("editEstudiante/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        EditEstudianteScreen(
                            estudianteId = id,
                            onNavigateBack = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}