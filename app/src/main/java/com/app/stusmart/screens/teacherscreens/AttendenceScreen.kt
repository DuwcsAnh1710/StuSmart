package com.app.stusmart.screens.teacherscreens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stusmart.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreen(
    studentList: List<Student>,
    onBack: () -> Unit,
    onShowQR: () -> Unit
) {
    val date = remember {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        today.format(formatter)
    }
    val className = remember { "12A1" }

    val attendanceState = remember {
        mutableStateListOf<Pair<String, AttendanceStatus>>()
    }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_diem_danh),
                        contentDescription = "Attendance Icon",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ĐIỂM DANH",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }

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
            onClick = {
                val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                val db = Firebase.firestore
                val qrData = "$className-$today"
                val qrInfo = hashMapOf(
                    "createdAt" to System.currentTimeMillis(),
                    "qrData" to qrData
                )
                db.collection("qr_codes").document("today")
                    .set(qrInfo)
                    .addOnSuccessListener {
                        onShowQR()
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error saving QR", e)
                    }
            },
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

class AttendanceStatus(var present: Boolean = false, var absent: Boolean = false)

data class Student(val name: String)
