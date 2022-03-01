package bitshareskit.models

import bitshareskit.extensions.buildJsonObject
import bitshareskit.objects.GrapheneSerializable
import kotlinx.io.core.buildPacket
import kotlinx.io.core.readBytes
import org.java_json.JSONObject

data class BaseModel(
    val rawJson: JSONObject
): GrapheneSerializable {

    companion object {

        const val KEY_ = ""

        fun fromJson(rawJson: JSONObject): BaseModel {
            return BaseModel(rawJson)
        }
    }


    override fun toByteArray(): ByteArray = buildPacket {

    }.readBytes()

    override fun toJsonElement(): JSONObject = buildJsonObject {


    }

}