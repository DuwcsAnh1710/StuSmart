package com.app.stuteacher.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stuteacher.R

@Preview(showBackground = true, name = "AttendanceScreen Preview")
@Composable
fun AttendanceScreenPreview() {
    val sampleStudents = listOf(
        Student("Nguyen Van A"),
        Student("Tran Thi B"),
        Student("Le Van C")
    )
    AttendanceScreen(studentList = sampleStudents, onBack = {})
}
@Composable
fun AttendanceScreen(
    studentList: List<Student>,
    onBack: () -> Unit
) {
    val date = remember { "15/05/2025" } // Tạm cứng
    val className = remember { "12A1" } // Tạm cứng

    val attendanceState = remember {
        mutableStateListOf<Pair<String, AttendanceStatus>>()
    }

    // Khởi tạo nếu rỗng
    LaunchedEffect(studentList) {
        if (attendanceState.isEmpty()) {
            attendanceState.clear()
            attendanceState.addAll(studentList.map { it.name to AttendanceStatus() })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0057D8))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Bên trái: ảnh + dòng chữ
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_diem_danh),
                        contentDescription = "Attendance Icon",
                        modifier = Modifier.size(28.dp) // chỉnh kích thước tùy ý
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ĐIỂM DANH",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Bên phải: icon back hình "<"
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }


        // Thông tin lớp và ngày
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE6EBF4))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Class: $className", color = Color(0xFF0057D8), fontWeight = FontWeight.SemiBold)
            Text("Date: $date", color = Color(0xFF0057D8), fontWeight = FontWeight.SemiBold)
        }

        // Tiêu đề cột
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tên Học Sinh", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Có mặt", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(16.dp))
            Text("Vắng", fontWeight = FontWeight.Bold)
        }

        Divider(color = Color.LightGray)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(attendanceState) { (name, status) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = name, modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = status.present,
                        onCheckedChange = {
                            status.present = it
                            if (it) status.absent = false
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Checkbox(
                        checked = status.absent,
                        onCheckedChange = {
                            status.absent = it
                            if (it) status.present = false
                        }
                    )
                }
                Divider()
            }
        }

        Button(
            onClick = { /* Submit attendance */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057D8))
        ) {
            Text("Điểm Danh", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

class AttendanceStatus(
    var present: Boolean = false,
    var absent: Boolean = false
)



data class Student(
    val name: String
)
