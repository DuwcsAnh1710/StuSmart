package com.app.stusmart.util

import android.content.Context
import com.app.stusmart.screens.teacherscreens.AttendanceStatus
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object AttendancePrefs {
    fun saveAttendanceState(context: Context, attendanceState: Map<String, AttendanceStatus>, date: LocalDate) {
        val prefs = context.getSharedPreferences("attendance_prefs", Context.MODE_PRIVATE)
        val json = Gson().toJson(attendanceState)
        val key = "attendance_state_${date.format(DateTimeFormatter.ISO_DATE)}"
        prefs.edit().putString(key, json).apply()
    }

    fun loadAttendanceState(context: Context, date: LocalDate): Map<String, AttendanceStatus> {
        val prefs = context.getSharedPreferences("attendance_prefs", Context.MODE_PRIVATE)
        val key = "attendance_state_${date.format(DateTimeFormatter.ISO_DATE)}"
        val json = prefs.getString(key, null)
        return if (json != null) {
            val type = object : com.google.gson.reflect.TypeToken<Map<String, AttendanceStatus>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyMap()
        }
    }
}