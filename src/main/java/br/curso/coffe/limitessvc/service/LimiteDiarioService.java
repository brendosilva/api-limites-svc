package br.curso.coffe.limitessvc.service;

import br.curso.coffe.limitessvc.entities.LimiteDiario;
import br.curso.coffe.limitessvc.repositories.LimiteDiarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LimiteDiarioService {

    private final LimiteDiarioRepository limiteDiarioRepository;

    @Value("${limite.valor:0}")
    private BigDecimal valorDiario;

    public LimiteDiarioService(LimiteDiarioRepository limiteDiarioRepository) {
        this.limiteDiarioRepository = limiteDiarioRepository;
    }

    public Optional<LimiteDiario> getLimit(final Long agencia, final Long conta) {
        var limiteDiario = limiteDiarioRepository.findByAgenciaAndConta(agencia, conta);
        if (limiteDiario.isEmpty()) {
            LimiteDiario limit = new LimiteDiario();
            //TODO
            limit.setValor(valorDiario);
            limit.setConta(conta);
            limit.setAgencia(agencia);
            return Optional.of(limiteDiarioRepository.save(limit));
        }

        return limiteDiario;
    }

    public Optional<LimiteDiario> findById(Long id) {
        return limiteDiarioRepository.findById(id);
    }

}
