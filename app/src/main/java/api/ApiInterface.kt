package api

import models.ScheduleResponse
import models.User
import models.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/api/users")
    suspend fun getAllUsers(): UserResponse

    @GET("/api/schedule/{user_id}")
    suspend fun getSchedule(@Path("user_id") userId: Int): ScheduleResponse
}