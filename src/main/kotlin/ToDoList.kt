import errors.TaskNotFoundException

class ToDoList {
    var tasks = mutableListOf<Task>()

    fun addTask(description: String): Int {
        val task = Task(description)
        tasks.add(task)
        return tasks.indexOf(task)
    }

    fun updateTask(id: Int, description: String) {
        try {
            tasks[id].description = description
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(id)
        }
    }

    fun updateTaskStatus(id: Int, status: Status) {
        try {
            tasks[id].status = status
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(id)
        }
    }

    fun deleteTask(taskId: Int) {
        try {
            tasks.removeAt(taskId)
        } catch (e: IndexOutOfBoundsException) {
            throw TaskNotFoundException(taskId)
        }
    }
}