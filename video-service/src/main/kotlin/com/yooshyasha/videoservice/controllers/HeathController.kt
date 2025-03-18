package com.yooshyasha.videoservice.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HeathController {
    @GetMapping
    fun getHealth() = "OK"
}