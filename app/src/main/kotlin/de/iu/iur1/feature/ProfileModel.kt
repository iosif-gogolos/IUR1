package de.iu.iur1.profile

data class ProfilePopularList(
    val name: String,
    val description: String,
    val star: String,
    val language: String

)

data class ImageTextList(
    val icon: IUR1Icon,
    val text: String
)
