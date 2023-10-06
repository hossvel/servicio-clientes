package com.devhoss.app.servicioclientes.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.AmqpTemplate;
@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publish(Object payload, String exchange, String routingKey) {
        // log.info("Publishing to {} using routingKey {}. Payload: {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        // log.info("Published to {} using routingKey {}. Payload: {}", exchange, routingKey, payload);
    }

}