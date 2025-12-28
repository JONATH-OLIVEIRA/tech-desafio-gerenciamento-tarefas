package com.techtest.gerenciador_tarefas.globalExceptionHandler;

import java.util.UUID;

public class TarefaNaoEncontradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TarefaNaoEncontradaException(UUID id) {
		super("Tarefa com ID " + id + " não encontrada.");
	}

	public TarefaNaoEncontradaException(String mensagem, UUID id) {
		super(mensagem + " (ID: " + id + ")");
	}

	public TarefaNaoEncontradaException(String message) {
		super(message);
	}

}
