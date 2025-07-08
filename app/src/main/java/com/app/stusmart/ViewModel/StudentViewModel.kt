package com.app.stusmart.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stusmart.model.AddStudentRequest
import com.app.stusmart.model.Student
import com.app.stusmart.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    private val _addStudentResult = MutableStateFlow<Student?>(null)
    val addStudentResult: StateFlow<Student?> = _addStudentResult

    private val _addStudentError = MutableStateFlow<String?>(null)
    val addStudentError: StateFlow<String?> = _addStudentError

    fun fetchStudents() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.authApi.getStudents()
                _students.value = result
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
            }
        }
    }

    fun addStudent(request: AddStudentRequest) {
        viewModelScope.launch {
            try {
                val student = RetrofitInstance.authApi.addStudent(request)
                _addStudentResult.value = student
                _addStudentError.value = null
            } catch (e: Exception) {
                _addStudentError.value = "Lỗi thêm học sinh: ${e.message}"
            }
        }
    }
}