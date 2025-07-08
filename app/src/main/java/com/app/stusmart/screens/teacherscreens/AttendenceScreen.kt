package com.app.stusmart.screens.teacherscreens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stusmart.screens.teacherscreens.Student

@Composable
fun AttendanceScreen(
    studentList: List<Student>,
    onBack: () -> Unit,
    onShowQR: () -> Unit
) {
    val className = remember { "12A1" }
    val date = remember { "15/05/2025" }
    val attendanceState = remember {
        mutableStateMapOf<String, AttendanceStatus>().apply {
            studentList.forEach { put(it.name, AttendanceStatus()) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0057D8))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "ĐIỂM DANH",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
        // Class & Date
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
        // Table header
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
        // Student list
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(studentList) { student ->
                val status = attendanceState[student.name] ?: AttendanceStatus()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = student.name, modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = status.present,
                        onCheckedChange = {
                            attendanceState[student.name] = status.copy(present = it, absent = if (it) false else status.absent)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Checkbox(
                        checked = status.absent,
                        onCheckedChange = {
                            attendanceState[student.name] = status.copy(absent = it, present = if (it) false else status.present)
                        }
                    )
                }
                Divider()
            }
        }
        // QR Button
        Button(
            onClick = onShowQR,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057D8))
        ) {
            Text("Điểm Danh (QR)", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

data class AttendanceStatus(val present: Boolean = false, val absent: Boolean = false)
