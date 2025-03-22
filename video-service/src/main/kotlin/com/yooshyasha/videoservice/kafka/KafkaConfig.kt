package com.yooshyasha.videoservice.kafka

import com.yooshyasha.videoservice.kafka.dto.RequestPublishVideo
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration
class KafkaConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun producerFactory(): ProducerFactory<String, RequestPublishVideo> {
        val jsonSerializer = JsonSerializer<RequestPublishVideo>()
        jsonSerializer.isAddTypeInfo = true

        val config = HashMap<String, Any>()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = jsonSerializer::class.java

        return DefaultKafkaProducerFactory(config.toMap())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, RequestPublishVideo> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun publishVideoTopic(): NewTopic {
        return TopicBuilder
            .name("publish-video")
            .partitions(10)
            .replicas(3)
            .compact()
            .build()
    }
}