package com.app.stuteacher.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.stuteacher.screens.AddStudentScreen
import com.app.stuteacher.screens.AttendanceScreen
import com.app.stuteacher.screens.EnterGradesStudentScreen
import com.app.stuteacher.screens.GradeOverviewScreen
import com.app.stuteacher.screens.HomeScreen
import com.app.stuteacher.screens.HomeWorkScreen
import com.app.stuteacher.screens.NotificationScreen
import com.app.stuteacher.screens.SchoolProfileScreen
import com.app.stuteacher.screens.Student
import com.app.stuteacher.screens.TeacherLoginScreen
import com.app.stuteacher.screens.TimeTableScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    val studentList = remember { mutableStateListOf<Student>() }
    val allStudents = remember { mutableStateMapOf<String, MutableList<Student>>() }

    NavHost(navController = navController, startDestination = "login") {

        // Login Screen
        composable("login") {
            TeacherLoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable("home") {
            HomeScreen(
                onNavigate = { route ->
                    // Điều hướng riêng cho "grades"
                    val targetRoute = when (route) {
                        "grades" -> "grade_overview"
                        else -> route
                    }
                    navController.navigate(targetRoute)
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        // School Profile
        composable("school_profile") {
            SchoolProfileScreen()
        }

        // Add Student
        composable("add_student") {
            AddStudentScreen(
                onBack = { navController.popBackStack() },
                onAddStudent = {
                    navController.popBackStack()
                }
            )
        }

        // Attendance
        composable("attendance") {
            AttendanceScreen(
                studentList = studentList,
                onBack = { navController.navigate("home") }
            )
        }

        // Grade Overview → danh sách bài test
        composable("grade_overview") {
            GradeOverviewScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() },
                onResultClick = {
                    navController.navigate("enter_grades")
                }
            )
        }

        // Enter Grades → nhập điểm từng học sinh
        composable("enter_grades") {
            EnterGradesStudentScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() }
            )
        }
        composable("homework") {
            HomeWorkScreen(
                onBack = { navController.navigate("home") }
            )
        }
        composable("timetable") {
            TimeTableScreen (
                onBack = { navController.navigate("home") }
            )
        }

        composable("notification"){
            NotificationScreen(
                onBack = { navController.navigate("home") }
            )
        }


    }
}
