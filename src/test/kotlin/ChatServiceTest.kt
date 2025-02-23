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
}