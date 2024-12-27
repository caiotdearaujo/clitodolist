package help

object Help {
    private val helpMessages = listOf(
        CommandHelp("list", listOf(
            ParamHelp("List all tasks."),
            ParamHelp("[id]", "List task with the specified id."),
            ParamHelp("[status]", "List all tasks with the specified status.")
        )),

        CommandHelp("add", listOf(
            ParamHelp("[description]", "Create a task with the specified description.")
        )),

        CommandHelp("update", listOf(
            ParamHelp("[id] [description]", "Change the description of the task with the specified id.")
        )),

        CommandHelp("mark-todo", listOf(
            ParamHelp("[id]", "Mark the task with the specified id with \"to do\".")
        )),

        CommandHelp("mark-in-progress", listOf(
            ParamHelp("[id]", "Mark the task with the specified id with \"in progress\".")
        )),

        CommandHelp("mark-done", listOf(
            ParamHelp("[id]", "Mark the task with the specified id with \"done\".")
        )),

        CommandHelp("delete", listOf(
            ParamHelp("[id]", "Delete the task with the specified id.")
        )),

        CommandHelp("version", listOf(
            ParamHelp("Print the current version of the program.")
        )),

        CommandHelp("reset", listOf(
            ParamHelp("Clear the task list file. Useful to fix violations.")
        )),

        CommandHelp("help", listOf(
            ParamHelp("List all commands, parameters and descriptions."),
            ParamHelp("[command]", "List specific parameters and descriptions for the specified command.")
        ))
    )

    fun help() {
        // Size of the largest command in the list
        val largestCommandLength = helpMessages.map { it.command }.maxOfOrNull { it.length } ?: 0

        // Size of the largest param in the list
        val largestParamLength = helpMessages.flatMap { commandHelp -> commandHelp.paramsHelp.map { it.param.length } }
            .maxOrNull() ?: 0

        helpMessages.forEach {
            println(it.format(largestCommandLength, largestParamLength))
        }
    }

    fun help(command: String) {
        val helpMessage = helpMessages.firstOrNull { it.command == command }

        if (helpMessage == null) println("Command $command does not exist.")
        else println(helpMessage.format())
    }
}