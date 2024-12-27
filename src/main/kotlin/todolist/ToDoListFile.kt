package todolist

import kotlinx.serialization.json.Json
import serializers.ToDoListSerializer
import java.io.File
import java.io.FileNotFoundException

object ToDoListFile {
    private val filePath = "${System.getProperty("user.home")}/clitodolist/tasks.json"

    fun accessOrInitialize(): ToDoList {
        try {
            val jsonString = File(filePath).readText()
            val deserializedToDoList = Json.decodeFromString(ToDoListSerializer, jsonString)

            return deserializedToDoList
        } catch (e: FileNotFoundException) {
            println("A to-do list file was not found. Creating a new one in $filePath.")

            return ToDoList()
        }
    }

    fun write(toDoList: ToDoList) {
        val jsonString = Json.encodeToString(ToDoListSerializer, toDoList)
        val file = File(filePath)

        file.parentFile?.mkdirs() // Create program directory if it doesn't exist
        file.writeText(jsonString)
    }
}