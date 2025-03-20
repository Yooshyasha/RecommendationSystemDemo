package com.yooshyasha.videopublishservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
class VideoPublishServiceApplication

fun main(args: Array<String>) {
    runApplication<VideoPublishServiceApplication>(*args)
}
