package graphene.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.mapSerialDescriptor
import kotlinx.serialization.descriptors.setSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonArray
import java.util.*
import kotlin.Comparator

class SortedSetSerializer<T: Comparable<T>>(
    private val elementSerializer: KSerializer<T>
) : KSerializer<SortedSet<T>> {
    override val descriptor: SerialDescriptor = setSerialDescriptor(elementSerializer.descriptor)
    override fun deserialize(decoder: Decoder): SortedSet<T> {
        decoder as JsonDecoder
        return decoder.decodeJsonElement().jsonArray.map {
            decoder.json.decodeFromJsonElement(elementSerializer, it)
        }.toSortedSet()
    }
    override fun serialize(encoder: Encoder, value: SortedSet<T>) {
        val size = value.size
        val composite = encoder.beginCollection(elementSerializer.descriptor, size)
        val iterator = value.iterator()
        for (index in 0 until size)
            composite.encodeSerializableElement(descriptor, index, elementSerializer, iterator.next())
        composite.endStructure(descriptor)
    }
}

class StaticVarSetSerializer<T: Any>(
    private val elementSerializer: StaticVarSerializer<T>
) : KSerializer<SortedSet<T>> {
    override val descriptor: SerialDescriptor = setSerialDescriptor(elementSerializer.descriptor)
    private val comparator: Comparator<T> = Comparator { o1: T, o2: T ->
        elementSerializer.typelist.indexOf(o1::class) - elementSerializer.typelist.indexOf(o2::class)
    }
    override fun deserialize(decoder: Decoder): SortedSet<T> {
        if (decoder is JsonDecoder) {
            return decoder.decodeJsonElement().jsonArray.map {
                decoder.json.decodeFromJsonElement(elementSerializer, it)
            }.toSortedSet(comparator)
        } else {
            TODO()
        }
    }
    override fun serialize(encoder: Encoder, value: SortedSet<T>) {
        val size = value.size
        val composite = encoder.beginCollection(elementSerializer.descriptor, size)
        val iterator = value.iterator()
        for (index in 0 until size)
            composite.encodeSerializableElement(descriptor, index, elementSerializer, iterator.next())
        composite.endStructure(descriptor)
    }
}

class SortedMapSerializer<K: Comparable<K>, V>(
    private val keySerializer: KSerializer<K>,
    private val valueSerializer: KSerializer<V>
) : KSerializer<SortedMap<K, V>> {
    override val descriptor: SerialDescriptor = mapSerialDescriptor(keySerializer.descriptor, valueSerializer.descriptor)
    override fun deserialize(decoder: Decoder): SortedMap<K, V> {
        decoder as JsonDecoder
        return decoder.decodeJsonElement().jsonArray.associateBy(
            { decoder.json.decodeFromJsonElement(keySerializer, it.jsonArray[0]) },
            { decoder.json.decodeFromJsonElement(valueSerializer, it.jsonArray[1]) }
        ).toSortedMap()
    }
    override fun serialize(encoder: Encoder, value: SortedMap<K, V>) {
        TODO()
    }
}

class PairAsArraySerializer<A, B>(
    private val firstSerializer: KSerializer<A>, private val secondSerializer: KSerializer<B>
) : KSerializer<Pair<A, B>> {
    override val descriptor: SerialDescriptor = mapSerialDescriptor(firstSerializer.descriptor, secondSerializer.descriptor)
    override fun deserialize(decoder: Decoder): Pair<A, B> {
        return (decoder as JsonDecoder).decodeJsonElement().jsonArray.let {
            Pair(
                decoder.json.decodeFromJsonElement(firstSerializer, it[0]),
                decoder.json.decodeFromJsonElement(secondSerializer, it[1])
            )
        }
    }
    override fun serialize(encoder: Encoder, value: Pair<A, B>) {
        (encoder as JsonEncoder).encodeJsonElement(
            buildJsonArray {
                add(encoder.json.encodeToJsonElement(firstSerializer, value.first))
                add(encoder.json.encodeToJsonElement(secondSerializer, value.second))
            }
        )
    }
}