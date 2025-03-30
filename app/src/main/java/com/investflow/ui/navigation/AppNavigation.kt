package com.investflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.investflow.ui.screens.home.HomeScreen
import com.investflow.ui.screens.projects.CreateProjectScreen
import com.investflow.ui.screens.projects.ProjectDetailsScreen
import com.investflow.ui.screens.projects.ProjectsListScreen
import com.investflow.ui.screens.cashflow.CashFlowScreen
import com.investflow.ui.screens.settings.SettingsScreen

/**
 * Principais rotas de navegação do aplicativo
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProjectsList : Screen("projects_list")
    object CreateProject : Screen("create_project")
    object ProjectDetails : Screen("project_details/{projectId}") {
        fun createRoute(projectId: Long): String = "project_details/$projectId"
    }
    object CashFlow : Screen("cash_flow/{projectId}") {
        fun createRoute(projectId: Long): String = "cash_flow/$projectId"
    }
    object Settings : Screen("settings")
}

/**
 * Componente de navegação principal do aplicativo
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToProjects = { navController.navigate(Screen.ProjectsList.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        
        composable(Screen.ProjectsList.route) {
            ProjectsListScreen(
                onNavigateToCreateProject = { navController.navigate(Screen.CreateProject.route) },
                onNavigateToProjectDetails = { projectId ->
                    navController.navigate(Screen.ProjectDetails.createRoute(projectId))
                }
            )
        }
        
        composable(Screen.CreateProject.route) {
            CreateProjectScreen(
                onNavigateBack = { navController.popBackStack() },
                onProjectCreated = { projectId ->
                    navController.navigate(Screen.ProjectDetails.createRoute(projectId)) {
                        // Remove CreateProject da backstack após criação
                        popUpTo(Screen.ProjectsList.route)
                    }
                }
            )
        }
        
        composable(
            route = Screen.ProjectDetails.route,
            arguments = listOf(
                navArgument("projectId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: -1L
            ProjectDetailsScreen(
                projectId = projectId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCashFlow = { 
                    navController.navigate(Screen.CashFlow.createRoute(projectId))
                },
                onProjectDeleted = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.CashFlow.route,
            arguments = listOf(
                navArgument("projectId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getLong("projectId") ?: -1L
            CashFlowScreen(
                projectId = projectId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}