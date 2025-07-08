package com.app.stusmart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stusmart.model.Student
import com.app.stusmart.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    fun fetchStudents() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getStudents()
                _students.value = result
            } catch (e: Exception) {
                // Xử lý lỗi nếu cần
            }
        }
    }
}