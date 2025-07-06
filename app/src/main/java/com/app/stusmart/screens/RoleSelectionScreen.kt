package com.app.stusmart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.app.stusmart.R

@Preview(showBackground = true, name = "RoleSelectionScreen Preview")
@Composable
fun RoleSelectionScreenPreview() {
    RoleSelectionScreen(
        onStudentClick = {},
        onTeacherClick = {}
    )
}
@Composable
fun RoleSelectionScreen(
    onStudentClick: () -> Unit,
    onTeacherClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header bo cong
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(bottomStart = 150.dp, bottomEnd = 150.dp))
                .background(Color(0xFF0057D8)),
            contentAlignment = Alignment.BottomCenter

        ) {
            // Có thể thêm nút menu nếu cần
        }

        // Avatar nổi, đặt ngoài header, offset âm để nổi lên
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(y = (-90).dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.White, CircleShape)
                .border(6.dp, Color(0xFF0057D8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_stusmart),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("Sự lựa chọn của bạn", fontSize = 18.sp, color = Color(0xFF0057D8), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Học sinh
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onStudentClick() }
                    .background(Color(0xFF0057D8), shape = RoundedCornerShape(24.dp))
                    .size(90.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_student),
                    contentDescription = "Học sinh",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Học Sinh", color = Color.White, fontWeight = FontWeight.Bold)
            }
            // Giáo viên
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onTeacherClick() }
                    .background(Color(0xFF0057D8), shape = RoundedCornerShape(24.dp))
                    .size(90.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_teacher),
                    contentDescription = "Giáo viên",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Giáo Viên", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

