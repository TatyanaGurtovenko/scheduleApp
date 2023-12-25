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
        fun fromJson(json: String): Schedule {
            return Gson().fromJson(json, Schedule::class.java)
        }
    }
}
data class ScheduleResponse(
    val data: List<Schedule>
)