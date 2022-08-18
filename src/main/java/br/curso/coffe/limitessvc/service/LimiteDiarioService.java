package br.curso.coffe.limitessvc.service;


import br.curso.coffe.limitessvc.dto.TransactionDto;
import br.curso.coffe.limitessvc.entities.LimiteDiario;
import br.curso.coffe.limitessvc.events.LimiteProducer;
import br.curso.coffe.limitessvc.repositories.LimiteDiarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class LimiteDiarioService {

    private final LimiteDiarioRepository limiteDiarioRepository;

    private LimiteProducer limiteProducer;

    @Value("${limite.valor:0}")
    private BigDecimal valorDiario;

    public LimiteDiarioService(LimiteDiarioRepository limiteDiarioRepository, LimiteProducer limiteProducer) {
        this.limiteDiarioRepository = limiteDiarioRepository;
        this.limiteProducer = limiteProducer;
    }

    public Optional<LimiteDiario> getLimit(final Long agencia, final Long conta) {
        var limiteDiario = limiteDiarioRepository.findByAgenciaAndConta(agencia, conta);
        if (limiteDiario.isEmpty()) {
            LimiteDiario limit = new LimiteDiario();
            //TODO
            limit.setValor(valorDiario);
            limit.setConta(conta);
            limit.setAgencia(agencia);
            limit.setDate(LocalDateTime.now());
            return Optional.of(limiteDiarioRepository.save(limit));
        }

        return limiteDiario;
    }

    public Optional<LimiteDiario> findById(Long id) {
        return limiteDiarioRepository.findById(id);
    }

    public void validarLimiteDiario(TransactionDto dto) {
        var limiteDiario = limiteDiarioRepository.findByAgenciaAndContaAndDate(
                dto.getConta().getCodigoAgencia(), dto.getConta().getCodigoConta(), LocalDateTime.now()
        );

        if (Objects.isNull(limiteDiario)) {
            limiteDiario = new LimiteDiario();
            limiteDiario.setAgencia(dto.getConta().getCodigoAgencia());
            limiteDiario.setConta(dto.getConta().getCodigoConta());
            limiteDiario.setValor(valorDiario);
            limiteDiario.setDate(dto.getData());
            limiteDiario = limiteDiarioRepository.save(limiteDiario);
        }

        if (limiteDiario.getValor().compareTo(dto.getValor()) < 0) {
            dto.suspeitaFraude();
            log.info("Transação excede valor diario: {}", dto);
        }
        else if (limiteDiario.getValor().compareTo(BigDecimal.valueOf(10000L)) > 0) {
            dto.analiseHumana();
            log.info("Transação em analise humana {}", dto);
        }

        else {
            dto.analisada();
            log.info("Transação analisada");
            limiteDiario.setValor(limiteDiario.getValor().subtract(dto.getValor()));
            limiteDiarioRepository.save(limiteDiario);
        }
        limiteProducer.enviar(dto);
    }
}
