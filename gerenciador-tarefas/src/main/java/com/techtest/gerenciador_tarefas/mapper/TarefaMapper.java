package com.techtest.gerenciador_tarefas.mapper;

import com.techtest.gerenciador_tarefas.DTO.CriarTarefaDTO;
import com.techtest.gerenciador_tarefas.DTO.TarefaDTO;
import com.techtest.gerenciador_tarefas.model.Tarefa;

public class TarefaMapper {

	private TarefaMapper() {
	}

	public static Tarefa toEntity(CriarTarefaDTO dto) {
		if (dto == null) {
			return null;
		}
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(dto.getTitulo());
		tarefa.setDescricao(dto.getDescricao());
		tarefa.setPrioridade(dto.getPrioridade());

		return tarefa;
	}

	public static TarefaDTO toDTO(Tarefa entity) {
		if (entity == null) {
			return null;
		}
		return new TarefaDTO(entity.getId(), entity.getTitulo(), entity.getDescricao(), entity.getStatus(),
				entity.getPrioridade(), entity.getDataCriacao(), entity.getDataConclusao());

	}
}
