package serializers

import todolist.Status
import todolist.Task
import errors.ViolatedFileException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

object TaskSerializer: KSerializer<Task> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("todolist.Task") {
        element<String>("description")
        element<Status>("status")
        element<Long>("createdAt")
        element<Long>("updatedAt")
    }

    override fun serialize(encoder: Encoder, value: Task) {
        val jsonObject = JsonObject(mapOf(
            "description" to JsonPrimitive(value.description),
            "status" to JsonPrimitive(value.status.name),
            "createdAt" to JsonPrimitive(value.createdAt.time),
            "updatedAt" to JsonPrimitive(value.updatedAt.time)
        ))

        return encoder.encodeSerializableValue(JsonObject.serializer(), jsonObject)
    }

    override fun deserialize(decoder: Decoder): Task {
        val jsonObject = decoder.decodeSerializableValue(JsonObject.serializer())

        try {
            val description = jsonObject["description"]!!.jsonPrimitive.content
            val status = Status.valueOf(jsonObject["status"]!!.jsonPrimitive.content)
            val createdAt = Date(jsonObject["createdAt"]!!.jsonPrimitive.content.toLong())
            val updatedAt = Date(jsonObject["updatedAt"]!!.jsonPrimitive.content.toLong())

            return Task(description, status).apply { this.createdAt = createdAt; this.updatedAt = updatedAt }
        } catch (e: Exception) {
            when (e) {
                is NullPointerException /* If key does not exist */,
                is IllegalArgumentException /* If description is blank */ ->
                    throw ViolatedFileException()
                else -> throw e
            }
        }
    }
}