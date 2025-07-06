package com.app.stusmart.screens.teacherscreens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stusmart.R
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, name = "GradeOverviewScreen Preview")
@Composable
fun GradeOverviewScreenPreview() {
    val sampleStudents = mapOf(
        "10A1" to listOf(Student("Nguyen Van A"), Student("Tran Thi B")),
        "10A2" to listOf(Student("Le Van C"), Student("Pham Thi D"))
    )
    GradeOverviewScreen(
        onBack = {},
        allStudents = sampleStudents,
        onResultClick = {}
    )
}
@Composable
fun GradeOverviewScreen(
    onBack: () -> Unit,
    allStudents: Map<String, List<Student>>,
    onResultClick: () -> Unit
) {
    val tests = List(5) { "Bài Test ${it + 1}" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFF0057D8)).padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_diem),
                        contentDescription = "Grades Icon",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("KẾT QUẢ HỌC TẬP", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            items(tests.size) { index ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("${tests[index]}", fontWeight = FontWeight.SemiBold, color = Color(0xFF0057D8))
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.LightGray, RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Kết Quả",
                            color = Color(0xFF0057D8),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.End).clickable { onResultClick() }
                        )
                    }
                }
            }
        }
    }
}