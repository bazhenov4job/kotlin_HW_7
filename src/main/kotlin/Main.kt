package ru.netology

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val myList = mutableListOf(1, 2, 3, 6, 7, 4, 8)
    // используем ссылку на функцию для передачи предиката в filter
    val filterOption = ::moreThanSix
    val filteredList = myList.filter(filterOption)
    println(filteredList)
    //Ламбда-реализация
    val filteredList2 = myList.filter lambda@{number: Int ->
        return@lambda number < 4
    }
    println(filteredList2)
}

fun moreThanSix(number: Int): Boolean{
    return number > 6
}