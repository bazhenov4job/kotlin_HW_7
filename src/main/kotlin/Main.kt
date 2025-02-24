package ru.netology

import ChatService

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val myList = mutableListOf(1, 2, 3, 6, 7, 4, 8)
    // используем ссылку на функцию для передачи предиката в filter
    val filterOption = ::moreThanSix
    val filteredList = myList.filter(filterOption)
    //println(filteredList)
    //Ламбда-реализация
    val filteredList2 = myList.filter lambda@{number: Int ->
        return@lambda number < 4
    }
    //println(filteredList2)

    ChatService.createMessage(1, 1, 2, "Привет! Как дела?", false)
    ChatService.createMessage(1, 2, 1, "Привет! А у тебя?", true)
    ChatService.createMessage(2, 1, 3, "Привет! Что нового?", true)
    ChatService.createMessage(2, 3, 1, "Привет! Последнее 2?", true)
    ChatService.createMessage(3, 1, 4, "Привет! Что нового?", true)
    ChatService.createMessage(3, 4, 1, "Привет! Что нового?", true)
    ChatService.createMessage(3, 5, 1, "Привет! Последнее 3?", false)
    ChatService.createMessage(4, 1, 5, "Привет! Последнее 4?", true)
    println(ChatService.getUnreadChatsCount())
    println(ChatService.getChats())
    println(ChatService.getLastMessages())

}

fun moreThanSix(number: Int): Boolean{
    return number > 6
}