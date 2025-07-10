package com.app.stusmart.screens.teacherscreens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.stusmart.ViewModel.AttendanceViewModel
import com.app.stusmart.model.Student
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.app.stusmart.model.AttendanceRecord
import androidx.compose.ui.tooling.preview.Preview
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAttendanceScreen() {
    val fakeStudents = listOf(
        Student(
            _id = "1",
            username = "hs001",
            password = "123456",
            className = "12A1",
            fullName = "Nguyễn Văn A",
            email = "a@email.com",
            birthDate = "2007-01-01",
            parentName = "Nguyễn Văn B",
            parentPhone = "0123456789",
            address = "Hà Nội"
        ),
        Student(
            _id = "2",
            username = "hs002",
            password = "123456",
            className = "12A1",
            fullName = "Trần Thị B",
            email = "b@email.com",
            birthDate = "2007-02-02",
            parentName = "Trần Văn C",
            parentPhone = "0987654321",
            address = "Hà Nội"
        )
    )
    AttendanceScreen(
        studentList = fakeStudents,
        className = "12A1",
        onClassChange = {},
        onBack = {},
        onShowQR = {},
        onSaveAttendance = {}
    )
}
@RequiresApi(Build.VERSION_CODES.O)
    @Composable
fun AttendanceScreen(
    studentList: List<Student>,
    className: String,
    onClassChange: (String) -> Unit,
    onBack: () -> Unit,
    onShowQR: () -> Unit,
    onSaveAttendance: (List<AttendanceRecord>) -> Unit
) {
    // Lấy ngày hiện tại
    val currentDate = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    // Danh sách lớp mẫu, bạn có thể lấy từ API nếu muốn động
    val classList = listOf("12A1", "12A2", "11A1", "11A2")
    var selectedClass by remember { mutableStateOf(className) }
    
    // Cập nhật danh sách học sinh khi đổi lớp
    LaunchedEffect(selectedClass) {
        onClassChange(selectedClass)
    }
    val attendanceState = remember {
        mutableStateMapOf<String, AttendanceStatus>().apply {
            studentList.forEach { put(it.username, AttendanceStatus()) }
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
        // Chọn lớp & ngày
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE6EBF4))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Dropdown chọn lớp
            var expanded by remember { mutableStateOf(false) }
            Box {
                TextButton(onClick = { expanded = true }) {
                    Text("Lớp: $selectedClass", color = Color(0xFF0057D8), fontWeight = FontWeight.SemiBold)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    classList.forEach { classItem ->
                        DropdownMenuItem(
                            text = { Text("Lớp $classItem") },
                            onClick = {
                                selectedClass = classItem
                                expanded = false
                                // Tự động lấy danh sách học sinh khi chọn lớp mới
                                onClassChange(classItem)
                            }
                        )
                    }
                }
            }
            Text("Ngày: $currentDate", color = Color(0xFF0057D8), fontWeight = FontWeight.SemiBold)
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
                val status = attendanceState[student.username] ?: AttendanceStatus()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = student.fullName, modifier = Modifier.weight(1f)) // Đổi sang tên
                    Checkbox(
                        checked = status.present,
                        onCheckedChange = {
                            attendanceState[student.username] = status.copy(present = it, absent = if (it) false else status.absent)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Checkbox(
                        checked = status.absent,
                        onCheckedChange = {
                            attendanceState[student.username] = status.copy(absent = it, present = if (it) false else status.present)
                        }
                    )
                }
                Divider()
            }
        }
        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Nút lưu điểm danh thủ công
            Button(
                onClick = {
                    // Lưu trạng thái điểm danh
                    val attendanceData = attendanceState.map { (username, status) ->
                        AttendanceRecord(
                            studentUsername = username,
                            className = selectedClass,
                            date = currentDate,
                            isPresent = status.present,
                            isAbsent = status.absent
                        )
                    }.filter { it.isPresent || it.isAbsent } // Chỉ lưu những học sinh đã được đánh dấu
                    
                    // Gọi hàm lưu điểm danh
                    onSaveAttendance(attendanceData)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057D8))
            ) {
                Text("Lưu Điểm Danh", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            
            // Nút điểm danh QR
            Button(
                onClick = onShowQR,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057D8))
            ) {
                Text("Điểm Danh (QR)", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

data class AttendanceStatus(val present: Boolean = false, val absent: Boolean = false)

data class AttendanceRecord(
    val studentUsername: String,
    val className: String,
    val date: String,
    val isPresent: Boolean,
    val isAbsent: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreenWrapper(
    initialClassName: String,
    onBack: () -> Unit,
    onShowQR: () -> Unit
) {
    val viewModel: AttendanceViewModel = viewModel()
    val students = viewModel.studentList
    var className by remember { mutableStateOf(initialClassName) }

    // Lấy danh sách học sinh khi vào màn hình lần đầu
    LaunchedEffect(Unit) {
        viewModel.fetchStudents(initialClassName)
    }

    AttendanceScreen(
        studentList = students,
        className = className,
        onClassChange = { newClass ->
            className = newClass
            // Gọi API lấy danh sách học sinh theo lớp mới
            viewModel.fetchStudents(newClass)
        },
        onBack = onBack,
        onShowQR = onShowQR,
        onSaveAttendance = { attendanceRecords ->
            // Gọi ViewModel để lưu điểm danh
            viewModel.saveAttendance(attendanceRecords)
        }
    )
}


