package com.zserg.notepad

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer


@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfiguration {
    @Bean
    @ServiceConnection
    fun mongoDBContainer(): MongoDBContainer {
        return MongoDBContainer("mongo:6.0.5")
    }
}