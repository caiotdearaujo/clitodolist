package todolist

import errors.TaskNotFoundException
import kotlinx.serialization.json.Json
import serializers.ToDoListSerializer
import java.io.File
import java.io.FileNotFoundException

class ToDoList {
    var tasks = mutableListOf<Task>()

    fun listTask(id: Int) {
        println(tasks.getOrNull(id) ?: throw TaskNotFoundException(id))
    }

    fun listTasks() {
        tasks.forEachIndexed { id, task -> println("ID: $id | $task") }
    }

    fun listTasks(status: Status) {
        tasks.mapIndexed { index, task -> index to task }.filter { (_, task) -> task.status == status }
            .forEach { (id, task) -> println("ID: $id | $task") }
    }

    fun addTask(description: String) {
        tasks.add(Task(description))
        val id = tasks.size - 1

        println("Task added successfully. (ID: $id)")
    }

    fun updateTask(id: Int, description: String) {
        try {
            tasks[id].description = description

            println("Task updated successfully.")
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(id)
        }
    }

    fun updateTaskStatus(id: Int, status: Status) {
        try {
            tasks[id].status = status

            println("Task successfully marked as ${status.toFormattedString()}.")
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(id)
        }
    }

    fun deleteTask(taskId: Int) {
        try {
            tasks.removeAt(taskId)

            println("Task deleted successfully. (Alert: the identifiers of the tasks after it were changed!)")
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(taskId)
        }
    }

    fun clear() = tasks.clear()

    fun write() {
        val jsonString = Json.encodeToString(ToDoListSerializer, this)
        val file = File(filePath)

        file.parentFile?.mkdirs() // Create program directory if it doesn't exist
        file.writeText(jsonString)
    }

    companion object {
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
    }
}