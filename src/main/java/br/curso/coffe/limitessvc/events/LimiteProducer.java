package br.curso.coffe.limitessvc.events;

import br.curso.coffe.limitessvc.dto.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LimiteProducer {

    @Value("${app.returnTopic}")
    private String topic;
    private KafkaTemplate<String,String> kafkaTemplate;
    private ObjectMapper objectMapper;


    public LimiteProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviar(final TransactionDto dto) {
        String payLoad = null;
        try {
            payLoad = objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Message<String> message = MessageBuilder
                .withPayload(payLoad)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.PARTITION_ID,0)
                .build();

        kafkaTemplate.send(message);
    }
}
