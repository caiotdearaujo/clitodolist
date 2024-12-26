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
        val statusString = when (status) {
            Status.TODO -> "to do"
            Status.IN_PROGRESS -> "in progress"
            Status.DONE -> "done"
        }

        return "Description: $description | " +
                "Status: $statusString | " +
                "Last updated at: ${DateFormatter.format(updatedAt)} | " +
                "Created at: ${DateFormatter.format(createdAt)}"
    }
}