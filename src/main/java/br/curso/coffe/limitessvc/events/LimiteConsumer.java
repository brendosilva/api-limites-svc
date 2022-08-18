package br.curso.coffe.limitessvc.events;

import br.curso.coffe.limitessvc.dto.TransactionDto;
import br.curso.coffe.limitessvc.service.LimiteDiarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class LimiteConsumer {

    private LimiteDiarioService limiteDiarioService;
    private ObjectMapper objectMapper;

    public LimiteConsumer(LimiteDiarioService limiteDiarioService, ObjectMapper objectMapper) {
        this.limiteDiarioService = limiteDiarioService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${app.topic}")
    public void onCosume(final String message) {
        try {
            TransactionDto transactionDto = getTransaction(message);
            limiteDiarioService.validarLimiteDiario(transactionDto);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private TransactionDto getTransaction(String message) throws IOException {
        TransactionDto dto = objectMapper.readValue(message, TransactionDto.class);
        dto.setData(LocalDateTime.now());
        return dto;
    }
}
