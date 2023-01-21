package br.com.brunadelmouro.songconsumer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.connection.username}")
    private String username;

    @Value("${spring.rabbitmq.connection.password}")
    private String password;

    @Value("${spring.rabbitmq.connection.host}")
    private String host;

    @Value("${play-music.exchange}")
    private String exchange;

    @Value("${play-music.exchange-error}")
    private String dlxExchange;

    @Value("${play-music.queues}")
    private String rockQueue;

    @Value("${play-music.queues-error}")
    private String dlqRockQueue;

    @Value("${play-music.routing-keys}")
    private String rockRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(exchange).build();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.nonDurable(rockQueue).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(rockRoutingKey);
    }

    @Bean
    public TopicExchange dlxTopicExchange() {
        return ExchangeBuilder.topicExchange(dlxExchange).build();
    }

    @Bean
    public Queue dlqQueue(){
        return QueueBuilder.nonDurable(dlqRockQueue).build();
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlqQueue()).to(dlxTopicExchange()).with("deadLetter");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
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
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());

        return factory;
    }
}