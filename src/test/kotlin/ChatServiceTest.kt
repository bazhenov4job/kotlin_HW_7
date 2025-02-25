import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Before
    fun setUp() {
        ChatService.clear()
    }

    @Test
    fun createMessage() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        val result = ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        assertEquals(2,result)
    }

    @Test
    fun createChat() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        val result = ChatService.chatList.last().id
        assertEquals(1,result)
    }

    @Test
    fun deleteMessageFound() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        val result = ChatService.deleteMessage(2)
        assertEquals(1, result)
    }

    @Test
    fun deleteMessageNotFound() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        val result = ChatService.deleteMessage(3)
        assertEquals(0, result)
    }

    @Test
    fun deleteChat() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        ChatService.createMessage(2, 2, 3, "Привет! Что нового?", false)
        val result = ChatService.deleteChat(1)
        assertEquals(1,result)
    }

    @Test
    fun getLastMessages() {
        val listToCompare = mutableListOf(
            Message(2,1, 2, 1, "Привет! А у тебя?", false),
            Message(4, 2, 3, 1, "Привет! Последнее 2?", false),
            Message(7, 3, 5, 1, "Привет! Последнее 3?", false),
            Message(8, 4, 1, 5, "Привет! Последнее 4?", false)
        )
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", false)
        ChatService.createMessage(2, 1, 3, "Привет! Что нового?", false)
        ChatService.createMessage(2, 3, 1, "Привет! Последнее 2?", false)
        ChatService.createMessage(3, 1, 4, "Привет! Что нового?", false)
        ChatService.createMessage(3, 4, 1, "Привет! Что нового?", false)
        ChatService.createMessage(3, 5, 1, "Привет! Последнее 3?", false)
        ChatService.createMessage(4, 1, 5, "Привет! Последнее 4?", false)
        val result = ChatService.getLastMessages()

        assertEquals(listToCompare, result)
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", true)
        ChatService.createMessage(2, 1, 3, "Привет! Что нового?", true)
        ChatService.createMessage(2, 3, 1, "Привет! Последнее 2?", true)
        ChatService.createMessage(3, 1, 4, "Привет! Что нового?", true)
        ChatService.createMessage(3, 4, 1, "Привет! Что нового?", true)
        ChatService.createMessage(3, 5, 1, "Привет! Последнее 3?", false)
        ChatService.createMessage(4, 1, 5, "Привет! Последнее 4?", true)

        val result = ChatService.getUnreadChatsCount()
        assertEquals(2, result)
    }

    @Test
    fun getMessageById() {
        val listToCompare = listOf(
            Message(6, 3, 4, 1, "Привет! Что нового1?", true),
            Message(7, 3, 4, 1, "Привет! Что нового2?", true),
            Message(8, 3, 4, 1, "Привет! Что нового3?", true)
        )
        ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
        ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", true)
        ChatService.createMessage(2, 1, 3, "Привет! Что нового?", true)
        ChatService.createMessage(2, 3, 1, "Привет! Последнее 2?", true)
        ChatService.createMessage(3, 1, 4, "Привет! Что нового?", false)
        ChatService.createMessage(3, 4, 1, "Привет! Что нового1?", false)
        ChatService.createMessage(3, 4, 1, "Привет! Что нового2?", false)
        ChatService.createMessage(3, 4, 1, "Привет! Что нового3?", true)
        ChatService.createMessage(3, 5, 1, "Привет! Последнее 3?", false)
        ChatService.createMessage(4, 1, 5, "Привет! Последнее 4?", true)

        val result = ChatService.getMessageById(4, 3)

        assertEquals(listToCompare, result)
    }
}