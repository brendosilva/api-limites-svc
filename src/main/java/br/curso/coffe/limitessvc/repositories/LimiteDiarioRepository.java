package br.curso.coffe.limitessvc.repositories;

import br.curso.coffe.limitessvc.entities.LimiteDiario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimiteDiarioRepository extends CrudRepository<LimiteDiario, Long> {

    Optional<LimiteDiario> findByAgenciaAndConta(final Long agencia, final Long conta);

}
