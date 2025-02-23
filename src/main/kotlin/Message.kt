

data class Message (
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val receiverId: Int,
    val text: String,
    val isRead: Boolean
) {
}