package br.com.brunadelmouro.songproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@EnableRabbit
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.connection.username}")
    private String username;
    @Value("${rabbitmq.connection.password}")
    private String password;
    @Value("${rabbitmq.connection.host}")
    private String host;
    @Value("#{'${queues.play-music.queues}'.split(',')}")
    private String[] queues;
    @Value("#{'${queues.play-music.routing-keys}'.split(',')}")
    private String[] routingKeys;
    @Value("${queues.play-music.exchange}")
    private String exchange;

    @Bean
    TopicExchange exchange() {
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

//    @Bean
//    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setDefaultReceiveQueue(queues);
//        rabbitTemplate.setReplyAddress(queue().getName());
//        rabbitTemplate.setUseDirectReplyToContainer(false);
//        return rabbitTemplate;
//    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());

        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        AmqpAdmin declare = new RabbitAdmin(connectionFactory());

        /* declare exchange */
        declare.declareExchange(exchange());

        /* declare queues and declare bindings */
        for (int i = 0; i < queues.length; i++) {
            var queue = new Queue(queues[i], true);

            declare.declareQueue(queue);

            declare.declareBinding(BindingBuilder.bind(queue).to(exchange()).with(routingKeys[i]));
        }

        return declare;
    }
}
