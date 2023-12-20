package models

import com.google.gson.Gson


data class Schedule(
    val GroupName: String,
    val UserName: String,
    val Teacher: String,
    val Subject: String,
    val Weekday: Int,
    val LessonNumber: Int,
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): User {
            return Gson().fromJson(json, User::class.java)
        }
    }
}
data class ScheduleResponse(
    val data: List<Schedule>
)