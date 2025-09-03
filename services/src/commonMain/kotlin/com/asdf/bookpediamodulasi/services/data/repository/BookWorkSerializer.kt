package com.asdf.bookpediamodulasi.services.data.repository

import com.asdf.bookpediamodulasi.services.data.model.Description
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object BookWorkSerializer : KSerializer<BookWork> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = BookWork::class.simpleName!!
    ) {
        element<String?>("description")
    }

    override fun serialize(
        encoder: Encoder, value: BookWork
    ) = encoder.encodeStructure(descriptor) {
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }

    override fun deserialize(decoder: Decoder): BookWork = decoder.decodeStructure(descriptor) {
        var description: String? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This decoder only works with JSON"
                    )
                    val element = jsonDecoder.decodeJsonElement()
                    description = if (element is JsonObject) {
                        decoder.json.decodeFromJsonElement<Description>(
                            element = element,
                            deserializer = Description.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString) {
                        element.content
                    } else null
                }

                CompositeDecoder.DECODE_DONE -> break
                else -> break
            }
        }
        return@decodeStructure BookWork(description)
    }
}