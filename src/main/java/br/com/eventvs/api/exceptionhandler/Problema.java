package br.com.eventvs.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
public class Problema {

	@Getter @Setter private Integer status;
	@Getter @Setter private OffsetDateTime dataHora;
	@Getter @Setter private String message;
	@Getter @Setter private List<Campo> campos;
	
	@AllArgsConstructor
	public static class Campo {
		public Campo(String nome2, String mensagem2) {
			// TODO Auto-generated constructor stub
		}
		@Getter @Setter private String nome;
		@Getter @Setter private String mensagem;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
	
	
	
}
