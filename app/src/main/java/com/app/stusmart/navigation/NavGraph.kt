package com.app.stusmart.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.stusmart.model.Student
import com.app.stusmart.screens.RoleSelectionScreen
import com.app.stusmart.screens.SplashScreen
import com.app.stusmart.screens.studentscreens.*
import com.app.stusmart.screens.teacherscreens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val studentList = remember { mutableStateListOf<Student>() }
    val allStudents = remember { mutableStateMapOf<String, MutableList<Student>>() }

    NavHost(navController = navController, startDestination = "splash") {
        // Splash + Role Selection
        composable("splash") {
            SplashScreen(onFinish = { navController.navigate("role_selection") })
        }
        composable("role_selection") {
            RoleSelectionScreen(
                onStudentClick = { navController.navigate("student_login") },
                onTeacherClick = { navController.navigate("teacher_login") }
            )
        }

        // ---------- TEACHER SCREENS ----------
        composable("teacher_login") {
            TeacherLoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("teacher_login") { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("teacher_register") }
            )
        }

        composable("home") {
            HomeScreen(
                onNavigate = { route ->
                    val targetRoute = when (route) {
                        "grades" -> "grade_overview"
                        else -> route
                    }
                    navController.navigate(targetRoute)
                },
                onLogout = {
                    navController.navigate("teacher_login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("school_profile") {
            SchoolProfileScreen()
        }

        composable("add_student") {
            AddStudentScreen(
                onBack = { navController.popBackStack() },
                onAddStudent = { navController.popBackStack() }
            )
        }

        composable("attendance") {
            AttendanceScreen(
                studentList = studentList,
                onBack = { navController.navigate("home") },
                onShowQR = { navController.navigate("qr_screen") }
            )
        }

        composable("grade_overview") {
            GradeOverviewScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() },
                onResultClick = { navController.navigate("enter_grades") }
            )
        }

        composable("enter_grades") {
            EnterGradesStudentScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() }
            )
        }

        composable("homework") {
            HomeWorkScreen(onBack = { navController.navigate("home") })
        }

        composable("timetable") {
            TimeTableScreen(onBack = { navController.navigate("home") })
        }

        composable("notification") {
            NotificationScreen(onBack = { navController.navigate("home") })
        }

        composable("qr_screen") {
            QRScreen(onBack = { navController.popBackStack() })
        }

        // ---------- STUDENT SCREENS ----------
        composable("student_login") {
            StudentLoginScreen(
                onLoginSuccess = {
                    navController.navigate("student_home") {
                        popUpTo("student_login") { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate("student_register") }
            )
        }

        composable("student_home") {
            StudentHomeScreen(
                onNavigate = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate("student_login") {
                        popUpTo("student_home") { inclusive = true }
                    }
                }
            )
        }

        composable("student_timetable") {
            StudentTimeTableScreen(onBack = { navController.navigate("student_home") })
        }

        composable("student_homework") {
            StudentHomeWorkScreen(onBack = { navController.navigate("student_home") })
        }

        composable("student_notification") {
            StudentNotificationScreen(onBack = { navController.navigate("student_home") })
        }

        composable("student_overview") {
            StudentOverviewScreen()
        }

        composable("student_info") {
            StudentOverviewScreen()
        }

        composable("student_profile") {
            StudentOverviewScreen()
        }

        composable("student_attendance") {
            StudentOverviewScreen()
        }

        composable("student_grades") {
            StudentOverviewScreen()
        }

        composable("student_register") {
            StudentLoginScreen(
                onLoginSuccess = {},
                onRegisterClick = {}
            )
        }
    }
}
