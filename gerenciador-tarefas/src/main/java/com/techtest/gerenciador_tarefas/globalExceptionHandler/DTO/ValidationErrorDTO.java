package com.techtest.gerenciador_tarefas.globalExceptionHandler.DTO;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorDTO extends ApiErrorDTO {

	private Map<String, String> errors;

	public ValidationErrorDTO() {
		super();
	}

	public ValidationErrorDTO(Integer status, String error, String message, String path, LocalDateTime timestamp,
			Map<String, String> errors) {
		super(status, error, message, path, timestamp);
		this.errors = errors;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
