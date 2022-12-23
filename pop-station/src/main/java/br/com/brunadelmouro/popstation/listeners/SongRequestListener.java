package br.com.brunadelmouro.popstation.listeners;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.brunadelmouro.popstation.messages.SongRequestMessage;
import br.com.brunadelmouro.popstation.models.enums.Genre;
import br.com.brunadelmouro.popstation.models.enums.Nationality;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SongRequestListener {

    private ObjectMapper objectMapper;

    public SongRequestListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "#{'${queues}'}")
    public void receiveMessage( Message message) throws IOException {
         var songMessage = objectMapper.readValue(message.getBody(), SongRequestMessage.class);

        validateGenreAndNationality(songMessage);

        playMusic(songMessage);
    }

    private void playMusic(SongRequestMessage songMessage) {
        System.out.println("PLAYING INTERNATIONAL POP MUSIC...");
        System.out.println(songMessage);
    }

    private void validateGenreAndNationality(SongRequestMessage songMessage) {
        if (!Genre.POP.equals(songMessage.getBand().getBandGenre())) {
            throw new AmqpRejectAndDontRequeueException("Band genre not accepted.");
        }

        if(Nationality.NATIONAL.equals(songMessage.getBand().getBandGenre())) {
            throw new AmqpRejectAndDontRequeueException("Nationality not accepted for this station.");
        }
    }
}
