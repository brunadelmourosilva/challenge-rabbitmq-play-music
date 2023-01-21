package br.com.brunadelmouro.songconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

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
        return QueueBuilder.nonDurable(rockQueue).deadLetterExchange(dlxExchange).deadLetterRoutingKey("deadLetter").build();
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

    // use this instead object mapper
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
}