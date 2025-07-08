package com.app.stusmart.network

import com.app.stusmart.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("api/students/login")
    suspend fun studentLogin(@Body request: StudentLoginRequest): Student
    @GET("api/students")
    suspend fun getStudents(): List<Student>
    @POST("api/teachers/login")
    suspend fun teacherLogin(@Body request: TeacherLoginRequest): Teacher
}