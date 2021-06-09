package br.com.eventvs.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 70, nullable = false)
    private String logradouro;

    @Column(length = 5, nullable = false)
    private String numero;

    @Column(length = 70, nullable = false)
    private String bairro;

    @Column(length = 70, nullable = false)
    private String cidade;

    @Column(length = 50, nullable = false)
    private String estado;

    @Column(length = 9, nullable = false)
    private String CEP;

}
