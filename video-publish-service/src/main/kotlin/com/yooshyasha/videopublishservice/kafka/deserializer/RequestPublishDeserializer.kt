package com.yooshyasha.videopublishservice.kafka.deserializer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.yooshyasha.videopublishservice.kafka.dto.RequestPublishVideo
import org.apache.kafka.common.serialization.Deserializer

class RequestPublishDeserializer : Deserializer<RequestPublishVideo> {
    override fun deserialize(topic: String?, data: ByteArray?): RequestPublishVideo {
        return jacksonObjectMapper().reader().readValue(data, RequestPublishVideo::class.java)
    }
}