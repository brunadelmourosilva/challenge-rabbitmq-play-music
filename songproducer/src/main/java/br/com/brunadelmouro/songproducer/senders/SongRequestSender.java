package br.com.brunadelmouro.songproducer.senders;

import br.com.brunadelmouro.songproducer.models.SongRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SongRequestSender {

    @Value("${queues.play-music.exchange}")
    private String exchange;

    private  AmqpTemplate amqpTemplate;

    private  ObjectMapper objectMapper;

    public SongRequestSender( AmqpTemplate amqpTemplate,  ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(SongRequest songRequest) {
        var routingKey = buildRoutingKey(songRequest);
        var message = convertObjectToJson(songRequest);

        amqpTemplate.convertAndSend(exchange, routingKey, message);

        log.info("Message sent to queue...");
    }

    private String convertObjectToJson(SongRequest songRequest)  {
        var message = "";
        try {
            message = objectMapper.writeValueAsString(songRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private String buildRoutingKey(SongRequest songRequest) {
        var stringBuilder = new StringBuilder();
        stringBuilder
                .append(songRequest.getBand().getBandGenre().getGenre())
                .append(".")
                .append(songRequest.getBand().getBandNationality().getNationality());

        return stringBuilder.toString();
    }
}
