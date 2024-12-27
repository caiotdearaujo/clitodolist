package errors

class TaskNotFoundException(taskId: Int): Exception("todolist.Task of id $taskId was not found.")