package br.com.brunadelmouro.songconsumer.listeners;

import br.com.brunadelmouro.songconsumer.messages.SongRequestMessage;
import br.com.brunadelmouro.songconsumer.models.enums.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SongRequestListener {

    private final ObjectMapper objectMapper;

    public SongRequestListener(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "#{'${queues.play-music.queues}'.split(',')}")
    public void receiveMessage(final Message message) throws IOException {
        final var result = objectMapper.readValue(message.getBody(), SongRequestMessage.class);

        if (!result.getBand().getBandGenre().equals(Genre.ROCK) ||
                !result.getBand().getBandGenre().equals(Genre.POP) ||
                !result.getBand().getBandGenre().equals(Genre.INDIE)) {
            throw new AmqpRejectAndDontRequeueException("Band genre not accepted.");
        }

        System.out.println(result + "\n\n\n");
    }
}
