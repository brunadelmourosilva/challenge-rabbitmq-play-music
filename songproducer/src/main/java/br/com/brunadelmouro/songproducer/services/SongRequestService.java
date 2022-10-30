package br.com.brunadelmouro.songproducer.services;

import br.com.brunadelmouro.songproducer.models.SongRequest;
import br.com.brunadelmouro.songproducer.senders.SongRequestSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class SongRequestService {

    private SongRequestSender songRequestSender;

    public SongRequestService(final SongRequestSender songRequestSender) {
        this.songRequestSender = songRequestSender;
    }

    public SongRequest createSongRequest(final SongRequest songRequest) throws JsonProcessingException {
        songRequestSender.publishMessage(songRequest);

        //todo implementar as exceptions adequadas
        //todo retornar um número aleatório do numero do pedido da musica
        return songRequest;
    }
}
