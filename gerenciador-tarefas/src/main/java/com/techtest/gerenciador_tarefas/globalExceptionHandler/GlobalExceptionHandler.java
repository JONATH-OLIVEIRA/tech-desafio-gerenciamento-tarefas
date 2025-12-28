package com.techtest.gerenciador_tarefas.globalExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.techtest.gerenciador_tarefas.globalExceptionHandler.DTO.ApiErrorDTO;
import com.techtest.gerenciador_tarefas.globalExceptionHandler.DTO.ValidationErrorDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TarefaNaoEncontradaException.class)
	public ResponseEntity<ApiErrorDTO> handleTarefaNaoEncontrada(TarefaNaoEncontradaException ex,
			HttpServletRequest request) {

		ApiErrorDTO error = new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", ex.getMessage(),
				request.getRequestURI(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(OperacaoInvalidaException.class)
	public ResponseEntity<ApiErrorDTO> handleOperacaoInvalida(OperacaoInvalidaException ex,
			HttpServletRequest request) {

		ApiErrorDTO error = new ApiErrorDTO(HttpStatus.BAD_REQUEST.value(), "Operação inválida", ex.getMessage(),
				request.getRequestURI(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ValidationErrorDTO error = new ValidationErrorDTO(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				"Um ou mais campos estão inválidos", request.getRequestURI(), LocalDateTime.now(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ValidationErrorDTO> handleConstraintViolation(ConstraintViolationException ex,
			HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(violation -> {
			String fieldName = violation.getPropertyPath().toString();
			String errorMessage = violation.getMessage();
			errors.put(fieldName, errorMessage);
		});

		ValidationErrorDTO error = new ValidationErrorDTO(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				"Parâmetros inválidos", request.getRequestURI(), LocalDateTime.now(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorDTO> handleGenericException(Exception ex, HttpServletRequest request) {

		ex.printStackTrace();

		ApiErrorDTO error = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno",
				"Erro inesperado no sistema", request.getRequestURI(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorDTO> handleHttpMessageNotReadable(
	        HttpMessageNotReadableException ex,
	        HttpServletRequest request) {

	    ApiErrorDTO error = new ApiErrorDTO(
	            HttpStatus.BAD_REQUEST.value(),
	            "Erro de requisição",
	            "Valor inválido para um dos campos. Verifique os enums permitidos (ex: BAIXA, MEDIA, ALTA).",
	            request.getRequestURI(),
	            LocalDateTime.now()
	    );

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}