# challenge-rabbitmq-play-music

## Initial steps

:arrow_right: create 3 microservices
  * microservice 1: get infos by endpoint and the response should be published to queues.
  * microservice 2: consume message received by producer and play this music.
  * microservice 3: receive music object and save in the mongoDB database.
  
:arrow_right:: queues | radio station

The queues will simulation radio stations and besides that, each radio station will play the song of according the routing key defined by exchange.

**** define routing key pattern.
  * rock-song-limit-2010
  * rock-song-between-1970-1998
  * rock-song-guns-n-roses
  * pop-song
  * indie-song

---

ideias
criar varias filas para cada tipo de música, rock, pop... -> exchange topic

routing keys para genero.ano

filas que podem receber mais de uma mensagem:

-> exemplo fila recebe só rock acima de 1990 e outra fila recebe rocks, idendendenemente do ano

-> dead letter: recebe mensagens que não dão match com nenhuma mensagem recebida
