

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
            throw ChatNotFoundException("Нет чата с ID $id")
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
            return mutableListOf(
                Message(messageCounter + 1, 0, 0, 0, "Нет сообщений", false)
            )
        }
        var lastMessageList = mutableListOf<Message>()
        for (chat in chatList) {
            lastMessageList.add(
                messageList.findLast {message: Message ->
                    chat.id == message.chatId
                } ?: Message(messageCounter + 1, 0, 0, 0, "Нет сообщений", false))
        }
        // такой вариант считается небезопасным в случае пустого массива
        //        return lastMessageList as MutableList<Message>
        return lastMessageList
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

    fun getMessageById(id: Int, count: Int): List<Message> {
        val allMessageById = messageList.filter{message: Message -> message.senderId == id}
        val messageToGet = allMessageById.filterIndexed(){index, message: Message -> index < count}
        //тут я тоже не знаю, как перебрать лямбдой все элементы(ещё и условие, что только логическое выражение)
        for (message in messageToGet) {
            message.isRead = true
        }
        return messageToGet
    }

    fun clear() {
        chatList.clear()
        messageList.clear()
        messageCounter = 0
        chatCounter = 0
    }

}