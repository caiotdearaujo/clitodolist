package todolist

import errors.MissingArgumentError
import errors.TaskNotFoundException

class ToDoList {
    var tasks = mutableListOf<Task>()

    fun listTask(id: Int) {
        println(tasks.getOrNull(id) ?: throw MissingArgumentError("id"))
    }

    fun listTasks() {
        tasks.forEachIndexed { id, task -> println("ID: $id | $task") }
    }

    fun listTasks(status: Status) {
        tasks
            .filter { it.status == status }
            .forEachIndexed { id, task -> println("ID: $id | $task") }
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
}