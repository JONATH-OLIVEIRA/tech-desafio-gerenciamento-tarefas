package com.techtest.gerenciador_tarefas.repository.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.techtest.gerenciador_tarefas.model.PrioridadeTarefa;
import com.techtest.gerenciador_tarefas.model.StatusTarefa;
import com.techtest.gerenciador_tarefas.model.Tarefa;

public class TarefaSpecification {
	private TarefaSpecification() {
	}

	public static Specification<Tarefa> statusIgual(StatusTarefa status) {
		return (root, query, cb) -> cb.equal(root.get("status"), status);
	}

	public static Specification<Tarefa> prioridadeIgual(PrioridadeTarefa prioridade) {
		return (root, query, cb) -> cb.equal(root.get("prioridade"), prioridade);
	}

	public static Specification<Tarefa> dataCriacaoEntre(LocalDateTime inicio, LocalDateTime fim) {

		return (root, query, cb) -> cb.between(root.get("dataCriacao"), inicio, fim);
	}

}
