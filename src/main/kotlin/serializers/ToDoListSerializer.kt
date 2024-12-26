package serializers

import ToDoList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ToDoListSerializer: KSerializer<ToDoList> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ToDoList") {
        element("tasks", ListSerializer(TaskSerializer).descriptor) // Describes List<Task>
    }

    override fun serialize(encoder: Encoder, value: ToDoList) {
        encoder.encodeSerializableValue(ListSerializer(TaskSerializer), value.tasks)
    }

    override fun deserialize(decoder: Decoder): ToDoList {
        val tasks = decoder.decodeSerializableValue(ListSerializer(TaskSerializer))

        return ToDoList().apply { tasks.forEach { this.tasks.add(it) } } // Recovers the old state of ToDoList
    }
}