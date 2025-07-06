package com.app.stusmart.screens.teacherscreens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.app.stusmart.R
@Preview(showBackground = true, name = "TimeTableScreen Preview")
@Composable
fun TimeTableScreenPreview() {
    TimeTableScreen(onBack = {})
}
@Composable
fun TimeTableScreen(
    onBack: () -> Unit
) {
    val daysOfWeek = listOf("2", "3", "4", "5", "6", "7")
    var selectedDayIndex by remember { mutableStateOf(0) }

    val mockSchedule = mapOf(
        0 to listOf("12A1", "12A1", "12A4", "12A5", "12A5"),
        1 to listOf("12A2", "12A2", "12A4", "12A4", "12A5"),
        2 to listOf("12A3", "12A3", "12A1", "12A1", "12A5"),
        3 to listOf("12A1", "12A2", "12A3", "12A4", "12A5"),
        4 to listOf("12A5", "12A1", "12A2", "12A3", "12A4"),
        5 to listOf("12A1", "12A1", "12A1", "12A1", "12A1")

    )

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_tkb),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "THỜI KHÓA BIỂU",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Day selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            daysOfWeek.forEachIndexed { index, day ->
                Button(
                    onClick = { selectedDayIndex = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDayIndex == index) Color(0xFF0057D8) else Color.LightGray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(day)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Schedule for selected day
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val schedule = mockSchedule[selectedDayIndex] ?: emptyList()
            itemsIndexed(schedule) { index, clazz ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("TIẾT ${String.format("%02d", index + 1)}", color = Color(0xFF0057D8), fontWeight = FontWeight.Bold)
                        Text(clazz, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057D8))
        ) {
            Text("Quay lại", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
