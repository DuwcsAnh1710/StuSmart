package com.app.stusmart.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.stusmart.screens.teacherscreens.AddStudentScreen
import com.app.stusmart.screens.teacherscreens.AttendanceScreen
import com.app.stusmart.screens.teacherscreens.EnterGradesStudentScreen
import com.app.stusmart.screens.teacherscreens.GradeOverviewScreen
import com.app.stusmart.screens.teacherscreens.HomeScreen
import com.app.stusmart.screens.teacherscreens.HomeWorkScreen
import com.app.stusmart.screens.teacherscreens.NotificationScreen
import com.app.stusmart.screens.teacherscreens.QRScreen
import com.app.stusmart.screens.teacherscreens.SchoolProfileScreen
import com.app.stusmart.screens.teacherscreens.Student
import com.app.stusmart.screens.teacherscreens.TeacherLoginScreen
import com.app.stusmart.screens.teacherscreens.TimeTableScreen
import com.app.stusmart.screens.studentscreens.StudentLoginScreen
import com.app.stusmart.screens.SplashScreen
import com.app.stusmart.screens.RoleSelectionScreen
import com.app.stusmart.screens.studentscreens.StudentHomeScreen
import com.app.stusmart.screens.studentscreens.StudentHomeWorkScreen
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val studentList = remember { mutableStateListOf<Student>() }
    val allStudents = remember { mutableStateMapOf<String, MutableList<Student>>() }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onFinish = { navController.navigate("role_selection") })
        }
        composable("role_selection") {
            RoleSelectionScreen(
                onStudentClick = { navController.navigate("student_login") },
                onTeacherClick = { navController.navigate("teacher_login") }
            )
        }
        // Teacher Login Screen
        composable("teacher_login") {
            TeacherLoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("teacher_login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("teacher_register")
                }
            )
        }


        // Student Login Screen
        composable("student_login") {
            StudentLoginScreen(
                onLoginSuccess = {
                    navController.navigate("student_home") {
                        popUpTo("student_login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("student_register")
                }
            )
        }


        // Teacher Home Screen
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
                    navController.navigate("teacher_login") {
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

        // Teacher Attendance
        composable("attendance") {
            AttendanceScreen(
                studentList = listOf(
                    Student("Nguyen Van A"),
                    Student("Tran Thi B"),
                    Student("Le Van C")
                ),
                onBack = { navController.navigate("home") },
                onShowQR = { navController.navigate("qr_screen") }
            )
        }


        // Teacher Grade Overview → danh sách bài test
        composable("grade_overview") {
            GradeOverviewScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() },
                onResultClick = {
                    navController.navigate("enter_grades")
                }
            )
        }

        // Teacher Enter Grades → nhập điểm từng học sinh
        composable("enter_grades") {
            EnterGradesStudentScreen(
                allStudents = allStudents,
                onBack = { navController.popBackStack() }
            )
        }
        // Teacher Homework
        composable("homework") {
            HomeWorkScreen(
                onBack = { navController.navigate("home") }
            )
        }
        
        // Teacher Timetable
        composable("timetable") {
            TimeTableScreen (
                onBack = { navController.navigate("home") }
            )
        }

        // Teacher Notification
        composable("notification"){
            NotificationScreen(
                onBack = { navController.navigate("home") }
            )
        }
        
        // Teacher QR Screen
        composable("qr_screen") {
            QRScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Student Home Screen
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
        // Student Attendance
        composable("student_attendance") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Student Attendance Screen", fontSize = 24.sp)
            }
        }
        // Student Homework
        composable("student_homework") {
            StudentHomeWorkScreen(
                onBack = { navController.navigate("student_home") }
            )
        }
        // Student Grades
        composable("student_grades") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Student Grades Screen", fontSize = 24.sp)
            }
        }
        // Student Timetable
        composable("student_timetable") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Student Timetable Screen", fontSize = 24.sp)
            }
        }
        // Student Info
        composable("student_info") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Student Info Screen", fontSize = 24.sp)
            }
        }
        // Student Notification
        composable("student_notification") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Student Notification Screen", fontSize = 24.sp)
            }
        }

        // Student Register Screen (placeholder)
        composable("student_register") {
            StudentLoginScreen(
                onLoginSuccess = { },
                onRegisterClick = { }
            )
        }
    }
}
