package br.com.eventvs.domain.model;

import br.com.eventvs.domain.enums.StatusEvento;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 300, nullable = false)
    private String descricao;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private StatusEvento statusEvento;

    @ManyToOne
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
