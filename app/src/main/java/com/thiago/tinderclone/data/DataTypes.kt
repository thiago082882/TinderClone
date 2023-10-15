package com.thiago.tinderclone.data

// Classe de dados que representa informações do usuário
data class UserData(
    var userId: String? = "",
    var name: String? = "",
    var username: String? = "",
    var imageUrl: String? = "",
    var bio: String? = "",
    var gender: String? = "",
    var genderPreference: String? = "",
    var swipesLeft: List<String> = listOf(),
    var swipesRight: List<String> = listOf(),
    var matches: List<String> = listOf()
) {
    // Função para converter os dados do usuário em um mapa (útil para armazenamento em banco de dados)
    fun toMap() = mapOf(
        "userId" to userId,
        "name" to name,
        "username" to username,
        "imageUrl" to imageUrl,
        "bio" to bio,
        "gender" to gender,
        "genderPreference" to genderPreference,
        "swipesLeft" to swipesLeft,
        "swipesRight" to swipesRight,
        "matches" to matches
    )
}

// Classe de dados que representa informações de um chat
data class ChatData(
    var chatId: String? = "",
    var user1: ChatUser = ChatUser(),
    var user2: ChatUser = ChatUser()
)

// Classe de dados que representa informações de um usuário em um chat
data class ChatUser(
    var userId: String? = "",
    var name: String? = "",
    var imageUrl: String? = ""
)

// Classe de dados que representa uma mensagem
data class Message(
    val sentBy: String? = null,
    val message: String? = null,
    val timestamp: String? = null
)

