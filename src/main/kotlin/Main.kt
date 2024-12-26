import errors.ArgumentFormatException
import errors.MissingArgumentError
import errors.TaskNotFoundException
import errors.ViolatedFileException
import kotlinx.serialization.json.Json
import serializers.ToDoListSerializer
import java.io.File
import java.io.FileNotFoundException

val filePath = "${System.getProperty("user.home")}/clitodolist/tasks.json" // Storage path
const val version = "0.1.0" // Program version

fun main(args: Array<String>) {
    var toDoList = accessOrInitializeToDoList() ?: return // If null is returned, break the program

    try {
        when (args.getOrNull(0)) {
            "list" -> {
                val arg2 = args.getOrNull(1)

                if (arg2 == null) {
                    // Print all tasks with id and info
                    toDoList.tasks.forEachIndexed { id, task -> println("ID: $id | $task") }
                } else if (arg2.matches("^[0-9]+$".toRegex())) {
                    val id = arg2.toInt()
                    val task = toDoList.tasks.getOrNull(id) ?: throw TaskNotFoundException(id)

                    // Print a specific task info, without its provided id to avoid redundancy
                    println(task)
                } else {
                    val status = when (arg2) {
                        "todo" -> Status.TODO
                        "in-progress" -> Status.IN_PROGRESS
                        "done" -> Status.DONE
                        else -> throw ArgumentFormatException("status", "todo | in-progress | done")
                    }

                    // Print all tasks id and info, filtered by its status
                    toDoList.tasks.filter { it.status == status }
                        .forEachIndexed { id, task -> println("ID: $id | $task") }
                }
            }

            "add" -> {
                val description = args.getOrNull(1) ?: throw MissingArgumentError("description")
                val id = toDoList.addTask(description)

                println("Task added successfully. (ID: $id)")
            }

            "update" -> {
                val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
                val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

                val description = args.getOrNull(2) ?: throw MissingArgumentError("description")

                toDoList.updateTask(id, description)

                println("Task updated successfully.")
            }

            "mark-todo" -> {
                val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
                val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

                toDoList.updateTaskStatus(id, Status.TODO)

                println("Task successfully marked as to do.")
            }

            "mark-in-progress" -> {
                val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
                val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

                toDoList.updateTaskStatus(id, Status.IN_PROGRESS)

                println("Task successfully marked as in progress.")
            }

            "mark-done" -> {
                val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
                val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

                toDoList.updateTaskStatus(id, Status.DONE)

                println("Task successfully marked as done.")
            }

            "delete" -> {
                val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
                val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

                toDoList.deleteTask(id)

                println("Task deleted successfully. (Alert: the identifiers of the tasks after it were changed!)")
            }

            "version" -> {
                println("v$version")
            }

            "reset" -> {
                // Create a new empty to-do list and overwrite the potentially violated one
                toDoList = ToDoList()
            }

            null -> {
                println("CLI to-do list made in Kotlin.")
                println("Source code: https://github.com/caiotdearaujo/clitodolist")
                println("Version: $version")
            }

            else -> {
                println("Command ${args[0]} does not exist.")
            }
        }
    } catch (e: Exception) {
        when (e) {
            is MissingArgumentError,
            is TaskNotFoundException,
            is ArgumentFormatException,
            is IllegalArgumentException -> println(e.message)
            else -> throw e
        }
    }

    saveToDoList(toDoList)
}

fun accessOrInitializeToDoList(): ToDoList? {
    try {
        val jsonString = File(filePath).readText()
        val deserializedToDoList = Json.decodeFromString(ToDoListSerializer, jsonString)

        return deserializedToDoList
    } catch (e: FileNotFoundException) {
        // If no stored to-do list is found, a new one is created
        return ToDoList()
    } catch (e: ViolatedFileException) {
        // To recover the program, it is necessary to run `reset` command
        println(e.message)

        // Null breaks the program
        return null
    }
}

fun saveToDoList(toDoList: ToDoList) {
    val jsonString = Json.encodeToString(ToDoListSerializer, toDoList)
    val file = File(filePath)

    file.parentFile?.mkdirs() // Create program directory if it doesn't exist
    file.writeText(jsonString)
}