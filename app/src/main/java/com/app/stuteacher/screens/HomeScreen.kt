package com.app.stuteacher.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stuteacher.R
import kotlinx.coroutines.launch

@Preview(showBackground = true, name = "HomeScreen Preview")
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            TeacherDrawer { selected ->
                scope.launch { drawerState.close() }
                when (selected) {
                    "profile" -> onNavigate("school_profile")
                    "info" -> onNavigate("teacher_profile")
                    "logout" -> showLogoutDialog = true
                }
            }
        }
    ) {
        // Giao diện chính
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F4FF)) // Màu nền sáng)
        ) {
            // Header xanh và avatar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(bottomStart = 150.dp, bottomEnd = 150.dp))
                    .background(Color(0xFF0057D8))
            ) {
                IconButton(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = 50.dp)
                        .border(5.dp, Color(0xFF0628AF), CircleShape)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.teacher_avatar),
                        contentDescription = "Avatar",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Khung lời chào
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(Color(0xFF0057D8), shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Chào mừng đến với StuSmart! ➜",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Ứng dụng quản lý dành cho giáo viên: điểm danh, bài tập, kết quả học tập, thời khóa biểu và nhiều hơn nữa.",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Danh sách các nút chức năng
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    FeatureItem("Điểm Danh", R.drawable.ic_diem_danh){
                        onNavigate("attendance")
                    }
                    FeatureItem("Bài Tập", R.drawable.ic_bai_tap){
                        onNavigate("homework")
                    }
                    FeatureItem("Kết Quả", R.drawable.ic_ket_qua){
                        onNavigate("grades")
                    }
                }
                Spacer(modifier = Modifier.height(70.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    FeatureItem("Thời Khoá Biểu", R.drawable.ic_tkb){
                        onNavigate("timetable")
                    }
                    FeatureItem("Thêm Học Sinh", R.drawable.ic_them_hs){
                        onNavigate("add_student")
                    }
                    FeatureItem("Thông Báo", R.drawable.ic_thong_bao){
                        onNavigate("notification")
                    }
                }
            }
        }

        // Hộp thoại xác nhận đăng xuất
        if (showLogoutDialog) {
            AlertDialog(
                containerColor = Color(0xFF0057D8),
                titleContentColor = Color.White,
                textContentColor = Color.White,
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Xác nhận đăng xuất") },
                text = { Text("Bạn có chắc chắn muốn đăng xuất không?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            onLogout()
                        }
                    ) {
                        Text("Xác nhận", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Huỷ", color = Color.White)
                    }
                }
            )
        }
    }
}

@Composable
fun FeatureItem(title: String, iconRes: Int, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(90.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFFd3e3fd), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color(0xFF0057D8),
            fontWeight = FontWeight.Medium
        )
    }
}

