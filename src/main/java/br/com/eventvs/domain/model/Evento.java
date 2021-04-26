package br.com.eventvs.domain.model;

import br.com.eventvs.domain.enums.CategoriaEvento;
import br.com.eventvs.domain.enums.StatusEvento;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String titulo;

    private String descricao;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraTermino;

    private CategoriaEvento categoriaEvento;

    private StatusEvento statusEvento;

}
