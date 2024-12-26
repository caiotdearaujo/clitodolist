package errors

class ViolatedFileException: Exception(
    "The file that contains the information about the to-do list was manually violated, so it's not possible to read." +
            System.lineSeparator() + "Run `reset` command to create a new file."
)