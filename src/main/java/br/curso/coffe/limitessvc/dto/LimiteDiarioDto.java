package br.curso.coffe.limitessvc.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class LimiteDiarioDto implements Serializable {

    private Long id;

    private Long agencia;

    private Long conta;

    private BigDecimal valor;
}
