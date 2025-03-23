package com.yooshyasha.videopublishservice.kafka

import com.yooshyasha.videopublishservice.kafka.deserializer.RequestPublishDeserializer
import com.yooshyasha.videopublishservice.kafka.dto.RequestPublishVideo
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
@EnableKafka
class KafkaConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun consumerFactory(): ConsumerFactory<String, RequestPublishVideo> {
        val valueDeserializer = RequestPublishDeserializer()

        val config = HashMap<String, Any>()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = valueDeserializer::class.java
        config[JsonDeserializer.TRUSTED_PACKAGES] = "com.yooshyasha.videopublishservice.kafka.dto"

        return DefaultKafkaConsumerFactory(config.toMap(), StringDeserializer(), valueDeserializer)
    }

    @Bean
    fun consumerContainer(
        consumerFactory: ConsumerFactory<String, RequestPublishVideo>,
    ): ConcurrentKafkaListenerContainerFactory<String, RequestPublishVideo> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, RequestPublishVideo>()
        factory.consumerFactory = consumerFactory
        return factory
    }
}