package util

import todolist.Status
import todolist.ToDoList
import errors.ArgumentFormatException
import errors.MissingArgumentError
import errors.TaskNotFoundException
import errors.ViolatedFileException
import help.Help
import version
import java.util.*

object CommandProcessor {
    fun process(args: Array<String>) {
        try {
            val toDoList = ToDoList.accessOrInitialize()

            when (args.getOrNull(0)?.lowercase(Locale.getDefault())) {
                "list" -> processList(args, toDoList)
                "add" -> processAdd(args, toDoList)
                "update" -> processUpdate(args, toDoList)
                "mark-todo", "mark-in-progress", "mark-done" -> processUpdateStatus(args, toDoList)
                "delete" -> processDelete(args, toDoList)
                "version" -> processVersion()
                "reset" -> processReset(toDoList)
                "help" -> processHelp(args)
                null -> processNoCommand()
                else -> processUnknownCommand(args)
            }

            toDoList.write()
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun processList(args: Array<String>, toDoList: ToDoList) {
        val argument = args.getOrNull(1)

        when {
            argument == null -> toDoList.listTasks()
            argument.matches("^[0-9]+$".toRegex()) -> toDoList.listTask(argument.toInt())
            else -> toDoList.listTasks(Status.fromString(argument))
        }
    }

    private fun processAdd(args: Array<String>, toDoList: ToDoList) {
        val description = args.getOrNull(1) ?: throw MissingArgumentError("description")

        toDoList.addTask(description)
    }

    private fun processUpdate(args: Array<String>, toDoList: ToDoList) {
        val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
        val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")
        val description = args.getOrNull(2) ?: throw MissingArgumentError("description")

        toDoList.updateTask(id, description)
    }

    private fun processUpdateStatus(args: Array<String>, toDoList: ToDoList) {
        val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
        val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")
        val status = Status.fromString(args[0].substring(5))

        toDoList.updateTaskStatus(id, status)
    }

    private fun processDelete(args: Array<String>, toDoList: ToDoList) {
        val idString = args.getOrNull(1) ?: throw MissingArgumentError("id")
        val id = idString.toIntOrNull() ?: throw ArgumentFormatException("id", "integer")

        toDoList.deleteTask(id)
    }

    private fun processVersion() = println("v$version")

    private fun processReset(toDoList: ToDoList) {
        println("Resetting to do list.")
        toDoList.clear()
    }

    private fun processHelp(args: Array<String>) {
        val command = args.getOrNull(1)

        if (command == null) Help.help()
        else Help.help(command)
    }

    private fun processNoCommand() {
        println("CLI to-do list made in Kotlin.")
        println("Use `help` to see all commands.")
        println("Source code: https://github.com/caiotdearaujo/clitodolist")
        println("Version: $version")
    }

    private fun processUnknownCommand(args: Array<String>) {
        println("Command ${args[0]} does not exist.")
        println("Use `help` to see all commands, or `help [command]` to get help about a specific command.")
    }

    private fun handleError(e: Exception) {
        when (e) {
            is MissingArgumentError,
            is TaskNotFoundException,
            is ArgumentFormatException,
            is IllegalArgumentException,
            is ViolatedFileException -> println(e.message)
            else -> throw e
        }
    }
}