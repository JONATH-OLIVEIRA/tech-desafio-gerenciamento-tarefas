package com.techtest.gerenciador_tarefas.globalExceptionHandler;

import java.util.UUID;

public class OperacaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperacaoInvalidaException(UUID id) {
		super("Operação inválida para a tarefa com ID " + id + "Tarefa ja Concluida");
	}

	public OperacaoInvalidaException(String message) {
		super(message);
	}

}
