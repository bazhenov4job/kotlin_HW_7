

object ChatService {
    var chatList = mutableListOf<Chat>()
    var messageList = mutableListOf<Message>()
    var messageCounter = 0
    var chatCounter = 0

    fun createMessage(chatId: Int, senderId: Int, receiverId: Int, text: String, isRead: Boolean): Int {
        messageCounter += 1
        var chatCounter = chatId
        // вот тут проверяем есть ли чат с такими пользователями
        // если нет - создаём
        // используем Anonymous function
        if (chatList.filter(fun(chat: Chat) = (receiverId == chat.guestId) || (receiverId == chat.ownerId)).isEmpty()) {
            chatCounter = createChat(senderId, receiverId)
        }
        messageList.add(Message(messageCounter, chatCounter, senderId, receiverId, text, isRead))
        return messageCounter
    }

    fun createChat(ownerId: Int, guestId: Int): Int {
        chatCounter += 1
        chatList.add(Chat(chatCounter, ownerId, guestId))
        return chatCounter
    }

    fun deleteMessage(id: Int): Int {
        val messageToDelete = messageList.filter(fun(message: Message) = (message.id == id))
        if (messageToDelete.isEmpty()) {
            // сюда можно добавить исключение
            return 0
        }
        messageList -= messageToDelete.toSet()
        return 1
    }

    fun deleteChat(id: Int): Int {
        val chatToDelete = chatList.filter(fun(chat: Chat) = (chat.id == id))
        if (chatToDelete.isEmpty()) {
            return 0
        }
        chatList -= chatToDelete.toSet()
        //находим все сообщения, которые не содержат id удаляемого чата и заменяем содежимое списка сообщений
        messageList = messageList.filter(fun(message: Message) = (message.chatId != id)) as MutableList<Message>
        return 1
    }

    fun getChats(): MutableList<Chat> {
        return chatList
    }

    fun getLastMessages(): MutableList<Message> {
        if (chatList.isEmpty()) {
            println("нет сообщений")
            return messageList
        }
        var lastMessageList = mutableListOf<Message?>()
        for (chat in chatList) {
            lastMessageList.add(messageList.findLast {message: Message -> chat.id == message.chatId})
        }
        return lastMessageList as MutableList<Message>
    }

    fun getUnreadChatsCount(): Int {
        val unreadMessageList = messageList.filter{message: Message -> !message.isRead}
        var uniqueChatId = mutableListOf<Int>()
        // понятия не имею, как согласно заданию забрать уникальные элементы из списка
        for(message in unreadMessageList) {
            if (message.chatId !in uniqueChatId) {
                uniqueChatId += message.chatId
            }
        }
        return uniqueChatId.size
    }

    fun clear() {
        chatList.clear()
        messageList.clear()
        messageCounter = 0
        chatCounter = 0
    }

}