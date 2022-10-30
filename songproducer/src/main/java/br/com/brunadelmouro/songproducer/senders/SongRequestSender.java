package br.com.brunadelmouro.songproducer.senders;

import br.com.brunadelmouro.songproducer.models.SongRequest;
import br.com.brunadelmouro.songproducer.models.enums.Genre;
import br.com.brunadelmouro.songproducer.models.enums.Nationality;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SongRequestSender {

    @Value("${queues.play-music.exchange}")
    private String exchange;

    private AmqpTemplate amqpTemplate;

    private ObjectMapper objectMapper;

    public SongRequestSender(final AmqpTemplate amqpTemplate, final ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(SongRequest songRequest) throws JsonProcessingException {
        var routingKey = buildRoutingKey(songRequest);
        var message = convertObjectToJson(songRequest);

        amqpTemplate.convertAndSend(exchange, routingKey, message);

        //todo add logs depois que mensagem for postada
        //todo ver sobre MessagePostProcessor
    }

    private String convertObjectToJson(SongRequest songRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(songRequest);
    }

    private String buildRoutingKey(SongRequest songRequest) {

        if (songRequest.getBand().getGenre().equals(Genre.ROCK) &&
                songRequest.getBand().getNationality().equals(Nationality.INTERNATIONAL)) {
            return "rock.international";
        }

        if (songRequest.getBand().getGenre().equals(Genre.POP) &&
                songRequest.getBand().getNationality().equals(Nationality.INTERNATIONAL)) {
            return "pop.international";
        }

        if (songRequest.getBand().getGenre().equals(Genre.INDIE) &&
                songRequest.getBand().getNationality().equals(Nationality.INTERNATIONAL)) {
            return "indie.international";
        }

        return "";
    }
}
