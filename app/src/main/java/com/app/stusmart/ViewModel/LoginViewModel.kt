package com.app.stusmart.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stusmart.model.*
import com.app.stusmart.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class LoginViewModel : ViewModel() {
    private val _studentLoginResult = MutableStateFlow<Student?>(null)
    val studentLoginResult: StateFlow<Student?> = _studentLoginResult

    private val _teacherLoginResult = MutableStateFlow<Teacher?>(null)
    val teacherLoginResult: StateFlow<Teacher?> = _teacherLoginResult

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loginStudent(username: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Gửi request đăng nhập: $username, $password")
                val student = RetrofitInstance.authApi.studentLogin(StudentLoginRequest(username, password))
                Log.d("LoginViewModel", "Kết quả đăng nhập: $student")
                _studentLoginResult.value = student
                _error.value = null
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Lỗi đăng nhập: ${e.message}")
                _error.value = "Sai tài khoản hoặc mật khẩu"
            }
        }
    }

    fun loginTeacher(gmail: String, password: String) {
        viewModelScope.launch {
            try {
                val teacher = RetrofitInstance.authApi.teacherLogin(TeacherLoginRequest(gmail, password))
                _teacherLoginResult.value = teacher
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Sai tài khoản hoặc mật khẩu!"
            }
        }
    }
}