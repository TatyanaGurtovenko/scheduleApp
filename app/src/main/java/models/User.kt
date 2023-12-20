package models

import com.google.gson.Gson

data class User(
    val user_id: Int,
    val user_name: String,
    val user_phone: String,
    val user_password: String
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
data class UserResponse(
    val data: List<User>
)