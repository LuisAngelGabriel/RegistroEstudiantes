package edu.ucne.registroestudiantes.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registroestudiantes.presentation.asignatura.edit.AsignaturaScreen
import edu.ucne.registroestudiantes.presentation.asignatura.list.AsignaturaListScreen
import edu.ucne.registroestudiantes.presentation.estudiante.list.EstudianteListScreen
import edu.ucne.registroestudiantes.presentation.estudiante.edit.EditEstudianteScreen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                EstudianteListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    goToEstudiante = { id -> navHostController.navigate(Screen.Estudiante(id)) },
                    createEstudiante = { navHostController.navigate(Screen.Estudiante(0)) }
                )
            }

            composable<Screen.Estudiante> {
                val args = it.toRoute<Screen.Estudiante>()
                EditEstudianteScreen(
                    estudianteId = args.estudianteId,
                    onNavigateBack = { navHostController.navigateUp() }
                )
            }

            composable<Screen.AsignaturaList> {
                AsignaturaListScreen(
                    onDrawer = { scope.launch { drawerState.open() } },
                    goToAsignatura = { id -> navHostController.navigate(Screen.Asignatura(id)) },
                    createAsignatura = { navHostController.navigate(Screen.Asignatura(0)) }
                )
            }

            composable<Screen.Asignatura> {
                val args = it.toRoute<Screen.Asignatura>()
                AsignaturaScreen(
                    asignaturaId = args.asignaturaId,
                    goBack = { navHostController.navigateUp() },
                    onDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
    }
}