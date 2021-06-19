package br.com.eventvs.api.dto.responses;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import br.com.eventvs.domain.enums.StatusEvento;
import br.com.eventvs.domain.model.Categoria;
import br.com.eventvs.domain.model.Endereco;
import br.com.eventvs.domain.model.Evento;
import br.com.eventvs.domain.model.Produtor;

public class EventoResponse {
	
	   private Integer id;

	    private String nome;

	    private String descricao;

	    private LocalDateTime dataHoraInicio;

	    private LocalDateTime dataHoraFim;

	    private Categoria categoria;

	    private StatusEvento statusEvento;

	    private Produtor produtor;

	    private Endereco endereco;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public LocalDateTime getDataHoraInicio() {
			return dataHoraInicio;
		}

		public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
			this.dataHoraInicio = dataHoraInicio;
		}

		public LocalDateTime getDataHoraFim() {
			return dataHoraFim;
		}

		public void setDataHoraFim(LocalDateTime dataHoraFim) {
			this.dataHoraFim = dataHoraFim;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		public StatusEvento getStatusEvento() {
			return statusEvento;
		}

		public void setStatusEvento(StatusEvento statusEvento) {
			this.statusEvento = statusEvento;
		}

		public Produtor getProdutor() {
			return produtor;
		}

		public void setProdutor(Produtor produtor) {
			this.produtor = produtor;
		}

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
		}
	
}
