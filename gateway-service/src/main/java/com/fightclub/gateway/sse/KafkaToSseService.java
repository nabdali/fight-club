package com.fightclub.gateway.sse;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
@Service
public class KafkaToSseService {

    // Replay the last 50 events to any new subscriber (handles reconnects and React StrictMode)
    private final Sinks.Many<ServerSentEvent<String>> sink =
            Sinks.many().replay().limit(50);

    @KafkaListener(topics = {"fight.created", "fight.ended", "stats.update"}, groupId = "gateway-sse")
    public void onMessage(ConsumerRecord<String, String> record) {
        log.debug("Kafka → SSE  topic={} key={}", record.topic(), record.key());
        ServerSentEvent<String> event = ServerSentEvent.<String>builder()
                .event(record.topic())
                .data(record.value())
                .build();
        Sinks.EmitResult result = sink.tryEmitNext(event);
        if (result.isFailure()) {
            log.warn("SSE emit failed: topic={} result={}", record.topic(), result);
        }
    }

    public Flux<ServerSentEvent<String>> stream() {
        Flux<ServerSentEvent<String>> heartbeat = Flux.interval(Duration.ofSeconds(25))
                .map(i -> ServerSentEvent.<String>builder().comment("heartbeat").build());
        return Flux.merge(sink.asFlux(), heartbeat);
    }
}
