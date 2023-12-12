package marlon.leoner.projeto.avaliacao.tecnica.consumer;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.domain.Session;
import marlon.leoner.projeto.avaliacao.tecnica.enums.StatusSessionEnum;
import marlon.leoner.projeto.avaliacao.tecnica.service.SessionService;
import marlon.leoner.projeto.avaliacao.tecnica.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SessionConsumer {

    private final Logger logger = LoggerFactory.getLogger(SessionConsumer.class);

    private final SessionService service;

    @RabbitListener(queues = {Constants.OPEN_SESSION_QUEUE})
    public void openSession(@Payload String message) {
        Session session = service.getSessionOrExceptionById(message);
        if (!session.allowOpen()) {
            return;
        }

        logger.info("Sessão {} aberta para votação.", session.getContract().getName());
        session.setStatus(StatusSessionEnum.OPEN);
        service.save(session);
    }

    @RabbitListener(queues = {Constants.CLOSE_SESSION_QUEUE})
    public void closeSession(@Payload String message) {
        Session session = service.getSessionOrExceptionById(message);
        if (!session.allowClose()) {
            return;
        }

        logger.info("Sessão {} fechada.", session.getContract().getName());
        session.setStatus(StatusSessionEnum.CLOSED);
        service.save(session);
    }

    @RabbitListener(queues = {Constants.NOTIFY_SESSION_RESULT_QUEUE})
    public void notifySessions(@Payload String message) {
        Session session = service.getSessionOrExceptionById(message);
        if (!session.allowResult()) {
            return;
        }

        logger.info("Notificando resultado: " + session.getResult());
        session.setStatus(StatusSessionEnum.NOTIFIED);
        service.save(session);
    }
}
