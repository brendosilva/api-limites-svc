package br.curso.coffe.limitessvc.repositories;

import br.curso.coffe.limitessvc.entities.LimiteDiario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimiteDiarioRepository extends CrudRepository<LimiteDiario, Long> {

    LimiteDiario findByAgenciaAndConta(Long agencia, Long conta);

}
