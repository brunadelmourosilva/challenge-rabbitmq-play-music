package br.com.brunadelmouro.popstation.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${play-music.exchange}")
    private String exchange;

    @Value("${play-music.exchange-error}")
    private String dlxExchange;

    @Value("${play-music.queues}")
    private String popQueue;

    @Value("${play-music.queues-error}")
    private String dlqPopQueue;

    @Value("${play-music.routing-keys}")
    private String popRoutingKey;

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(exchange).build();
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.nonDurable(popQueue).deadLetterExchange(dlxExchange).deadLetterRoutingKey("deadLetter").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(popRoutingKey);
    }

    @Bean
    public TopicExchange dlxTopicExchange() {
        return ExchangeBuilder.topicExchange(dlxExchange).build();
    }

    @Bean
    public Queue dlqQueue(){
        return QueueBuilder.nonDurable(dlqPopQueue).build();
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlqQueue()).to(dlxTopicExchange()).with("deadLetter");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //        rabbitTemplate.setMessageConverter(messageConverter);
        return  rabbitTemplate;
    }

    @Bean
    public RabbitAdmin createAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
