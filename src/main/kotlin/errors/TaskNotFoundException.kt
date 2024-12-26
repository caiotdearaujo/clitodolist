package errors

class TaskNotFoundException(taskId: Int): Exception("Task of id $taskId was not found.")