package marlon.leoner.projeto.avaliacao.tecnica.scheduler;

import lombok.AllArgsConstructor;

import marlon.leoner.projeto.avaliacao.tecnica.aggregation.SessionAggregation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SessionScheduler {

    private SessionAggregation aggregation;

    @Scheduled(cron = "*/10 * * * * *")
    public void openSessions() {
        aggregation.openSessions();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void closeSessions() {
        aggregation.closeSessions();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void notifySessionsResult() {
        aggregation.notifySessions();
    }
}
