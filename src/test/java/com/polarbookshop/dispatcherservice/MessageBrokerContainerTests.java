package com.polarbookshop.dispatcherservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Disabled
@Testcontainers
 class MessageBrokerContainerTests {

    private static final int RABBITMQ_PORT = 5672;

    @Container
    GenericContainer<?> rabbitMq = new GenericContainer<>(DockerImageName.parse("rabbitmq:3.10-management"))
            .withExposedPorts(RABBITMQ_PORT);


    @DynamicPropertySource
    void registerProperties(DynamicPropertyRegistry registry){
        registry.add("spring.rabbitmq.host", () -> rabbitMq.getHost());
        registry.add("spring.rabbitmq.port", () -> rabbitMq.getMappedPort(RABBITMQ_PORT));
    }

    @Test
    void verifyThatSpringContextLoads() {
    }
}
