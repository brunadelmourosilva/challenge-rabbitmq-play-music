# challenge-rabbitmq-play-music

---
### Status: in progress :warning:
---

## Initial steps

:arrow_right: create 3 microservices
  * microservice 1: get infos by endpoint and the response should be published to queues.
  * microservice 2: consume message received by producer and play this music.
  * microservice 3: receive music object and save in the mongoDB database.
  
:arrow_right: queues | radio station

The queues will simulation radio stations and besides that, each radio station will play the song of according the routing key defined by exchange.

  * rock-song -> can be national or international
  * rock-song-international
  * pop-song-international
  * indie-song-international
  
:arrow_right: exchange type

 * topic exchange: receive messages and send to queues according by routing key.
 * dead letter exchange: consumer will send the message to dead letter if the music don't enter in any queue. Should throws AmqpRejectAndDontRequeueException exception.
  
:arrow_right: define routing key pattern

 * genre.nationality

---

![image](https://user-images.githubusercontent.com/61791877/198846298-7aa33718-0556-40e7-b415-f180e8228a89.png)

---

filas que podem receber mais de uma mensagem:

-> exemplo fila recebe só rock acima de 1990 e outra fila recebe rocks, independentemente do ano

ideia futura: adicionar datas, pois atualmente é complexa a ideia entre envio para as filas, visto que, haveria duplicação da mensagem caso ela entrasse em duas filas, uma que recebe entre x data e outra que recebe em x data igual. As routings keys seriam diferentes para mesma mensagem.
