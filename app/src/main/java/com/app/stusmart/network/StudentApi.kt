package com.app.stusmart.network

import com.app.stusmart.model.Student
import retrofit2.http.GET

interface StudentApi {
    @GET("api/students")
    suspend fun getStudents(): List<Student>
}