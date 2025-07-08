package by.alerus.tms_android

import java.util.Date

data class User(
    var name: String = "User",
    var description: String = "Description",
    var avatarId: Int = R.drawable.ic_launcher_background,
    val birthDate: Date = Date()
)
