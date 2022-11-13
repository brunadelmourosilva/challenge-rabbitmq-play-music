package br.com.brunadelmouro.songproducer.services;

import br.com.brunadelmouro.songproducer.models.SongRequest;
import br.com.brunadelmouro.songproducer.senders.SongRequestSender;
import org.springframework.stereotype.Service;

@Service
public class SongRequestService {

    private final SongRequestSender songRequestSender;

    public SongRequestService(final SongRequestSender songRequestSender) {
        this.songRequestSender = songRequestSender;
    }

    public SongRequest createSongRequest(final SongRequest songRequest) {
        songRequestSender.publishMessage(songRequest);

        return songRequest;
    }
}
