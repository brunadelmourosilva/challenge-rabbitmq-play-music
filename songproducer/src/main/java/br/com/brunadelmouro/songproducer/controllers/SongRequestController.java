package br.com.brunadelmouro.songproducer.controllers;

import br.com.brunadelmouro.songproducer.models.SongRequest;
import br.com.brunadelmouro.songproducer.services.SongRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song-request")
public class SongRequestController {

    private SongRequestService songRequestService;

    public SongRequestController(SongRequestService songRequestService) {
        this.songRequestService = songRequestService;
    }

    @PostMapping
    public ResponseEntity<SongRequest> createSongRequest(@RequestBody SongRequest songRequest) {
        songRequest = songRequestService.createSongRequest(songRequest);

        return new ResponseEntity<>(songRequest, HttpStatus.CREATED);
    }
}
