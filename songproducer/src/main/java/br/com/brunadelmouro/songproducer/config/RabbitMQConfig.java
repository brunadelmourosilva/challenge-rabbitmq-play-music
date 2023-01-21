package br.com.brunadelmouro.songproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.connection.username}")
    private String username;

    @Value("${rabbitmq.connection.password}")
    private String password;

    @Value("${rabbitmq.connection.host}")
    private String host;

    @Value("${play-music.exchange}")
    private String exchange;

//    @Value("#{'${queues.play-music.queues}'.split(',')}")
//    private String[] queues;
//    @Value("#{'${queues.play-music.routing-keys}'.split(',')}")
//    private String[] routingKeys;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

/* Way to create all the queues just one producer */
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        AmqpAdmin declare = new RabbitAdmin(connectionFactory());
//
//        /* declare exchange */
//        declare.declareExchange(exchange());
//
//        /* declare queues and declare bindings */
//        for (int i = 0; i < queues.length; i++) {
//            //todo finish dead letter exchange
//            var queue = QueueBuilder.durable(queues[i]).withArgument("x-dead-letter-exchange", exchangeError)
//                    .withArgument("x-dead-letter-routing-key", "deadLetter").build();
//
//            declare.declareQueue(queue);
//
//            declare.declareBinding(BindingBuilder.bind(queue).to(exchange()).with(routingKeys[i]));
//        }
//
//        return declare;
//    }
