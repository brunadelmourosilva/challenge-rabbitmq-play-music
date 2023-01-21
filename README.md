# Play music with RabbitMQ

---
### Status: done :heavy_check_mark:
---

## Initial steps

:arrow_right: create 3 microservices
  * microservice 1: get infos by endpoint and the response should be published to queues.
  * microservice 2: consume the message received by producer to play a rock song(international or national).
  * microservice 3:  consume the message received by producer to play a pop song(international).
  
:arrow_right: queues | radio station

The queues will simulation radio stations and besides that, each radio station will play the song of according the routing key defined by exchange.

  * rock-song -> can be national or international
  * pop-song-international
  
:arrow_right: exchange type

 * topic exchange: receive messages and send to queues of according by routing key.
 * dead letter exchange: consumer will send the message to dead letter if the music don't enter in any queue. For this application, there will be one exchange that directs the pop or music songs and also, two dead letter exchanges that will be received the wrong rock music or pop music.
Besides that, it should handle erros with AmqpRejectAndDontRequeueException exception.
  
:arrow_right: define routing key pattern

 * genre.nationality

---

## Architecture

![image](https://user-images.githubusercontent.com/61791877/209259041-dbb73b61-5226-4cc4-9d49-316b2ffb6c35.png)

---

## Final results

### Queues

![image](https://user-images.githubusercontent.com/61791877/213882765-893ea992-65d6-449c-86b6-05d7da695099.png)

### Exchanges

![image](https://user-images.githubusercontent.com/61791877/213882787-b340c81c-ef4e-45b8-afc4-114716f0bf9d.png)


