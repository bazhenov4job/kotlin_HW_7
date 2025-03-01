

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
        var returnCode = 1
        val messageToDelete = messageList.asSequence()
            .filter(fun(message: Message) = (message.id == id))
        if (messageToDelete.toList().isEmpty()) {
            // сюда можно добавить исключение
            return 0
        }
        messageList -= messageToDelete.toSet()
        return 1
    }

    fun deleteChat(id: Int): Int {
        val chatToDelete = chatList
            .filter(fun(chat: Chat) = (chat.id == id))
            .ifEmpty { throw ChatNotFoundException("Нет чата с ID $id") }
        chatList -= chatToDelete.toSet()
        //находим все сообщения, которые не содержат id удаляемого чата и заменяем содежимое списка сообщений
        messageList = messageList.filter(fun(message: Message) = (message.chatId != id)) as MutableList<Message>
        return 1
    }

    fun getChats(): MutableList<Chat> {
        return chatList
    }

    fun getLastMessages(): MutableList<Message> {

        var lastMessageList = messageList.asSequence()
            .groupBy { it.chatId }
            .map {it.value.last()}
            .ifEmpty { Message(messageCounter + 1, 0, 0, 0, "Нет сообщений", false) }

        return lastMessageList as MutableList<Message>
    }

    fun getUnreadChatsCount(): Int {
        val unreadMessageCount = messageList.asSequence()
            .filter{message: Message -> !message.isRead}
            .map {message: Message ->  message.chatId}
            .distinct()
            .count()

        return unreadMessageCount
    }

    fun getMessageById(id: Int, count: Int): List<Message> {

        val messageToGet = messageList.asSequence()
            .filter{ message: Message -> message.senderId == id}
            .take(count)
            // в отличие от forEach выполняет преобразование НАД элементом, а не меняет его тип
            .onEach {message: Message ->  message.isRead = true}

        return messageToGet.toList()
    }

    fun clear() {
        chatList.clear()
        messageList.clear()
        messageCounter = 0
        chatCounter = 0
    }

}