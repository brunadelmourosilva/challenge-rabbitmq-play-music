package br.com.brunadelmouro.songconsumer.listeners;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.brunadelmouro.songconsumer.messages.SongRequestMessage;
import br.com.brunadelmouro.songconsumer.models.enums.Genre;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SongRequestListener {

    private ObjectMapper objectMapper;

    public SongRequestListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "#{'${play-music.queues}'}")
    public void receiveMessage(Message message) throws IOException {
         var songMessage = objectMapper.readValue(message.getBody(), SongRequestMessage.class);
//// TODO: 21/01/2023 revisar isso para funcionar retry 
        validateGenre(songMessage);

        playMusic(songMessage);
    }

    private void playMusic(SongRequestMessage songMessage) {
        System.out.println("PLAYING ROCK MUSIC...");
        System.out.println(songMessage);
    }

    private void validateGenre(SongRequestMessage songMessage) {
        if (!Genre.ROCK.equals(songMessage.getBand().getBandGenre())) {
            throw new AmqpRejectAndDontRequeueException("Band genre not accepted.");
        }
    }
}
