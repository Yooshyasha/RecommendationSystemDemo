package com.yooshyasha.videoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class VideoServiceApplication

fun main(args: Array<String>) {
    runApplication<VideoServiceApplication>(*args)
}
