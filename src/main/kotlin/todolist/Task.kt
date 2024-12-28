package todolist

import util.DateFormatter
import java.util.Date

class Task(
    description: String,
    status: Status = Status.TODO,
) {
    var description: String = ""
        set(value) {
            if (value.isBlank()) throw IllegalArgumentException("Task description must not be empty")
            field = value
            updatedAt = Date()
        }
    var status: Status = status
        set(value) {
            if (value == field) return
            field = value
            updatedAt = Date()
        }
    var createdAt: Date = Date()
    var updatedAt: Date = createdAt

    init {
        this.description = description // Calls setter even in the constructor
    }

    override fun toString(): String {
        return "Description: $description | " +
                "Status: ${status.toFormattedString()} | " +
                "Last updated at: ${DateFormatter.format(updatedAt)} | " +
                "Created at: ${DateFormatter.format(createdAt)}"
    }
}