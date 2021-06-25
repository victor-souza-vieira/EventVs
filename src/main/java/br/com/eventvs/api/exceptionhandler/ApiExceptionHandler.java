package br.com.eventvs.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.eventvs.domain.exception.EntidadeNaoEncontradaException;
import br.com.eventvs.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setMessage(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return super.handleExceptionInternal(ex,problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocio(EntidadeNaoEncontradaException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setMessage(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return super.handleExceptionInternal(ex,problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		var campos = new ArrayList<Problema.Campo>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome =((FieldError) error).getField();
			String mensagem = error.getDefaultMessage();
			campos.add(new Problema.Campo(nome,mensagem));
		}
		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setMessage("Um ou mais campos estão inválidos. Preencha Corretamente!");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		return super.handleExceptionInternal(ex,problema, headers, status, request);
	}
}
