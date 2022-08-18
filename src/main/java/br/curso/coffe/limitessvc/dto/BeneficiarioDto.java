package br.curso.coffe.limitessvc.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BeneficiarioDto implements Serializable {
    private static final long serialVersionUID = 2806421543985360625L;


    public BeneficiarioDto() {
    }


    private Long cpf;


    private Long codigoBanco;


    private String agencia;


    private String conta;


    private String nomeFavorecido;
}
