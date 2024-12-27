package todolist

import kotlinx.serialization.Serializable

@Serializable
enum class Status {
    TODO, IN_PROGRESS, DONE;

    fun toFormattedString(): String {
        return when (this) {
            TODO -> "to do"
            IN_PROGRESS -> "in progress"
            DONE -> "done"
        }
    }

    companion object {
        fun fromString(value: String): Status {
            return when (value) {
                "todo" -> TODO
                "in-progress" -> IN_PROGRESS
                "done" -> DONE
                else -> throw IllegalArgumentException("Unknown status '$value'")
            }
        }
    }
}