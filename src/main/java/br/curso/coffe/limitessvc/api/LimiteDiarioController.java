package br.curso.coffe.limitessvc.api;

import br.curso.coffe.limitessvc.entities.LimiteDiario;
import br.curso.coffe.limitessvc.service.LimiteDiarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;

@RestController
public class LimiteDiarioController {

    private final LimiteDiarioService limiteDiarioService;

    public LimiteDiarioController(LimiteDiarioService limiteDiarioService) {
        this.limiteDiarioService = limiteDiarioService;
    }

    @GetMapping(value = "/limite-diario/{agencia}/{conta}")
    public LimiteDiario getLimit(@PathVariable("agencia") final Long agencia, @PathVariable("conta") final Long conta){

       var limit = limiteDiarioService.getLimit(agencia, conta);
       if(limit.isPresent()) {
           return limit.get();
       }

       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
    }

    @GetMapping(value = "/limite-diario/{id}")
    public LimiteDiario findById(@PathVariable("id") Long id) {

        var limite =limiteDiarioService.findById(id);
        if (limite.isPresent()) {
            return limite.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found");
    }
}
